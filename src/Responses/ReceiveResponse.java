package Responses;

import Interfaces.Response;

public class ReceiveResponse implements Response{
    private final Response.Status result;

    public ReceiveResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}