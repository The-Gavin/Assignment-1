package Interfaces;

import project.annotations.NetworkAPI;
import src.InputSource;
import src.OutputDestination;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.OutputResponse;
import Responses.InputResponse;

@NetworkAPI
public interface WebServer {
	
	InitializationResponse coordinationInitializer();
	
	//Take in a number of integers from the user (provide input source)
	InputResponse provideInputSource(InputSource inputSource) throws Exception;

	//Receive the factors for calculation (provide output destination)
	OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception;

	FactorResponse factor(InputResponse nums, String Path);

}