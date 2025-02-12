import project.annotations.NetworkAPIPrototype;

public class PrototypeNetwork
{
	@NetworkAPIPrototype
	public void prototype(WebServer server)
	{
		//Send integers to webserver
		UserData data = server.intMethod();
		server.outputSplit();
		//specify the output separation


		// create a bunch of objects and call the methods that are relevant to them when necessary
	}
}