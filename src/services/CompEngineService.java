package services;

import java.util.ArrayList;
import java.util.List;

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
			messenger.addFactorLists(findFactors(values.get(i)));
		}
		
		 responseObserver.onNext(messenger.build());
		 responseObserver.onCompleted();
	}

	private IntList findFactors(int val){
		List<Integer> factors = new ArrayList<>();
		for (int i = 1; i < val/2; i++) {
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
