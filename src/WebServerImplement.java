public class WebServerImplement implements WebServer {
    CompFactor computationComponent;
    Processing processingComponent;

    //Take in a number of integers from the user (provide input source)
    public InputResponse provideInputSource(InputSource inputSource) {
        return new InputResponse(Response.Status.SUCCESS);
    }

    //Receive the factors for calculation (provide output destination)
    public OutputResponse provideOutputDestination(OutputDestination outputDestination) {
        return new OutputResponse(Response.Status.SUCCESS);
    }

}
