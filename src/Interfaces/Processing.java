package Interfaces;

import project.annotations.ProcessAPI;
import src.DataDestination;
import src.DataSource;
import src.InputSource;
import src.OutputDestination;
import src.StreamSource;
import src.StructuredData;
import Responses.OutputResponse;
import Responses.ReadResponse;

import Responses.ReceiveResponse;
import Responses.SendResponse;
import Responses.WriteResponse;

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