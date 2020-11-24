package de.sabartius.quarkus.jaxb;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MyClientWrapper {

    private final WebTarget target;

    public MyClientWrapper(WebTarget target) {
        this.target = target.path("/rest/");
    }

    public QueueResponse getMyData() {
        final Response res = target
            .path("queue")
            .request(MediaType.APPLICATION_XML)
            .get();

        return validateAndReadEntity(res, QueueResponse.class);
    }

    private <T> T validateAndReadEntity(final Response res, final Class<T> clazz) {
        return validate(res).readEntity(clazz);
    }

    private Response validate(final Response res) {
        if (res.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            return res;
        }

        throw new IllegalStateException("Wrong Code: " + res.getStatus());
    }
}
