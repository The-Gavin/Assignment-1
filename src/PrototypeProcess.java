import project.annotations.ProcessAPIPrototype;

public class PrototypeProcess {
	@ProcessAPIPrototype
	public void prototype(Processing readWriter) {
		//Read data from input source
		ReadResponse readResponse = readWriter.readData(new InputSource());

		//Send data in form of integer stream to computation component
		SendResponse sendResponse = readWriter.SendDataStream(new DataDestination());

		//Receive processed data from computation component
		ReceiveResponse receiveResponse = readWriter.ReceiveData(new DataSource());

		//Write data to specified locations
		WriteResponse writeResponse = readWriter.writeData(new OutputDestination());
	}
}
