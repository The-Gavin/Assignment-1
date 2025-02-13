import project.annotations.NetworkAPIPrototype;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) {
		//Check to see if the user's data has been received
		DataResponse dataReceived = server.sendUserData(null);
		//Take in the user's int value(s)
		DataResponse userInt = server.sendFactors(null);
		//specify the output separation
		DataResponse userDelimiter = server.setDelimiter((Character) null);
	}
}