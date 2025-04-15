package responses;

import interfaces.Response;

public class ReceiveResponse implements Response<String>{
    private final Response.Status status;
    private String data;

    public ReceiveResponse(Response.Status s, String data) {
        this.status = s;
        this.data = data;
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