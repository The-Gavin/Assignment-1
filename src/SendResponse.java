package src;

public class SendResponse implements Response {
    //TBD
	private final Status result;
	
	public SendResponse(Status s) {
		this.result = s;
	}
	
	public Status getStatus() {
		return result;
	} 
}
