package de.sabartius.quarkus.jaxb;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

class MyClientWrapperWithoutQuarkusTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();

        wireMockServer.stubFor(get(urlMatching("/online/rest/queue"))
            .willReturn(ok()
                .withHeader("Content-Type", "application/xml")
                .withBodyFile("xml/queue.xml")));
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void itWorks() {
        new MyClientWrapper(ResteasyClientBuilder.newClient().target(wireMockServer.baseUrl() + "/online")).getMyData();
    }
}
