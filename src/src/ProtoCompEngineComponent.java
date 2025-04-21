package src;

import java.util.ArrayList;
import java.util.List;

import interfaces.CompFactor;
import protos.FactorMachine.FactorResponse;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.IntList;
import protos.FactorMachine.Status;

public class ProtoCompEngineComponent {
		
		StreamSource data;
		
		public InitializationResponse initializeComputation() {
			// TODO Auto-generated method stub
			return InitializationResponse.newBuilder()
					.setStatus(Status.SUCCESS)
					.build();
		}

		public FactorResponse readStream(StreamSource streamSource) {
			List<Integer> values = streamSource.getData();
			FactorResponse.Builder messenger = FactorResponse.newBuilder();
			if (values.isEmpty()) {
				return FactorResponse.newBuilder().setStatus(Status.FAILURE).build();
			}
			
			for (int i = 0; i < values.size(); i++) {
				messenger.addFactorLists(
						findFactors(values.get(i)));
			}
			return messenger.build();
		}

		private IntList findFactors(int val){
			List<Integer> factors = new ArrayList<>();
			for (int i = 1; i < val; i++) {
				if (val%i == 0) {
					factors.add(i);
				}
			}
			if (!factors.contains(val)) {
				factors.add(val);
			}
			return IntList.newBuilder().addAllNums(factors).build();
		}
}
