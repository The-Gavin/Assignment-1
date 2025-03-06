public class WriteResponse {
    private final Response.Status result;

    public WriteResponse(Response.Status s) {
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }
}
