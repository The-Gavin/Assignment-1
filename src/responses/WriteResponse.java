package responses;

import interfaces.Response;

public class WriteResponse implements Response<String> {
    private final Response.Status status;
    private String data;
    
    public WriteResponse(Response.Status s, String str) {
        this.status = s;
        this.data = str;
    }

    public Response.Status getStatus() {
        return status;
    }

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}
}
