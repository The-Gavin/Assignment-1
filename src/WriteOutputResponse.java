public class WriteOutputResponse implements Response {
    private final Response.Status result;

    public WriteOutputResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}
