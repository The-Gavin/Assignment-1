public class ReadStreamResponse {
    private final Response.Status result;

    public ReadStreamResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}
