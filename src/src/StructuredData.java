package src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructuredData {
	
	private Map<Integer, List<Integer>> data;
	private String outputPath;
	
	public StructuredData(DataSource source, String outputPath) {
		this.data = new HashMap<>();
		for (List<Integer> list: source.getData()) {
			data.put(list.get(list.size()-1), list);
		}
		this.outputPath = outputPath;
	}
	
	public Map<Integer, List<Integer>> getData(){
		return data;
	}
	
	public String getOutput() {
		return outputPath;
	}
}
