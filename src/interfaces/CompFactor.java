package interfaces;
import project.annotations.ConceptualAPI;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.WriteOutputResponse;
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