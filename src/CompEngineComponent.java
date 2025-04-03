
public class CompEngineComponent implements CompFactor {
	
	ComputationComponent computationComponent; 
	StreamSource data;
	OutputData outputData;
	//Initializes ComputationComponent to later receive data from StreamSource
	@Override
	public InitializationResponse initializeComputation(ComputationComponent computationComponent) {
		// TODO Auto-generated method stub
		this.computationComponent = computationComponent;
		return this.computationComponent.initialize();
	}

	//Verifies streamSource has data and sets this.data to that data
	@Override
	public ReadStreamResponse readStream(StreamSource streamSource) {
		// TODO Auto-generated method stub
		ReadStreamResponse check = streamSource.containsData();
		if(check.equals(Response.Status.FAILURE)){
			return check;
		}
		this.data = streamSource;
		return check;
	}
	
	//Sends computed data to the supplied Data Store
	@Override
	public WriteOutputResponse writeOutput(DataStore dataStore) {
		return dataStore.recieveData(outputData);
	}

}
