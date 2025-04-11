package src;

import java.util.ArrayList;
import java.util.List;

import interfaces.CompFactor;
import interfaces.Response;
import responses.FactorResponse;
import responses.InitializationResponse;

public class CompEngineComponent implements CompFactor {
	
	ComputationComponent computationComponent; 
	StreamSource data;
	
	//Initializes ComputationComponent to later receive data from StreamSource
	@Override
	public InitializationResponse initializeComputation() {
		// TODO Auto-generated method stub
		this.computationComponent = new ComputationComponent();
		return this.computationComponent.initialize();
	}

	@Override
	public FactorResponse readStream(StreamSource streamSource) {
		List<Integer> values = streamSource.getData();
		FactorResponse messenger = new FactorResponse(Response.Status.SUCCESS);
		if (values.isEmpty()) {
			return new FactorResponse(Response.Status.FAILURE);
		}
		
		for (int i = 0; i < values.size(); i++) {
			if (messenger.reciveFactors(findFactors(values.get(i))).getStatus().equals(Response.Status.FAILURE)) {
				return messenger;
			}
		}
		return messenger;
	}

	private List<Integer> findFactors(int val){
		List<Integer> factors = new ArrayList<>();
		for (int i = 1; i < val; i++) {
			if (val%i == 0) {
				factors.add(i);
			}
		}
		if (!factors.contains(val)) {
			factors.add(val);
		}
		return factors;
	}
}
