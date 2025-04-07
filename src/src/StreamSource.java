package src;
import java.util.List;

import Interfaces.Response;
import Responses.ReadStreamResponse;

public class StreamSource {
	
	List<Integer> values;
	
	public ReadStreamResponse containsData() {
		// TODO Auto-generated method stub
		return new ReadStreamResponse(Response.Status.SUCCESS);
	}
    //TBD
}
