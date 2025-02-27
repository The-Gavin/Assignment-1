package src;
import src.Response.Status;

public class WriteResponse {
	private final Status result;
	
	public WriteResponse(Status s) {
		this.result = s;
	}
	
	public Status getStatus() {
		return result;
	} 
}
