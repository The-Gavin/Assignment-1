import project.annotations.NetworkAPI;

@NetworkAPI
public interface WebServer {
	
	InitializationResponse coordinationInitializer();
	
	//Take in a number of integers from the user (provide input source)
	InputResponse provideInputSource(InputSource inputSource) throws Exception;

	//Receive the factors for calculation (provide output destination)
	OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception;

	
	
}