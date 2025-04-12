package responses;

import interfaces.Response;

public class InitializationResponse implements Response<String> {
    private final Response.Status result;
    private String data;

    public InitializationResponse(Response.Status s, String str) {
        this.result = s;
        this.data = str;
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
