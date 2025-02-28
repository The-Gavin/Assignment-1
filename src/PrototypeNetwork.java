package src;

import java.io.File;

import src.project.annotations.NetworkAPIPrototype;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) {
		//Provide Input Source
		InputResponse inputResponse = server.provideInputSource(new InputSource((File) null));

		//Provide Output Destination
		OutputResponse outputResponse = server.provideOutputDestination(new OutputDestination((File) null);

		//specify the output separation
		DelimiterResponse delimiterResponse = server.setDelimiter((Character) null);
	}
}