import java.io.File;

import project.annotations.NetworkAPIPrototype;

public class PrototypeNetwork {
	@NetworkAPIPrototype
	public void prototype(WebServer server) throws Exception {
		//Provide Input Source
		InputResponse inputResponse = server.provideInputSource(new InputSource((File) null));

		//Provide Output Destination
		OutputResponse outputResponse = server.provideOutputDestination(new OutputDestination((File) null));
	}
}