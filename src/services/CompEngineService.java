package services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.grpc.stub.StreamObserver;
import protos.FactorMachine.FactorResponse;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.IntList;
import protos.FactorMachine.Status;
import protos.FactorMachine.StreamSource;
import protos.CompEngineGrpc.CompEngineImplBase;
import protos.FactorMachine.Empty;

public class CompEngineService extends CompEngineImplBase{
	public void initializeComputation(Empty empty, StreamObserver<InitializationResponse> responseObserver) {
		// TODO Auto-generated method stub
		responseObserver.onNext(InitializationResponse.newBuilder()
				.setStatus(Status.SUCCESS)
				.build());
		responseObserver.onCompleted();
	}

	public void readStream(StreamSource request, StreamObserver<FactorResponse> responseObserver) {
		List<Integer> values = request.getValuesList();
		FactorResponse.Builder messenger = FactorResponse.newBuilder();
		if (values.isEmpty()) {
			FactorResponse response = FactorResponse.newBuilder().setStatus(Status.FAILURE).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		for (int i = 0; i < values.size(); i++) {
			int num = values.get(i);
			IntList.Builder factors = findFactors(Math.abs(num));
			if(num < 0) {
				List<Integer> negativeFactors = factors.getNumsList();
				negativeFactors = negativeFactors.stream().map(fact -> fact * -1).collect(Collectors.toList());
				factors.addAllNums(negativeFactors);
			}
			factors.build();
			messenger.addFactorLists(factors);
		}
		
		 responseObserver.onNext(messenger.build());
		 responseObserver.onCompleted();
	}

	private IntList.Builder findFactors(int val){
		List<Integer> factors = new ArrayList<>();
		factors.add(1);
		int di = 1;
		if (val%2 != 0) {
			di = 2;
		}else {
			factors.add(2);
		}
		for (int i = 3; i < (val/2)+1; i+=di) {
			if (val%i == 0) {
				factors.add(i);
			}
		} 
		if (!factors.contains(val)) {
			factors.add(val);
		}
		return IntList.newBuilder().addAllNums(factors);
	}	
}
