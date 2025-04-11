package Prototypes;

import java.io.File;

import Interfaces.Processing;
import project.annotations.ProcessAPIPrototype;
import Responses.ReadResponse;
import Responses.ReceiveResponse;
import Responses.SendResponse;
import Responses.WriteResponse;
import src.InputSource;
import src.DataDestination;
import src.DataSource;
import src.OutputDestination;

public class PrototypeProcess {
	@ProcessAPIPrototype
	public void prototype(Processing readWriter) {
		//Read data from input source
		ReadResponse readResponse = readWriter.readData(new InputSource((File) null));

		//Send data in form of integer stream to computation component
		SendResponse sendResponse = readWriter.sendDataStream(new DataDestination());

		//Receive processed data from computation component
		ReceiveResponse receiveResponse = readWriter.receiveData(new DataSource(null), null);

		//Write data to specified locations
		WriteResponse writeResponse = readWriter.writeData(null, null);
	}
}
