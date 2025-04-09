package src;
import java.util.Optional;

import Interfaces.CompFactor;
import Interfaces.Processing;
import Interfaces.Response;
import Interfaces.WebServer;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.InputResponse;
import Responses.OutputResponse;

public class CoordinationComponent implements WebServer {
	
	CompFactor computationComponent;
    Processing processingComponent;
    
    boolean initialize = false;
    
    public static Optional<CoordinationComponent> initialize() {
    	CoordinationComponent newComponent = new CoordinationComponent();
    	if(newComponent.coordinationInitializer().getStatus().equals(Response.Status.SUCCESS)) {
    		return Optional.of(newComponent);
    	}
    	return Optional.empty();
    }
    
    public InitializationResponse coordinationInitializer() {
    	this.computationComponent = new CompEngineComponent();
    	this.processingComponent = new DataStorage();
    	
    	InitializationResponse response = this.computationComponent.initializeComputation();
    	
    	if (response.getStatus().equals(Response.Status.SUCCESS)) {
    		initialize = true;
    	}
    	
    	return response;
    }

	@Override
	public InputResponse provideInputSource(InputSource inputSource) throws Exception{
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}
			return new InputResponse(processingComponent.readData(inputSource).getStatus());
	}
	
	public FactorResponse provideData(StreamSource values) {
		return computationComponent.readStream(values);
	}

	@Override
	public OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception{
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}
			return new OutputResponse(processingComponent.getOutputDestination(outputDestination).getStatus());
	}
	
	public FactorResponse factor() {
		FactorResponse factoringResponse = computationComponent.readStream(processingComponent.getStream());
		if(factoringResponse.getStatus().equals(Response.Status.FAILURE)) {
			return factoringResponse;
		}
		if( processingComponent.receiveData(new DataSource(factoringResponse.getFactors())).getStatus().equals(factoringResponse.getStatus())) {
			return factoringResponse;
		}
		return new FactorResponse(Response.Status.FAILURE);
	}
}