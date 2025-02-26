package src;

import src.Response.Status;

public class ReceiveResponse {
	private final Status result;
	
	public ReceiveResponse(Status s) {
		this.result = s;
	}
	
	public Status getStatus() {
		return result;
	} 
}