package prototypes;
import java.io.File;

import interfaces.WebServer;
import project.annotations.NetworkAPIPrototype;
import responses.InputResponse;
import responses.OutputResponse;
import src.InputSource;
import src.OutputDestination;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) throws Exception {
		//Provide Input Source
		InputResponse inputResponse = server.provideInputSource(new InputSource((File) null));

		//Provide Output Destination
		OutputResponse outputResponse = server.provideOutputDestination(new OutputDestination("File Path"));
	}
}