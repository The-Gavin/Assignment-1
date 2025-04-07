package Responses;

import Interfaces.Response;

public class FactorResponse implements Response{

	private Response.Status status;
	
	public FactorResponse(Response.Status s) {
		this.status = s;
	}
	
	@Override
	public Response.Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

}
