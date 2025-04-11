package Responses;

import Interfaces.Response;

public class OutputResponse implements Response<String>{
	
	private final Response.Status result;
	private String data;
	
	public OutputResponse(Response.Status s, String str) {
		result = s;
		this.data = str;
	}

	@Override
	public Response.Status getStatus() {
		return result;
	}
	
	@Override
	public String getData() {
		return data;
	}
}
