package src;

public class ReadResponse implements Response{
    //TBD
	private final Status result;
	
	public ReadResponse(Status s) {
		this.result = s;
	}
	
	public Status getStatus() {
		return result;
	}
}
