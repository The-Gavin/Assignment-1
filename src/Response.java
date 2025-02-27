package src;

public interface Response {
	
	public static enum Status {
		SUCCESS,
		Failure;
	}
	
	public Status getStatus();
}
