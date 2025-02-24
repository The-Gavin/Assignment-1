import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface CompFactor {
	//Initialize computation
	InitializationResponse initializeComputation();

	//Read integer stream
	ReadStreamResponse readStream();

	//Write output (integer followed by its factors) to data store
	WriteOutputResponse writeOutput();
}