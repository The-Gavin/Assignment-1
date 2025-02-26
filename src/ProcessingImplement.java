package src;

import src.Response.Status;

public class ProcessingImplement implements Processing {
	WebServer webServer;
	CompFactor computationComponent;

	//Read data from user-specified input source
	public ReadResponse readData(InputSource inputSource) {
		return new ReadResponse(Status.SUCCESS);
	}

	//Pass data in form of integer stream to computation component
	public SendResponse sendDataStream(DataDestination destination) {
		return new SendResponse(Status.SUCCESS);
	}

	//Receive processed data from computation component
	public ReceiveResponse receiveData(DataSource source) {
		return new ReceiveResponse(Status.SUCCESS);
	}

	//Write data to data storage system and user
	public WriteResponse writeData(OutputDestination outputDestination) {
		return new WriteResponse(Status.SUCCESS);
	}

}
