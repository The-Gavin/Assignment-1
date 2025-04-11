package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Interfaces.CompFactor;
import Interfaces.Processing;
import Interfaces.Response;
import Interfaces.WebServer;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.InputResponse;
import Responses.OutputResponse;
import Responses.ReadResponse;

public class MultiThreadWebServer implements WebServer {

	private static final int NUMBER_OF_THREADS = 10;
	CompFactor computationComponent;
	Processing processingComponent;
	
	boolean initialize = false;
	
	//Builder method for making sure the components get initialized properly
	public static Optional<WebServer> initialize() {
    	MultiThreadWebServer newComponent = new MultiThreadWebServer();
    	if(newComponent.coordinationInitializer().getStatus().equals(Response.Status.SUCCESS)) {
    		return Optional.of(newComponent);
    	}
    	return Optional.empty();
    }
	
	//Creates the components as initialized the Computation component
	@Override
	public InitializationResponse coordinationInitializer() {
		// TODO Auto-generated method stub
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
	public InputResponse provideInputSource(InputSource inputSource) throws Exception {
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}
		ReadResponse returned =	processingComponent.readData(inputSource);
		if(returned.getStatus().equals(Response.Status.FAILURE)) {
			System.out.println("InputSource produced error");
			throw new Exception("Input data cannot be read");
		}
		return new InputResponse(returned);
	}

	//Sends the output Destination to the processing component
	@Override
	public OutputResponse provideOutputDestination(OutputDestination outputDestination) throws Exception {
		if (!initialize) {
			throw new Exception("Component Not Initialized");
		}	
		OutputResponse outputSent = processingComponent.getOutputDestination(outputDestination);
		if(outputSent.getStatus().equals(Response.Status.SUCCESS)) {
			return new OutputResponse(outputSent.getStatus(), "Output sent");
		}
		return new OutputResponse(Response.Status.FAILURE, "Output not recievd by processing component");
	}

	/** R **/
	@Override
	public FactorResponse factor(InputResponse nums, String outputPath) {
		StreamSource factorables = new StreamSource(nums.getData());
		FactorResponse factoringResponse = multiThreadFactoring(factorables);
		if(factoringResponse.getStatus().equals(Response.Status.FAILURE)) {
			return factoringResponse;
		}
		DataSource facotoredData = new DataSource(factoringResponse.getData()); 
		if( processingComponent.receiveData(facotoredData, outputPath).getStatus().equals(factoringResponse.getStatus())) {
			return factoringResponse;
		}
		return new FactorResponse(Response.Status.FAILURE);
	}
	
	public FactorResponse multiThreadFactoring(StreamSource factors) {
		int threadCount = Math.min(NUMBER_OF_THREADS, factors.getData().size());
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		List<Future<FactorResponse>> results = new ArrayList<>();
		Queue<List<Integer>> singleLists = new LinkedList<>();
		FactorResponse toReturn = new FactorResponse(Response.Status.SUCCESS);
		
		for(Integer fact: factors.getData()) {
			List<Integer> holdValue = new ArrayList<>();
			holdValue.add(fact);
			singleLists.add(holdValue);
		}
		
		for(int i = 0; i < threadCount; i++) {
			results.add(threadPool.submit(() -> 
				computationComponent.readStream(
						new StreamSource(singleLists.poll())
				)
			));
		}
		
		results.forEach(future -> {
			try {
				FactorResponse listOfFactors = future.get();
				System.out.println(listOfFactors.getData().toString());
				toReturn.ReciveFactors(listOfFactors.getData().getFirst());
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return toReturn;
	}
	

}
