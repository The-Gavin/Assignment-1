package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import interfaces.Response;
import io.grpc.stub.StreamObserver;
import protos.FactorMachine.InputSource;
import protos.FactorMachine.ReadResponse;
import protos.FactorMachine.Status;
import protos.ProcessingGrpc.ProcessingImplBase;
import protos.ProcessingOuterClass.DataSource;
import protos.ProcessingOuterClass.ReceiveResponse;
import protos.FactorMachine.IntList;
import protos.FactorMachine.OutputDestination;
import protos.FactorMachine.OutputResponse;
import protos.ProcessingOuterClass.StructuredData;
import protos.ProcessingOuterClass.StructuredData.DataMap;
import protos.ProcessingOuterClass.WriteResponse;

public class ProcessingService extends ProcessingImplBase{
	
	@Override
	public void readData(InputSource request, StreamObserver<ReadResponse> responseObserver) {
		// TODO Auto-generated method stub
		ReadResponse parsedData = parseData(request);
		
		responseObserver.onNext(parsedData);
		
		responseObserver.onCompleted();
		
	}
	
	private ReadResponse parseData(InputSource inputSource) {
		List<Integer> list = new ArrayList<>();
		Scanner sc;
		try {
			File inputs = new File(inputSource.getFile());
			sc = new Scanner(inputs);  
		}catch (Exception e) {
			return ReadResponse.newBuilder()
					.setStatus(Status.forNumber(2))
					.build();
		}
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] nums = line.split(",");
			for (String s: nums) {
				try {
					list.add(Integer.parseInt(s));
				} catch (NumberFormatException e) {
					sc.close();
					return ReadResponse.newBuilder().setStatus(Status.FAILURE).build();
				}
			}
		}
		sc.close();
		if (list.isEmpty()) {
			return ReadResponse.newBuilder()
					.setStatus(Status.forNumber(2))
					.build();
		}
		return ReadResponse.newBuilder()
				.addAllData(list)
				.setStatus(Status.forNumber(1))
				.build();
	}
	
	@Override
	public void receiveData(DataSource request, StreamObserver<ReceiveResponse> responseObserver) {
		List<IntList> temp = request.getDataList();
		List<List<Integer>> source = new ArrayList<>();
		for (IntList list: temp) {
			source.add(list.getNumsList());
		}
		
		if (source.isEmpty()) {
			responseObserver.onNext(ReceiveResponse.newBuilder()
					.setStatus(Status.forNumber(2))
					.setData("DataSource contained no data")
					.build());
		}
		
		StructuredData.Builder dataBuilder = StructuredData.newBuilder()
				.setOutputPath(request.getOutputPath());
		
		for (List<Integer> list: source) {
			StructuredData.DataMap mapping = StructuredData.DataMap.newBuilder()
					.setKey(list.get(list.size() - 1))
					.addAllValues(list)
					.build();
			dataBuilder.addData(mapping);
		}
		StructuredData data = dataBuilder.build();
		
		WriteResponse written = writeData(data);
		if (written.getStatus().equals(Status.SUCCESS)) {
			responseObserver.onNext(ReceiveResponse.newBuilder()
					.setStatus(Status.SUCCESS).build());
		}else {
			responseObserver.onNext(ReceiveResponse.newBuilder()
					.setStatus(Status.FAILURE).build());
		}
		responseObserver.onCompleted();
	}
	
	
	public WriteResponse writeData(StructuredData request){
		
		File outputFile = new File(request.getOutputPath());
		List<String> serializedData = new ArrayList<>();
		
		Map<Integer, List<Integer>> data = new HashMap<>();
		
		for (DataMap mapping: request.getDataList()) {
			data.put(mapping.getKey(), mapping.getValuesList());
		}
		 
		for (Integer num: data.keySet()) {
			serializedData.add(serialize(num, data.get(num)));
		}
		StringBuilder output = new StringBuilder();
		for (String s: serializedData) {
			output.append(s);
			output.append("; ");
			output.append(System.lineSeparator());
		}
	
		output.deleteCharAt(output.length()-1);
		
		try {
			outputFile.createNewFile();
			FileWriter writer = new FileWriter(outputFile);
			writer.write(output.toString());
			writer.close();
		} catch (IOException e) {
			return WriteResponse.newBuilder()
					.setStatus(Status.FAILURE)
					.setData(e.toString()).build();
		}
		
		
		return WriteResponse.newBuilder()
				.setStatus(Status.SUCCESS)
				.setData("Factors Written to file @ " + outputFile.getPath())
				.build();
		
	}
	
	private String serialize(int num, List<Integer> factors) {
		StringBuilder serializedData = new StringBuilder(Integer.toString(num));
		serializedData.append(':');
		for (int i: factors) {
			serializedData.append(Integer.toString(i));
			serializedData.append(',');
		}
		serializedData.deleteCharAt(serializedData.length()-1);
		return serializedData.toString();
	}
	
	@Override
	public void getOutputDestination(OutputDestination request, StreamObserver<OutputResponse> responseObserver) {
		// TODO Auto-generated method stub
		responseObserver.onNext(OutputResponse.newBuilder().setData(request.getPath()).build());
		responseObserver.onCompleted();
	}
}
