package responses;

import java.util.ArrayList;
import java.util.List;

import interfaces.Response;

public class InputResponse implements Response<List<Integer>>{
	
	private final Response.Status result;
	private final List<Integer> data;
	
	public InputResponse() {
		result = Response.Status.FAILURE;
		data = new ArrayList<Integer>();
	}
	
	public InputResponse(Response.Status s, List<Integer> data) {
		result = s;
		this.data = data;
	}
	
	public InputResponse(Response<List<Integer>> info) {
		result = info.getStatus();
		data = info.getData();
	}

	@Override
	public Response.Status getStatus() {
		return result;
	}
	
	@Override
	public List<Integer> getData(){
		return data;
	}
    
}
