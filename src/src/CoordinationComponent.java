package src;
import java.util.Optional;

import interfaces.CompFactor;
import interfaces.Processing;
import interfaces.Response;
import interfaces.WebServer;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.InputResponse;
import responses.OutputResponse;
import responses.ReadResponse;

public class CoordinationComponent implements WebServer {
	
	CompFactor computationComponent;
    Processing processingComponent;
    
    boolean initialize = false; 
    
    //Builder method for making sure the components get initialized properly
    public static Optional<CoordinationComponent> initialize() {
    	CoordinationComponent newComponent = new CoordinationComponent();
    	if (newComponent.coordinationInitializer().getStatus().equals(Response.Status.SUCCESS)) {
    		return Optional.of(newComponent);
    	}
    	return Optional.empty();
    }
    
  //Creates the components as initialized the Computation component
    @Override
    public InitializationResponse coordinationInitializer() {
    	this.computationComponent = new CompEngineComponent();
    	this.processingComponent = new DataStorage();
    	
    	InitializationResponse response = this.computationComponent.initializeComputation();
    	
    	if (response.getStatus().equals(Response.Status.SUCCESS)) {
    		initialize = true;
    	}
    	
    	return response;
    }

    /**Sends the input data to the Process component
	 * Throw an error if the component cannot read the data**/
	@Override
	public InputResponse provideInputSource(InputSource inputSource) throws Exception{
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}
		ReadResponse returned =	processingComponent.readData(inputSource);
		if (returned.getStatus().equals(Response.Status.FAILURE)) {
			System.out.println("InputSource produced error");
			throw new Exception("Input data cannot be read");
		}
		return new InputResponse(returned);
	}

	//Sends the output Destination to the processing component
	@Override
	public OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception{
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}	
		OutputResponse outputSent = processingComponent.getOutputDestination(outputDestination);
		if (outputSent.getStatus().equals(Response.Status.SUCCESS)) {
			return new OutputResponse(outputSent.getStatus(), "Output sent");
		}
		return new OutputResponse(Response.Status.FAILURE, "Output not recievd by processing component");
	}
	
	/**Send the List<> of numbers to be factored to the computation component
	 * it will send back a Response<>. we then check to see if it failed. if
	 * it was a success we take the List<> from inside the response and send it 
	 * to the processing component to be written to the file **/
	public FactorResponse factor(InputResponse nums, String outputPath) {
		StreamSource factorables = new StreamSource(nums.getData());
		FactorResponse factoringResponse = computationComponent.readStream(factorables);
		if (factoringResponse.getStatus().equals(Response.Status.FAILURE)) {
			return factoringResponse;
		}
		DataSource facotoredData = new DataSource(factoringResponse.getData(),outputPath); 
		if ( processingComponent.receiveData(facotoredData).getStatus().equals(factoringResponse.getStatus())) {
			return factoringResponse;
		}
		return new FactorResponse(Response.Status.FAILURE);
	}
}