package implementations;

import interfaces.CompFactor;
import interfaces.Processing;
import interfaces.Response;
import interfaces.WebServer;
import responses.OutputResponse;
import responses.ReadResponse;
import responses.ReceiveResponse;
import responses.SendResponse;
import responses.WriteResponse;
import src.DataDestination;
import src.DataSource;
import src.InputSource;
import src.OutputDestination;
import src.StreamSource;
import src.StructuredData;

public class ProcessingImplement implements Processing {
	WebServer webServer;
	CompFactor computationComponent;

	//Read data from user-specified input source
	public ReadResponse readData(InputSource inputSource) {
		return new ReadResponse(Response.Status.SUCCESS);
	}

	//Pass data in form of integer stream to computation component
	public SendResponse sendDataStream(DataDestination destination) {
		return new SendResponse(Response.Status.SUCCESS,"");
	}

	//Receive processed data from computation component
	public ReceiveResponse receiveData(DataSource source, String outputPath) {
		return new ReceiveResponse(Response.Status.SUCCESS,"");
	}

	//Write data to data storage system and user
	public WriteResponse writeData(OutputDestination outputDestination) {
		return new WriteResponse(Response.Status.SUCCESS,"");
	}

	@Override
	public OutputResponse getOutputDestination(OutputDestination outputdestination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StreamSource getStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteResponse writeData(StructuredData source, String outputPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
