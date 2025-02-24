import project.annotations.ProcessAPI;

@ProcessAPI
public interface Processing {
	//Read data from user-specified input source
    ReadResponse readData(InputSource inputSource);

	//Pass data in form of integer stream to computation component
    SendResponse SendDataStream(DataDestination destination);

    //Receive processed data from computation component
    ReceiveResponse ReceiveData(DataSource source);

    //Write data to data storage system and user
    WriteResponse writeData(OutputDestination outputDestination);
}