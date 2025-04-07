package Responses;

import Interfaces.Response;

public class InputResponse implements Response{
	
	private final Response.Status result;
	
	public InputResponse(Response.Status s) {
		result = s;
	}

	@Override
	public Response.Status getStatus() {
		return result;
	}
    
}
