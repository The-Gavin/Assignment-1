public class WebServerImplement implements WebServer {
    CompFactor computationComponent;
    Processing processingComponent;

    //Take in a number of integers from the user (provide input source)
    public InputResponse provideInputSource(InputSource inputSource) {
        return new InputResponse();
    }

    //Receive the factors for calculation (provide output destination)
    public OutputResponse provideOutputDestination(OutputDestination outputDestination) {
        return new OutputResponse();
    }

    //Take in the character specified by the user as delimiter
    public DelimiterResponse setDelimiter(char delimiter) {
        return new DelimiterResponse();
    }

    //(Or opt for default delimiters)
    public DelimiterResponse setDelimiter() {
        return new DelimiterResponse();
        //(default delimiter TBD)
    }

}
