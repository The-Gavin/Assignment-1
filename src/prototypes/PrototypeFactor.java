package prototypes;

import project.annotations.ConceptualAPIPrototype;
import responses.FactorResponse;
import responses.InitializationResponse;
import responses.WriteOutputResponse;
import src.DataStore;
import src.StreamSource;

import java.util.ArrayList;

import interfaces.CompFactor;

public class PrototypeFactor {
	@ConceptualAPIPrototype
	public void prototype(CompFactor factoring)	{
		//Initialize computation
		InitializationResponse initializationResponse = factoring.initializeComputation();

		//Read integer stream
		FactorResponse factorResponse = factoring.readStream(new StreamSource(new ArrayList<Integer>()));
	}
}