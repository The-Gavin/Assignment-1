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
    	//Gets either input file location or input data from the user
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
    	String path = null;
		while (path == null) {
			System.out.println("\nHow would you like to input your data?"
				+ "\n1. Manual input \n2. Provie file path\nEnter a number(1, 2): ");
			Scanner sc = new Scanner(System.in);
			switch (sc.next()) {
			// If user wants to enter numbers
			case "1": {
				StringBuilder userInput = new StringBuilder();
				System.out.println("Please input numbers (input \'-\' to end): ");
				while (true) {
					String input = sc.next();
					if (input.equals("-")) {
						break;
					}else {
						try {
							int numInput = Integer.parseInt(input);
							userInput.append(numInput + ",");
						}catch (NumberFormatException e) {
							System.out.println(input + " is not a number, ignoring input please continue inputting numbers (\'-\' to stop)");
						}
					}
				}
				//makes temp file that will be deleted on exit and stores inputs there
				List<Integer> userNumbers = new ArrayList<>();
				File tempFile = new File("." + File.separatorChar + "input.csv");
				tempFile.deleteOnExit();
				
				try {
					System.out.println("Numbers given: " + userInput + "\nCreating temporary file");
					tempFile.createNewFile();
					FileWriter encoder = new FileWriter(tempFile);
					encoder.write(userInput.toString());
					path = tempFile.getPath();
					encoder.close();
				}catch (IOException e) {
					System.out.println("Unable to create temporary file");
				}
				
				break;
			}
			case "2": {
				System.out.println("Please Provide the file path:");
				path = sc.next();
				if (!(new File(path).exists())) {
					path = null;
					System.out.println("File could not be found please enter a valid path");
				}
				break;
			}
			default:
				System.out.print("Please choose a proper option");
				break;
			}
		}
		return path;
	}
    
    public OutputResponse provideOutputDestination() {
    	String path = getOutput();
    	OutputDestination destination = OutputDestination.newBuilder()
    			.setPath(path).build();
    	OutputResponse response = null;
    	
    	try {
    		response = blockingStub.provideOutputDestination(destination);
    	}catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    	
    	return response;
    }
    
    //Gets output file path or makes output file;
    private String getOutput() {
    	System.out.println("Enter a output Destination: ");
    	String path = null;
    	while (path == null) {
    		Scanner sc = new Scanner(System.in);
    		String userInput = sc.next();
    		if (new File(userInput).exists()) {
    			while (true) {
    				System.out.println("do you wish to overwrite " + userInput + " and fill with factors? (Y/n)");
    				String confirmation = sc.next();
    				if (confirmation.equals("Y")) {
    					System.out.println("File will be overwritten");
    					path = userInput;
    					break;
    				}else if (confirmation.equals("n")) {
    					path = makeSecondFile(userInput);
    					System.out.println("Will save to " + path + " instead");
    					break;
    				}else {
    					System.out.println("Please give a valid answer");
    				}
    			}
    		}else {
    			path = userInput;
    		}
    		sc.close();
    	}
    	
    	return path;
    }

    //If user does not wish to override file we make a new file same name with suffix
	private String makeSecondFile(String userInput) {
		//checks if user provided file extension and splits name from extension
		String[] splits = userInput.split("\\.");
		String name = splits[0];
		String ext = "";
		if (splits.length > 1) {
			ext = "." + splits[splits.length-1];
		}
		
		//checks if file already has numerical suffix adds one if there isn't, increments if there is
		int leftParentheses = name.lastIndexOf("(");
		int rightParentheses = name.lastIndexOf(")");
		if (leftParentheses == -1 || rightParentheses == -1) {
			name+="(1)";
		}else {
			try {
				int oldVersion = Integer.parseInt(
						name.substring(leftParentheses+1, rightParentheses));
				int newVersion = oldVersion+1;
				
				name = name.replace(String.valueOf(oldVersion), String.valueOf(newVersion));
			}catch (NumberFormatException e) {
					//if file name has non numeric values between parentheses
					name += (1);
			}
		}
		return name + ext;
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
        		throw new Exception("Bad input data please try again");
        	}
        	System.out.println("Inputs recieved and process");
        	OutputResponse output = client.provideOutputDestination();
        	System.out.println("About to begin processing this could take a while, given " + inputs.getDataCount() + " numbers to be factored");
        	client.factor(inputs, output);
        	System.out.println("Factors found and stored in " + output.getData());
        } catch (Exception e) {
        	System.out.println(e);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
