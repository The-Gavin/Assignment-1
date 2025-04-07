package Implementations;

import Interfaces.CompFactor;
import Interfaces.Processing;
import Interfaces.Response;
import Interfaces.WebServer;
import Responses.InitializationResponse;
import Responses.InputResponse;
import Responses.OutputResponse;
import src.CompEngineComponent;
import src.DataStorage;
import src.InputSource;
import src.OutputDestination;


public class WebServerImplement implements WebServer {
    CompFactor computationComponent;
    Processing processingComponent;

    //Take in a number of integers from the user (provide input source)    
    public InputResponse provideInputSource(InputSource inputSource) throws Exception{
        return new InputResponse(Response.Status.SUCCESS);
    }

    //Receive the factors for calculation (provide output destination)
    public OutputResponse provideOutputDestination(OutputDestination outputDestination) {
        return new OutputResponse(Response.Status.SUCCESS);
    }

	@Override
	public InitializationResponse coordinationInitializer() {
		// TODO Auto-generated method stub
		this.computationComponent = new CompEngineComponent();
		this.processingComponent = new DataStorage();
		
		return new InitializationResponse(Response.Status.SUCCESS);
	}

}
