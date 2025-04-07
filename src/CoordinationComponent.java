import java.util.Optional;

public class CoordinationComponent implements WebServer {
	
	InputSource inputsource;
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
	

	@Override
	public OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception{
		if (initialize) {
			throw new Exception("Component Not Initialized");
		}
			return new OutputResponse(processingComponent.writeData(outputDestination).getStatus());
	}
}