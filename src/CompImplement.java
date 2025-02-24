
public class CompImplement implements CompFactor {
	Processing processingComponent;
	WebServer webServerComponent;

	//Initialize computation
	public InitializationResponse initializeComputation(ComputationComponent computationComponent) {
		return new InitializationResponse();
	}

	//Read integer stream
	public ReadStreamResponse readStream(StreamSource streamSource) {
		return new ReadStreamResponse();
	}

	//Write output (integer followed by its factors) to data store
	public WriteOutputResponse writeOutput(DataStore dataStore) {
		return new WriteOutputResponse();
	}
}
