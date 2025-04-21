package clients;


import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import interfaces.Response;
import io.grpc.Channel;
import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import protos.CoordinationServiceGrpc;
import protos.CoordinationServiceGrpc.CoordinationServiceBlockingStub;
import protos.FactorMachine.FactorRequest;
import protos.FactorMachine.FactorResponse;
import protos.FactorMachine.InitializationRequest;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.InputResponse;
import protos.FactorMachine.InputSource;
import protos.FactorMachine.OutputDestination;
import protos.FactorMachine.OutputResponse;

public class CoordinationServiceClient {
    private final CoordinationServiceBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public CoordinationServiceClient(Channel channel) {
        blockingStub = CoordinationServiceGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }
    
    public InputResponse provideInputSource() {
    	System.out.print("Enter input file path: ");
    	String userInput = new Scanner(System.in).next();
    	InputSource source = InputSource.newBuilder().setFile(userInput).build();
    	InputResponse response = null;
    	
    	try {
    		response = blockingStub.provideInputSource(source);
    	} catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    	if (response.getStatus().equals(Response.Status.FAILURE)){
    		System.err.println("Error InputResponse Failed");
    	}else {
    		System.out.println("Input file at " + source.getFile());
    	}
    	return response;
    }
    
    public OutputResponse provideOutputDestination() {
    	System.out.print("Enter a output Destination: ");
    	String path = new Scanner(System.in).next();
    	OutputDestination destination = OutputDestination.newBuilder()
    			.setPath(path).build();
    	OutputResponse response = null;
    	
    	try {
    		response = blockingStub.provideOutputDestination(destination);
    	}catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    	
    	System.out.println(response.getData());
    	return response;
    }
    
    public void coordinationInitializer() {
    	InitializationRequest request = InitializationRequest.newBuilder().build();
    	InitializationResponse response;
    	
    	try {
    		response = blockingStub.coordinationInitializer(request);
    	}catch (StatusRuntimeException e) {
    		e.printStackTrace();
    		return;
    	}
    	if(response.getStatus().getNumber() == 1) {
    		System.out.println("Server connection established! \nComponents initialized.");
    	}
    }
    
    public void factor(InputResponse inputs,OutputResponse outputs) {
    	FactorRequest request = FactorRequest.newBuilder()
    			.setData(inputs)
    			.setPath(outputs.getData())
    			.build();
    	FactorResponse response;
    	
    	try {
    		response = blockingStub.factor(request);
    	}catch(StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ChannelCredentials creds = InsecureChannelCredentials.create();
        ManagedChannelBuilder<?> temp = Grpc.newChannelBuilder(target, creds);
        ManagedChannel channel = temp.build();
        try {
        	CoordinationServiceClient client = new CoordinationServiceClient(channel); // Boilerplate TODO: update to this class name
            client.coordinationInitializer();
        	InputResponse inputs = client.provideInputSource();
        	OutputResponse output = client.provideOutputDestination();
        	client.factor(inputs, output);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
