package de.sabartius.quarkus.jaxb;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/rest")
@RegisterRestClient
public interface QueueService {

    @GET
    @Path("/queue")
    @Produces("application/xml")
    QueueResponse get();
}
