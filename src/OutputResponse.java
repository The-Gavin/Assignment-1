public class OutputResponse implements Response{
	
	private final Response.Status result;
	
	public OutputResponse(Response.Status s) {
		result = s;
	}

	@Override
	public Response.Status getStatus() {
		return result;
	}
}
