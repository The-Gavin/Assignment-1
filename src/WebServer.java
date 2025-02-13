import project.annotations.NetworkAPI;

@NetworkAPI
public interface WebServer
{
	//Take in a number of integers from the user
	public DataResponse sendUserData(UserData data);
	//Send the factors for calculation
	public DataResponse sendFactors(FactorStream factor);
	//Take in the character specified by the user to separate outputs
	public DataResponse setDelimiter(char delim);
}