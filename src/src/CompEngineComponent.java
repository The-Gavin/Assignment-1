package src;

import Interfaces.CompFactor;
import Interfaces.Response;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.WriteOutputResponse;

public class CompEngineComponent implements CompFactor {
	
	ComputationComponent computationComponent; 
	StreamSource data;
	OutputData outputData;
	
	//Initializes ComputationComponent to later receive data from StreamSource
	@Override
	public InitializationResponse initializeComputation() {
		// TODO Auto-generated method stub
		this.computationComponent = new ComputationComponent();
		return this.computationComponent.initialize();
	}

	@Override
	public FactorResponse readStream(StreamSource streamSource) {
		return new FactorResponse(Response.Status.SUCCESS);
	}

	@Override
	public WriteOutputResponse writeOutput(DataStore dataStore) {
		// TODO Auto-generated method stub
		return null;
	}


}
