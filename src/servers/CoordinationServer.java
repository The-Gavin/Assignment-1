package servers;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import clients.DataStorageClient;
import io.grpc.ChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.InsecureServerCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.channelz.v1.Channel;
import io.grpc.protobuf.services.ProtoReflectionService;
import protos.CoordinationServiceGrpc.CoordinationServiceImplBase;
import protos.ProcessingGrpc.ProcessingImplBase;
import src.CoordinationService;
import src.ProcessingService;

public class CoordinationServer { // Boilerplate TODO: Change name of class
      private Server server;
      private DataStorageClient dataClient;
      
      private void start() throws IOException {
        
        int port = 50051;
        
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
        	.addService(new CoordinationService().bindService()) // Boilerplate TODO: Change name of class
            .addService(ProtoReflectionService.newInstance())
            .build()
            .start();
        System.out.println("Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                if (server != null) {
                  server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
              e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
          }
        });
      }

      /**
       * Await termination on the main thread since the grpc library uses daemon threads.
       */
      private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
          server.awaitTermination();
        }
      }

      public static void main(String[] args) throws Exception {
          CoordinationServer server = new CoordinationServer();
          server.start();
          server.blockUntilShutdown();
      }
}
