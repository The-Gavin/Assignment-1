package implementations;

import interfaces.CompFactor;
import interfaces.Processing;
import interfaces.Response;
import interfaces.WebServer;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.WriteOutputResponse;
import src.DataStore;
import src.StreamSource;

public class CompImplement implements CompFactor {
	Processing processingComponent;
	WebServer webServerComponent;

	//Initialize computation
	public InitializationResponse initializeComputation() {
		return new InitializationResponse(Response.Status.SUCCESS, "");
	}

	//Read integer stream
	public FactorResponse readStream(StreamSource streamSource) {
		return new FactorResponse(Response.Status.SUCCESS);
	}

	//Write output (integer followed by its factors) to data store
	public WriteOutputResponse writeOutput(DataStore dataStore) {
		return new WriteOutputResponse(Response.Status.SUCCESS, "");
	}
}
