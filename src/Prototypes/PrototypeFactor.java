package Prototypes;

import project.annotations.ConceptualAPIPrototype;
import src.DataStore;
import src.StreamSource;
import Responses.FactorResponse;
import Responses.InitializationResponse;
import Responses.WriteOutputResponse;
import Interfaces.CompFactor;

public class PrototypeFactor {
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)	{
		//Initialize computation
		InitializationResponse initializationResponse = factoring.initializeComputation();

		//Read integer stream
		FactorResponse factorResponse = factoring.readStream(new StreamSource());

		//Write output
		WriteOutputResponse writeOutputResponse = factoring.writeOutput(new DataStore());
	}
}