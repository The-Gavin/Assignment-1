package src;

import java.util.List;

public class ReadResponse implements Response{
    //TBD
	private final Status result;
	private List<Integer> list = null;
	
	public ReadResponse(Status s) {
		this.result = s;
	}
	
	public ReadResponse(List<Integer> list, Status s) {
		this.list = list;
		this.result = s;
	}

	public Status getStatus() {
		return result;
	}
	
	public List<Integer> gerResponse(){
		return list;
	}
}
