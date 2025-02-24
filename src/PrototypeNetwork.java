import project.annotations.NetworkAPIPrototype;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) {
		//Provide Input Source
		InputResponse inputResponse = server.provideInputSource(new InputSource());

		//Provide Output Destination
		OutputResponse outputResponse = server.provideOutputDestination(new OutputDestination());

		//specify the output separation
		DelimiterResponse delimiterResponse = server.setDelimiter((Character) null);
	}
}