package Prototypes;
import java.io.File;

import Interfaces.WebServer;
import Responses.InputResponse;
import Responses.OutputResponse;
import project.annotations.NetworkAPIPrototype;
import src.InputSource;
import src.OutputDestination;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) throws Exception {
		//Provide Input Source
		InputResponse inputResponse = server.provideInputSource(new InputSource((File) null));

		//Provide Output Destination
		OutputResponse outputResponse = server.provideOutputDestination(new OutputDestination((File) null));
	}
}