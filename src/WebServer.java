import project.annotations.NetworkAPI;

@NetworkAPI
public interface WebServer
{
	//take in a number of integers
	
	public int intMethod();
	
	//Split the output by the user-specified character
	public String outputSplit();
}