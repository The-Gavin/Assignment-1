package interfaces;

import project.annotations.ProcessAPI;
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

@ProcessAPI
public interface Processing {
	//Read data from user-specified input source
    ReadResponse readData(InputSource inputSource);

	OutputResponse getOutputDestination(OutputDestination outputdestination);
    //Pass data in form of integer stream to computation component
    SendResponse sendDataStream(DataDestination destination);

    //Receive processed data from computation component
    ReceiveResponse receiveData(DataSource source, String outputPath);

    //Write data to data storage system and user
    WriteResponse writeData(StructuredData source, String outputPath);

	StreamSource getStream();
    
    
}