
public class ProcessingImplement implements Processing {
	WebServer webServer;
	CompFactor computationComponent;

	//Read data from user-specified input source
	public ReadResponse readData(InputSource inputSource) {
		return new ReadResponse();
	}

	//Pass data in form of integer stream to computation component
	public SendResponse sendDataStream(DataDestination destination) {
		return new SendResponse();
	}

	//Receive processed data from computation component
	public ReceiveResponse receiveData(DataSource source) {
		return new ReceiveResponse();
	}

	//Write data to data storage system and user
	public WriteResponse writeData(OutputDestination outputDestination) {
		return new WriteResponse();
	}

}
