package src;
import java.util.List;

import Interfaces.Response;
import Responses.ReadStreamResponse;

public class StreamSource {
	
	private List<Integer> values;
	
	public StreamSource(List<Integer> values) {
		this.values = values;
	}
	
	public ReadStreamResponse containsData() {
		// TODO Auto-generated method stub
		if(values.isEmpty()) {
			return new ReadStreamResponse(Response.Status.FAILURE, "List was Empty");
		}
		return new ReadStreamResponse(Response.Status.SUCCESS, "List has Values");
	}
    
	public List<Integer> getData() {
		return values;
	}
}
