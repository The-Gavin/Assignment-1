package src;

import java.util.ArrayList;
import java.util.List;

import Interfaces.CompFactor;
import Interfaces.Response;
import Responses.FactorResponse;
import Responses.InitializationResponse;

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
		List<Integer> values = streamSource.values;
		FactorResponse messenger = new FactorResponse(Response.Status.SUCCESS);
		if(values.isEmpty()) {
			return new FactorResponse(Response.Status.FAILURE);
		}
		
		for(int i = 0; i < values.size(); i++) {
			if(messenger.ReciveFactors(findFactors(values.get(i))).getStatus().equals(Response.Status.FAILURE)) {
				return messenger;
			}
		}
		return messenger;
	}

	private List<Integer> findFactors(int val){
		List<Integer> factors = new ArrayList<>();
		factors.add(1);
		for(int i = 2; i < val; i++) {
			if(val%i == 0) {
				factors.add(i);
			}
		}
		factors.add(val);
		return factors;
	}
}
