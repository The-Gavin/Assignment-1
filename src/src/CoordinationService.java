package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import clients.CoordinationServiceClient;
import interfaces.CompFactor;
import interfaces.Processing;
import interfaces.Response;
import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import protos.CoordinationServiceGrpc;
import protos.CoordinationServiceGrpc.*;
import protos.FactorMachine.InitializationRequest;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.InputResponse;
import protos.FactorMachine.InputSource;
import protos.FactorMachine.ReadResponse;
import protos.FactorMachine.OutputDestination;
import protos.FactorMachine.OutputResponse;
import protos.FactorMachine.Status;
import protos.ProcessingGrpc;
import protos.ProcessingGrpc.ProcessingBlockingStub;
import protos.ProcessingOuterClass;
import protos.FactorMachine.FactorRequest;
import protos.FactorMachine.FactorResponse;

import protos.ProcessingOuterClass.DataSource;
import protos.ProcessingOuterClass.ReceiveResponse;

public class CoordinationService extends CoordinationServiceImplBase{
	
	private static final int NUMBER_OF_THREADS = 20;
	ProtoCompEngineComponent computationComponent;
	ProcessingBlockingStub processingComponent;
	
	boolean initialize = false;

	@Override
	public void coordinationInitializer(InitializationRequest request,
		StreamObserver<InitializationResponse> responseObserver) {
		this.computationComponent = new ProtoCompEngineComponent();
		
		String target = "localHost:50051";
		ChannelCredentials creds = InsecureChannelCredentials.create();
        ManagedChannelBuilder<?> temp = Grpc.newChannelBuilder(target, creds);
        ManagedChannel channel = temp.build();
        
		this.processingComponent = ProcessingGrpc.newBlockingStub(channel);
	    	
	    InitializationResponse response = InitializationResponse.newBuilder()
	    		.setStatus(Status.forNumber(1)).build();
		if (response.getStatus().getNumber() == 1) {
	    	initialize = true;
	    	System.out.println("initialized");
	    }
			
	    responseObserver.onNext(response);
	    responseObserver.onCompleted();		
	}
	
	@Override
	public void provideInputSource(InputSource request, StreamObserver<InputResponse> responseObserver) {
		ReadResponse returned = null;
		returned = processingComponent.readData(request);
		
		
		InputResponse response = InputResponse.newBuilder()
				.setStatus(Status.forNumber(1))
				.addAllData(returned.getDataList())
				.build();
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	@Override
	public void provideOutputDestination(OutputDestination request, StreamObserver<OutputResponse> responseObserver) {
		
		OutputResponse outputSent = processingComponent.getOutputDestination(request);
		
		
		//OutputResponse response;
		//if (outputSent.getStatus().equals(Response.Status.SUCCESS)) {
			//response = OutputResponse.newBuilder().setStatus(Status.forNumber(1)).setData("Component recieved outputDestination").build();
		/*}else {
			response = OutputResponse.newBuilder()
					.setStatus(Status.forNumber(2))
					.setData("Component could not recieve outputDestination")
					.build();
		}*/
		responseObserver.onNext(outputSent);
		responseObserver.onCompleted();
	}
	
	@Override
	public void factor(FactorRequest request,
	        StreamObserver<FactorResponse> responseObserver) {
		StreamSource factorables = new StreamSource(request.getData().getDataList());
		FactorResponse factoringResponse = multiThreadFactoring(factorables);
		
		if (factoringResponse.getStatus().getNumber() != 1) {
			responseObserver.onNext(factoringResponse);
		}
		DataSource facotoredData = DataSource.newBuilder()
				.addAllData(factoringResponse.getFactorListsList())
				.setOutputPath(request.getPath())
				.build(); 
		 ReceiveResponse dataWritten = processingComponent.receiveData(facotoredData);
			
		responseObserver.onNext(FactorResponse.newBuilder().setStatus(Status.forNumber(1)).build());
		responseObserver.onCompleted();
		
	}
	
	public FactorResponse multiThreadFactoring(StreamSource factors) throws RuntimeException{
		int threadCount = Math.min(NUMBER_OF_THREADS, factors.getData().size());
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		List<Future<?>> results = new ArrayList<>();
		Queue<List<Integer>> singleLists = new LinkedList<>();
		FactorResponse.Builder toReturn = FactorResponse.newBuilder().setStatus(Status.forNumber(1));
		
		List<Integer> data = factors.getData();
		List<List<Integer>> slices = splitList(data);
		
		//makes list of single values need to adjust to split list into sublists
		for (List<Integer> nums: slices) {
			singleLists.add(nums);
		}
		
		for (int i = 0; i < threadCount; i++) {
			StreamSource number = new StreamSource(singleLists.poll());
			results.add(threadPool.submit(() -> 
				computationComponent.readStream(number)));
		}
		
		
		results.forEach(future -> {
			try {
				FactorResponse listOfFactors = (FactorResponse) future.get();
				toReturn.addFactorLists(listOfFactors.getFactorLists(0));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		threadPool.close();
		return toReturn.build();
	}
	
	private List<List<Integer>> splitList(List<Integer> data){
		int start = 0;
		int interval = data.size() / NUMBER_OF_THREADS;
		int end = start + interval;
		List<List<Integer>> subLists = new ArrayList<>();
		
		while(end <= data.size()) {
			
			subLists.add(data.subList(start, end));
			start = end;
			if(end + interval < data.size() || end == data.size()) {
				end += interval;
			}else {
				end = data.size();
			}
		}
		
		return subLists;
	}
}
