package implementations;

import java.util.ArrayList;
import java.util.List;

import interfaces.CompFactor;
import interfaces.Processing;
import interfaces.Response;
import interfaces.WebServer;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.InputResponse;
import responses.OutputResponse;
import src.CompEngineComponent;
import src.DataStorage;
import src.InputSource;
import src.OutputDestination;


public class WebServerImplement implements WebServer {
    CompFactor computationComponent;
    Processing processingComponent;

    //Take in a number of integers from the user (provide input source)    
    public InputResponse provideInputSource(InputSource inputSource) throws Exception{
        return new InputResponse(Response.Status.SUCCESS, new ArrayList<Integer>());
    }

    //Receive the factors for calculation (provide output destination)
    public OutputResponse provideOutputDestination(OutputDestination outputDestination) {
        return new OutputResponse(Response.Status.SUCCESS, ""); 
    }

	@Override
	public InitializationResponse coordinationInitializer() {
		// TODO Auto-generated method stub
		this.computationComponent = new CompEngineComponent();
		this.processingComponent = new DataStorage();
		
		return new InitializationResponse(Response.Status.SUCCESS, "");
	}

	@Override
	public FactorResponse factor(InputResponse nums, String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
