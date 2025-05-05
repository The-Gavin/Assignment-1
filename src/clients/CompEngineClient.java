package clients;

import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import protos.CompEngineGrpc;
import protos.CompEngineGrpc.CompEngineBlockingStub;
import protos.FactorMachine.InitializationResponse;
import protos.FactorMachine.StreamSource;
import protos.FactorMachine.Empty;
import protos.FactorMachine.FactorResponse;


public class CompEngineClient {
    private final CompEngineBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public CompEngineClient(Channel channel) {
        blockingStub = CompEngineGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }
    
    
    public InitializationResponse initializeComutation() {
    	InitializationResponse response = null;
    	try {
    	response = blockingStub.initializeComputation(Empty.newBuilder().build());
    	}catch(StatusRuntimeException e){
    		e.printStackTrace();
    	}
    	return response;
    }
    
    public FactorResponse readStream(StreamSource streamSource) {
    	FactorResponse response = null;
    	try {
    	response = blockingStub.readStream(streamSource);
    	}catch(StatusRuntimeException e){
    		e.printStackTrace();
    	}
    	return response;
    }
}
