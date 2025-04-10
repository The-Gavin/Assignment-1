package src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructuredData {
	
	private Map<Integer, List<Integer>> data;
	
	public StructuredData(DataSource source) {
		this.data = new HashMap<>();
		for(List<Integer> list: source.getData()) {
			data.put(list.getLast(), list);
		}
	}
	
	public Map<Integer, List<Integer>> getData(){
		return data;
	}
}
