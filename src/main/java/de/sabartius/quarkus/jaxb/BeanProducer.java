package de.sabartius.quarkus.jaxb;

import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.client.ClientBuilder;

@ApplicationScoped
public class BeanProducer {

    @ApplicationScoped
    @Produces
    MyClientWrapper wrapper() {
        return new MyClientWrapper(ClientBuilder.newClient()
            .target(ConfigProvider.getConfig().getValue("de.sabartius.quarkus.target", String.class)));
    }
}
