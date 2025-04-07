package Responses;

import Interfaces.Response;

public class WriteResponse implements Response {
    private final Response.Status result;

    public WriteResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}
