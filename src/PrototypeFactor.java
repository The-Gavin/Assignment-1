import project.annotations.ConceptualAPIPrototype;

public class PrototypeFactor {
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)	{
		//Initialize computation
		InitializationResponse initializationResponse = factoring.initializeComputation();

		//Read integer stream
		ReadStreamResponse readStreamResponse = factoring.readStream();

		//Write output
		WriteOutputResponse writeOutputResponse = factoring.writeOutput();
	}
}