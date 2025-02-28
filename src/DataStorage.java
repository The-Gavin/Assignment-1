package src;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import src.Response.Status;

public class DataStorage implements Processing{

	private Map<Integer, List<Integer>> data;
	
	@Override
	public ReadResponse readData(InputSource inputSource) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<>();
		Scanner sc;
		try {
			sc = new Scanner(inputSource.getFile());
		}catch (Exception e) {
			return new ReadResponse(Status.FAILURE);
		}
		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] nums = line.split(",");
			for (String s: nums) {
				list.add(Integer.parseInt(s));
			}
		}
		return new ReadResponse(list, Status.SUCCESS);
	}

	@Override
	public SendResponse sendDataStream(DataDestination destination) {
		return null;
	}

	@Override
	public ReceiveResponse receiveData(DataSource source) {
		// TODO Auto-generated method stub
		this.data = source.getData();
		return new ReceiveResponse(Status.SUCCESS);
	}

	@Override
	public WriteResponse writeData(OutputDestination outputDestination) {
		List<String> serializedData = new ArrayList<>();
		for (Integer num: data.keySet()) {
			serializedData.add(serialize(num, data.get(num)));
		}
		StringBuilder output = new StringBuilder();
		for (String s: serializedData) {
			output.append(s);
			output.append(';');
		}
		output.deleteCharAt(output.length()-1);
		try {
			new FileWriter(outputDestination.getFile()).write(serializedData.toString());
		} catch (Exception e) {
			return new WriteResponse(Status.FAILURE);
		}
		return new WriteResponse(Status.SUCCESS);
	}

	private String serialize(int num, List<Integer> factors) {
		StringBuilder serializedData = new StringBuilder(Integer.toString(num));
		serializedData.append(':');
		for (int i: factors) {
			serializedData.append(Integer.toString(i));
			serializedData.append(',');
		}
		return serializedData.toString();
	}
}
