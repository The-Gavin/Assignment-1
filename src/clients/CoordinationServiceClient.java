package clients;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import protos.FactorMachine.Status;

public class CoordinationServiceClient {
    private final CoordinationServiceBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public CoordinationServiceClient(Channel channel) {
        blockingStub = CoordinationServiceGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }
    
    public InputResponse provideInputSource() {
    	String userInput = getInputs();
    	
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
    
    public String getInputs() {
    	StringBuilder userInput = new StringBuilder();
    	String path = null;
		while (path == null) {
			System.out.println("\nHow would you like to input your data?"
				+ "\n1. Manual input \n2. Provie file path\nEnter a number(1, 2): ");
			Scanner sc = new Scanner(System.in);
			switch (sc.nextInt()) {
			case 1: {
				System.out.println("Please input numbers (input \'-\' to end): ");
				while(true) {
					String input = sc.next();
					if(input.equals("-")) {
						break;
					}else {
						try {
							int numInput = Integer.parseInt(input);
							userInput.append(numInput + ",");
						}catch(NumberFormatException e) {
							System.out.print(input + " is not a number, ignoring input please continue inputting numbers (\'-\' to stop)");
						}
					}
				}
				List<Integer> userNumbers = new ArrayList<>();
				
				
				File tempFile = new File("." + File.separatorChar + "input.csv");
				
				tempFile.deleteOnExit();
				
				try {
					System.out.println("Numbers given: " + userInput);
					tempFile.createNewFile();
					FileWriter encoder = new FileWriter(tempFile);
					encoder.write(userInput.toString());
					path = tempFile.getPath();
					encoder.close();
				}catch (IOException e) {
					System.out.print(e.toString());
				}
				
				break;
			}
			case 2: {
				System.out.println("Please Provide the file path:");
				path = sc.next();
				if (!(new File(path).exists())) {
					path = null;
					System.out.println("File could not be found please enter a valid path");
				}
				break;
			}
			default:
				System.out.print("Please Choose a proper option(1,2)");
				break;
			}
		}
		return path;
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
    	if (response.getStatus().getNumber() == 1) {
    		System.out.println("Server connection established! \nComponents initialized.");
    	}
    }
    
    public void factor(InputResponse inputs, OutputResponse outputs) {
    	FactorRequest request = FactorRequest.newBuilder()
    			.setData(inputs)
    			.setPath(outputs.getData())
    			.build();
    	FactorResponse response;
    	
    	try {
    		response = blockingStub.factor(request);
    	}catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to
        long startTime = 0;
        ChannelCredentials creds = InsecureChannelCredentials.create();
        ManagedChannelBuilder<?> temp = Grpc.newChannelBuilder(target, creds);
        ManagedChannel channel = temp.build();
        try {
        	CoordinationServiceClient client = new CoordinationServiceClient(channel); // Boilerplate TODO: update to this class name
            client.coordinationInitializer();
        	InputResponse inputs = client.provideInputSource();
        	if (inputs.getStatus().equals(Status.FAILURE)) {
        		throw new Exception("Bad input data");
        	}
        	System.out.println("Inputs recieved and process");
        	OutputResponse output = client.provideOutputDestination();
        	System.out.print("About to begin processing this could take a while \n " + inputs.getDataCount() + " numbers to be factored");
        	startTime = System.currentTimeMillis();
        	client.factor(inputs, output);
        } catch (Exception e) {
        	System.out.println(e);
        } finally {
        	System.out.println(System.currentTimeMillis() - startTime);
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
