public interface Response {
	
	public static enum Status {
		SUCCESS,
		FAILURE;
	}
	
	public Status getStatus();
}
