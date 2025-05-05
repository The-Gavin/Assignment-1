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
import protos.FactorMachine.ReadResponse;
import protos.ProcessingGrpc;
import protos.ProcessingGrpc.ProcessingBlockingStub;
import protos.ProcessingOuterClass.DataDestination;
import protos.ProcessingOuterClass.DataSource;
import protos.ProcessingOuterClass.ReceiveResponse;
import protos.ProcessingOuterClass.SendResponse;
import protos.ProcessingOuterClass.StructuredData;
import protos.ProcessingOuterClass.WriteResponse;

public class DataStorageClient {
    private final ProcessingBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public DataStorageClient(Channel channel) {
        blockingStub = ProcessingGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }
    
    public ReadResponse readData(InputSource inputSource) {
    	ReadResponse response = null;
    	try {
    		response = blockingStub.readData(inputSource);
    	} catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    	if (response.getStatus().equals(Response.Status.FAILURE)){
    		System.err.println("Error Failed to read data");
    	}else {
    		System.out.println("Data successesfuly read");
    	}
    	return response;
    }
    
    public ReceiveResponse receiveData(DataSource source) {
    	ReceiveResponse response = null;
    	try {
    		response = blockingStub.receiveData(source);
    	} catch (StatusRuntimeException e) {
    		e.printStackTrace();
    	}
    	if (response.getStatus().equals(Response.Status.FAILURE)){
    		System.err.println("Error Failed to read data");
    	}else {
    		System.out.println("Data successesfuly read");
    	}
    	return response;
    }

	public OutputResponse getOutputDestination(OutputDestination outputdestination) {
		OutputResponse response = null;
		try {
			response = blockingStub.getOutputDestination(outputdestination);
		}catch(StatusRuntimeException e) {
			e.printStackTrace();
		}
		return response;
	}
    //Pass data in form of integer stream to computation component
    public SendResponse sendDataStream(DataDestination destination) {
    	return SendResponse.newBuilder().build();
    }
}
