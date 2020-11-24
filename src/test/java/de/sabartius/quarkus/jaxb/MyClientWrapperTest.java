package de.sabartius.quarkus.jaxb;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import org.junit.jupiter.api.Test;

import java.util.Map;

import javax.inject.Inject;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(MyClientWrapperTest.WiremockIt.class)
class MyClientWrapperTest {

    @Inject
    MyClientWrapper wrapper;

    @Test
    void itWorks() {
        wrapper.getMyData();
    }

    public static class WiremockIt implements QuarkusTestResourceLifecycleManager {
        private WireMockServer wireMockServer;

        @Override
        public Map<String, String> start() {
            wireMockServer = new WireMockServer();
            wireMockServer.start();

            stubFor(get(urlMatching("/online/rest/queue"))
                .willReturn(ok()
                    .withHeader("Content-Type", "application/xml")
                    .withBodyFile("xml/queue.xml")));

            return Map.of("de.sabartius.quarkus.target", wireMockServer.baseUrl() + "/online");
        }

        @Override
        public void stop() {
            if (null != wireMockServer) {
                wireMockServer.stop();
            }
        }
    }
}
