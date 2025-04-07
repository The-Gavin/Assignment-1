package Implementations;

import Interfaces.CompFactor;
import Interfaces.Processing;
import Interfaces.WebServer;
import Interfaces.Response;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.WriteOutputResponse;
import src.DataStore;
import src.StreamSource;

public class CompImplement implements CompFactor {
	Processing processingComponent;
	WebServer webServerComponent;

	//Initialize computation
	public InitializationResponse initializeComputation() {
		return new InitializationResponse(Response.Status.SUCCESS);
	}

	//Read integer stream
	public FactorResponse readStream(StreamSource streamSource) {
		return new FactorResponse(Response.Status.SUCCESS);
	}

	//Write output (integer followed by its factors) to data store
	public WriteOutputResponse writeOutput(DataStore dataStore) {
		return new WriteOutputResponse(Response.Status.SUCCESS);
	}
}
