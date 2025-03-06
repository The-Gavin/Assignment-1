public class InitializationResponse implements Response {
    private final Response.Status result;

    public InitializationResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}
