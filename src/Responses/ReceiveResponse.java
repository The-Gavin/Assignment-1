package Responses;

import Interfaces.Response;

public class ReceiveResponse implements Response<String>{
    private final Response.Status result;
    private String data;

    public ReceiveResponse(Response.Status s, String data) {
        this.result = s;
        this.data = data;
    }

    public Response.Status getStatus() {
        return result;
    }

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}
}