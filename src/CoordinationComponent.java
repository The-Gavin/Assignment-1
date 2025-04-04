
public class CoordinationComponent implements WebServer {
	
	InputSource inputsource;
	CompFactor computationComponent;
    Processing processingComponent;
    
    boolean initialize = false;
   
    public CoordinationComponent() {
    	
    }
    
    public InitializationResponse coordinationInitializer(CompFactor computationComponent, Processing processingComponent) {
    	this.computationComponent = computationComponent;
    	this.processingComponent = processingComponent;
    	
    	InitializationResponse response = this.computationComponent.initializeComputation();
    	
    	if (response.getStatus().equals(Response.Status.SUCCESS)) {
    		initialize = true;
    	}
    	
    	return response;
    }

	@Override
	public InputResponse provideInputSource(InputSource inputSource) throws Exception{
		if (initialize) {
			throw new Exception("Component Not Initialized");
		}
			return new InputResponse(processingComponent.readData(inputSource).getStatus());
	}

	@Override
	public OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception{
		if (initialize) {
			throw new Exception("Component Not Initialized");
		}
			return new OutputResponse(processingComponent.writeData(outputDestination).getStatus());
	}
}