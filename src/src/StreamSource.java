package src;
import java.util.List;

import Interfaces.Response;
import Responses.ReadStreamResponse;

public class StreamSource {
	
	List<Integer> values;
	
	public StreamSource(List<Integer> values) {
		this.values = values;
	}
	
	public ReadStreamResponse containsData() {
		// TODO Auto-generated method stub
		if(values.isEmpty()) {
			return new ReadStreamResponse(Response.Status.FAILURE);
		}
		return new ReadStreamResponse(Response.Status.SUCCESS);
	}
    //TBD
}
