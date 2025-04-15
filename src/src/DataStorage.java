package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import interfaces.Processing;
import interfaces.Response;
import responses.OutputResponse;
import responses.ReadResponse;
import responses.ReceiveResponse;
import responses.SendResponse;
import responses.WriteResponse;

public class DataStorage implements Processing{
	
	@Override
	public ReadResponse readData(InputSource inputSource) {
		// TODO Auto-generated method stub
		ReadResponse parsedData = parseData(inputSource);
		if (parsedData.getStatus().equals(Response.Status.FAILURE)) {
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
	public ReceiveResponse receiveData(DataSource source) {
		// TODO Auto-generated method stub
		if (source.getData().isEmpty()) {
			return new ReceiveResponse(Response.Status.FAILURE, "DataSource contained no data");
		}
		StructuredData data = new StructuredData(source, source.getOutput());
		
		WriteResponse written = writeData(data);
		if (written.getStatus().equals(Response.Status.SUCCESS)) {
			return new ReceiveResponse(Response.Status.SUCCESS, "Data has been Written to file");
		}else {
			return new ReceiveResponse(Response.Status.FAILURE, "Data Failed to be Written");
		}
	}

	@Override
	public WriteResponse writeData(StructuredData source) {
		File outputFile = new File(source.getOutput());
		List<String> serializedData = new ArrayList<>();
		for (Integer num: source.getData().keySet()) {
			serializedData.add(serialize(num, source.getData().get(num)));
		}
		StringBuilder output = new StringBuilder();
		for (String s: serializedData) {
			output.append(s);
			output.append("; ");
		}
	
		output.deleteCharAt(output.length()-1);
		
		try {
			FileWriter writer = new FileWriter(outputFile);
			writer.write(output.toString());
			writer.close();
		} catch (IOException e) {
			return new WriteResponse(Response.Status.FAILURE, e.toString());
		}
		return new WriteResponse(Response.Status.SUCCESS, "Factors written to file");
		
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
		return new OutputResponse(Response.Status.SUCCESS, "Received output");
	}

	@Override
	public StreamSource getStream() {
		// TODO Auto-generated method stub
		return new StreamSource(new ArrayList<Integer>());
	}
}
