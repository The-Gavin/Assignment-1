import src.project.annotations.ConceptualAPIPrototype;

public class PrototypeFactor {
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)	{
		//Initialize computation
		InitializationResponse initializationResponse = factoring.initializeComputation(new ComputationComponent());

		//Read integer stream
		ReadStreamResponse readStreamResponse = factoring.readStream(new StreamSource());

		//Write output
		WriteOutputResponse writeOutputResponse = factoring.writeOutput(new DataStore());
	}
}