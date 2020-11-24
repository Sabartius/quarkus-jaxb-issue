package de.sabartius.quarkus.jaxb;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.ClientBuilder;

import com.github.tomakehurst.wiremock.WireMockServer;

class MyClientWrapperWithoutQuarkusTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();

        stubFor(get(urlMatching("/online/rest/queue"))
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
        new MyClientWrapper(ClientBuilder.newClient().target(wireMockServer.baseUrl() + "/online")).getMyData();
    }
}
