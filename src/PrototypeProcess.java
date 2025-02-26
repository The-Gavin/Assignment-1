package src;

import src.project.annotations.ProcessAPIPrototype;

public class PrototypeProcess {
	@ProcessAPIPrototype
	public void prototype(Processing readWriter) {
		//Read data from input source
		ReadResponse readResponse = readWriter.readData(new InputSource());

		//Send data in form of integer stream to computation component
		SendResponse sendResponse = readWriter.sendDataStream(new DataDestination());

		//Receive processed data from computation component
		ReceiveResponse receiveResponse = readWriter.receiveData(new DataSource());

		//Write data to specified locations
		WriteResponse writeResponse = readWriter.writeData(new OutputDestination());
	}
}
