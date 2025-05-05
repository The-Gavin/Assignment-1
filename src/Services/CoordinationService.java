package Services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import clients.CompEngineClient;
import clients.DataStorageClient;
import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import protos.CoordinationServiceGrpc.CoordinationServiceImplBase;
import protos.FactorMachine.InitializationRequest;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.InputResponse;
import protos.FactorMachine.InputSource;
import protos.FactorMachine.ReadResponse;
import protos.FactorMachine.OutputDestination;
import protos.FactorMachine.OutputResponse;
import protos.FactorMachine.Status;
import protos.FactorMachine.FactorRequest;
import protos.FactorMachine.FactorResponse;
import protos.FactorMachine.StreamSource;
import protos.ProcessingOuterClass.DataSource;
import protos.ProcessingOuterClass.ReceiveResponse;

public class CoordinationService extends CoordinationServiceImplBase{
	
	private static final int NUMBER_OF_THREADS = 20;
	private CompEngineClient computationClient;
	private DataStorageClient dataClient;
	
	boolean initialize = false;

	@Override
	public void coordinationInitializer(InitializationRequest request,
		StreamObserver<InitializationResponse> responseObserver) {
		
		String compTarget = "localHost:50053";
		ChannelCredentials compCreds = InsecureChannelCredentials.create();
        ManagedChannelBuilder<?> compTemp = Grpc.newChannelBuilder(compTarget, compCreds);
        ManagedChannel compChannel = compTemp.build();
		
		this.computationClient = new CompEngineClient(compChannel);
		
		String dataTarget = "localHost:50052";
		ChannelCredentials dataCreds = InsecureChannelCredentials.create();
        ManagedChannelBuilder<?> temp = Grpc.newChannelBuilder(dataTarget, dataCreds);
        ManagedChannel dataChannel = temp.build();
        
		this.dataClient = new DataStorageClient(dataChannel);
	    	
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
		returned = dataClient.readData(request);
		
		if (returned.getStatus().equals(Status.SUCCESS)) {
			response = InputResponse.newBuilder()
					.setStatus(Status.forNumber(1))
					.addAllData(returned.getDataList())
					.build();
		} else {
			response = InputResponse.newBuilder()
					.setStatus(Status.FAILURE)
					.build();
		}
		
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
	
	@Override
	public void provideOutputDestination(OutputDestination request, StreamObserver<OutputResponse> responseObserver) {
		
		OutputResponse outputSent = dataClient.getOutputDestination(request);
		
		
		//OutputResponse response;
		//if (outputSent.getStatus().equals(Response.Status.SUCCESS)) {
			//response = OutputResponse.newBuilder().setStatus(Status.forNumber(1)).setData("Client recieved outputDestination").build();
		/*}else {
			response = OutputResponse.newBuilder()
					.setStatus(Status.forNumber(2))
					.setData("Client could not recieve outputDestination")
					.build();
		}*/
		responseObserver.onNext(outputSent);
		responseObserver.onCompleted();
	}
	
	@Override
	public void factor(FactorRequest request,
	        StreamObserver<FactorResponse> responseObserver) {
		StreamSource factorables = StreamSource.newBuilder().addAllValues(request.getData().getDataList()).build();
		FactorResponse factoringResponse = multiThreadFactoring(factorables);
		
		if (factoringResponse.getStatus().getNumber() != 1) {
			responseObserver.onNext(factoringResponse);
		}
		DataSource factoredData = DataSource.newBuilder()
				.addAllData(factoringResponse.getFactorListsList())
				.setOutputPath(request.getPath())
				.build(); 
		 ReceiveResponse dataWritten = dataClient.receiveData(facotoredData);
			
		responseObserver.onNext(FactorResponse.newBuilder().setStatus(Status.forNumber(1)).build());
		responseObserver.onCompleted();
		
	}
	
	public FactorResponse multiThreadFactoring(StreamSource factors) throws RuntimeException{
		int threadCount = Math.min(NUMBER_OF_THREADS, factors.getValuesCount());
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		List<Future<?>> results = new ArrayList<>();
		Queue<List<Integer>> singleLists = new LinkedList<>();
		FactorResponse.Builder toReturn = FactorResponse.newBuilder().setStatus(Status.forNumber(1));
		
		List<Integer> data = factors.getValuesList();
		List<List<Integer>> slices = splitList(data);
		
		//makes list of single values need to adjust to split list into sublists
		for (List<Integer> nums: slices) {
			singleLists.add(nums);
		}
		
		System.out.println("Sending " + singleLists.size() + " Slices to be factored");
		for (int i = 0; i < threadCount; i++) {
			StreamSource number = StreamSource.newBuilder().addAllValues(singleLists.poll()).build(); 
			results.add(threadPool.submit(() -> 
				computationClient.readStream(number)));
		}
		
		results.forEach(future -> {
			try {
				FactorResponse listOfFactors = (FactorResponse) future.get();
				System.out.println("list of Factors recieved");
				toReturn.addFactorLists(listOfFactors.getFactorLists(0));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		return toReturn.build();
	}
	
	private List<List<Integer>> splitList(List<Integer> data){
		int start = 0;
		int interval = Math.max(data.size() / NUMBER_OF_THREADS, 1);
		int end = start + interval;
		List<List<Integer>> subLists = new ArrayList<>();
		
		while (end <= data.size()) {
			
			subLists.add(data.subList(start, end));
			start = end;
			if (end + interval < data.size() || end == data.size()) {
				end += interval;
			}else {
				end = data.size();
			}
		}
		return subLists;
	}
}
