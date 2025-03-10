import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataStorage implements Processing{

	private Map<Integer, List<Integer>> data;
	
	@Override
	public ReadResponse readData(InputSource inputSource) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<>();
		Scanner sc;
		try {
			if (inputSource.getFile().exists() && inputSource.getFile().canRead()) {
				sc = new Scanner(inputSource.getFile());
			}else {
				throw new Exception("File not found or can not be read");  
			}
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
			return new ReceiveResponse(Response.Status.FAILURE);
		}
		this.data = source.getData();
		return new ReceiveResponse(Response.Status.SUCCESS);
	}

	@Override
	public WriteResponse writeData(OutputDestination outputDestination) {
		if (!outputDestination.getFile().exists() || !outputDestination.getFile().canWrite()) {
			return new WriteResponse(Response.Status.FAILURE);
		}
		List<String> serializedData = new ArrayList<>();
		for (Integer num: data.keySet()) {
			serializedData.add(serialize(num, data.get(num)));
		}
		StringBuilder output = new StringBuilder();
		for (String s: serializedData) {
			output.append(s);
			output.append(';');
		}
		try {
			output.deleteCharAt(output.length()-1);
		}catch (Exception e) {
			return new WriteResponse(Response.Status.FAILURE);
		}
		try {
			FileWriter writer = new FileWriter(outputDestination.getFile());
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
		return serializedData.toString();
	}
}
