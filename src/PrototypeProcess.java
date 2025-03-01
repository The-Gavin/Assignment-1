import java.io.File;

import project.annotations.ProcessAPIPrototype;

public class PrototypeProcess {
	@ProcessAPIPrototype
	public void prototype(Processing readWriter) {
		//Read data from input source
		ReadResponse readResponse = readWriter.readData(new InputSource((File) null));

		//Send data in form of integer stream to computation component
		SendResponse sendResponse = readWriter.sendDataStream(new DataDestination());

		//Receive processed data from computation component
		ReceiveResponse receiveResponse = readWriter.receiveData(new DataSource(null));

		//Write data to specified locations
		WriteResponse writeResponse = readWriter.writeData(new OutputDestination((File) null));
	}
}
