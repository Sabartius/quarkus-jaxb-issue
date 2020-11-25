package de.sabartius.quarkus.jaxb;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import java.util.Map;

import javax.inject.Inject;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(MyClientWrapperRestClientTest.WiremockIt.class)
class MyClientWrapperRestClientTest {

    @Inject
    @RestClient
    QueueService wrapper;

    @Test
    void itWorks() {
        wrapper.get();
    }

    public static class WiremockIt implements QuarkusTestResourceLifecycleManager {
        private WireMockServer wireMockServer;

        @Override
        public Map<String, String> start() {
            wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
            wireMockServer.start();

            wireMockServer.stubFor(get(urlMatching("/online/rest/queue"))
                .willReturn(ok()
                    .withHeader("Content-Type", "application/xml")
                    .withBodyFile("xml/queue.xml")));

            return Map.of("de.sabartius.quarkus.jaxb.QueueService/mp-rest/url", wireMockServer.baseUrl() + "/online",
                "de.sabartius.quarkus.jaxb.QueueService/mp-rest/scope", "javax.inject.Singleton");
        }

        @Override
        public void stop() {
            if (null != wireMockServer) {
                wireMockServer.stop();
            }
        }
    }
}
