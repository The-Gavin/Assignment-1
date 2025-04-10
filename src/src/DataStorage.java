package src;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Interfaces.Processing;
import Interfaces.Response;
import Responses.OutputResponse;
import Responses.ReadResponse;
import Responses.ReceiveResponse;
import Responses.SendResponse;
import Responses.WriteResponse;

public class DataStorage implements Processing{
	
	@Override
	public ReadResponse readData(InputSource inputSource) {
		// TODO Auto-generated method stub
		ReadResponse parsedData = parseData(inputSource);
		if(parsedData.getStatus().equals(Response.Status.FAILURE)) {
			return parsedData;
		}
		return parsedData;
	}
	
	private ReadResponse parseData(InputSource inputSource) {
		List<Integer> list = new ArrayList<>();
		Scanner sc;
		try {
			sc = new Scanner(inputSource.getFile());  
		}catch (Exception e) {
			return new ReadResponse(Response.Status.FAILURE);
		}
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] nums = line.split(",");
			for (String s: nums) {
				list.add(Integer.parseInt(s));
			}
		}
		sc.close();
		if (list.isEmpty()) {
			return new ReadResponse(Response.Status.FAILURE);
		}
		return new ReadResponse(list, Response.Status.SUCCESS);
	}

	@Override
	public SendResponse sendDataStream(DataDestination destination) {
		return null;
	}

	@Override
	public ReceiveResponse receiveData(DataSource source, String outputPath) {
		// TODO Auto-generated method stub
		if (source.getData().isEmpty()) {
			return new ReceiveResponse(Response.Status.FAILURE);
		}
		StructuredData data = new StructuredData(source);
		
		writeData(data, outputPath);
		return new ReceiveResponse(Response.Status.SUCCESS);
	}

	@Override
	public WriteResponse writeData(StructuredData source, String outputPath) {
		File outputFile = new File(outputPath);
		List<String> serializedData = new ArrayList<>();
		for (Integer num: source.getData().keySet()) {
			serializedData.add(serialize(num, source.getData().get(num)));
		}
		StringBuilder output = new StringBuilder();
		for (String s: serializedData) {
			output.append(s);
			output.append("; ");
		}
		try {
			output.deleteCharAt(output.length()-1);
		}catch (Exception e) {
			return new WriteResponse(Response.Status.FAILURE);
		}
		try {
			FileWriter writer = new FileWriter(outputFile);
			writer.write(output.toString());
			writer.close();
		} catch (Exception e) {
			return new WriteResponse(Response.Status.FAILURE);
		}
		return new WriteResponse(Response.Status.SUCCESS);
		
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
	
	public OutputResponse getOutputDestination(OutputDestination output) {
		return new OutputResponse(Response.Status.SUCCESS);
	}

	@Override
	public StreamSource getStream() {
		// TODO Auto-generated method stub
		return new StreamSource(new ArrayList<Integer>());
	}
}
