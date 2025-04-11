package Responses;

import Interfaces.Response;

public class WriteOutputResponse implements Response<String> {
    private final Response.Status result;
    private String data;

    public WriteOutputResponse(Response.Status s, String data) {
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
