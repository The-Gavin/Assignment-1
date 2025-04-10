package Interfaces;
public interface Response<T> {
	
	public static enum Status {
		SUCCESS,
		FAILURE;
	}
	
	public Status getStatus();
	
	public T getData();
}
