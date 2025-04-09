package Interfaces;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.WriteOutputResponse;
import project.annotations.ConceptualAPI;
import src.StreamSource;
import src.DataStore;

@ConceptualAPI
public interface CompFactor {
	//Initialize computation
	InitializationResponse initializeComputation();

	//Read integer stream
	FactorResponse readStream(StreamSource streamSource);

	//Write output (integer followed by its factors) to data store
	
}