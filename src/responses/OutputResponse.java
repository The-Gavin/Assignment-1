package responses;

import interfaces.Response;

public class OutputResponse implements Response<String>{
	
	private final Response.Status status;
	private String data;
	
	public OutputResponse(Response.Status s, String str) {
		this.status = s;
		this.data = str;
	}

	@Override
	public Response.Status getStatus() {
		return status;
	}
	
	@Override
	public String getData() {
		return data;
	}
}
