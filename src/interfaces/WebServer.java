package interfaces;

import project.annotations.NetworkAPI;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.InputResponse;
import responses.OutputResponse;
import src.InputSource;
import src.OutputDestination;

@NetworkAPI
public interface WebServer {
	
	InitializationResponse coordinationInitializer();
	
	//Take in a number of integers from the user (provide input source)
	InputResponse provideInputSource(InputSource inputSource) throws Exception;

	//Receive the factors for calculation (provide output destination)
	OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception;

	FactorResponse factor(InputResponse nums, String path);

}