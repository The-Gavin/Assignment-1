import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface CompFactor {
	//Initialize computation
	InitializationResponse initializeComputation(ComputationComponent computationComponent);

	//Read integer stream
	ReadStreamResponse readStream(StreamSource streamSource);

	//Write output (integer followed by its factors) to data store
	WriteOutputResponse writeOutput(DataStore dataStore);
}