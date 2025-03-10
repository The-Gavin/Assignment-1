import src.project.annotations.NetworkAPI;

@NetworkAPI
public interface WebServer {
	//Take in a number of integers from the user (provide input source)
	InputResponse provideInputSource(InputSource inputSource);

	//Receive the factors for calculation (provide output destination)
	OutputResponse provideOutputDestination(OutputDestination outputDestination);

	//Take in the character specified by the user as delimiter
	//(Or opt for default delimiters)
	DelimiterResponse setDelimiter(char delimiter);
}