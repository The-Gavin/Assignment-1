package test;

import src.Response.Status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ProcessSmokeTest {
	
	@Test
	public void readSmokeTest() throws Exception {
		Processing data = new ProcessingImplement();
		InputSource userData = Mockito.mock(InputSource.class);
		Assertions.assertEquals(data.readData(userData).getStatus(), Status.SUCCESS);		
	}
	
	@Test
	public void sendSmokeTest() throws Exception {
		Processing data = new ProcessingImplement();
		DataDestination outputFile = Mockito.mock(DataDestination.class);
		Assertions.assertEquals(data.sendDataStream(outputFile).getStatus(), Status.SUCCESS);
	}
	
	@Test
	public void receiveSmokeTest() throws Exception {
		Processing data = new ProcessingImplement();
		DataSource processedData = Mockito.mock(DataSource.class);
		Assertions.assertEquals(data.receiveData(processedData).getStatus(), Status.SUCCESS);
	}
	
	@Test
	public void writeSmokeTest() throws Exception {
		Processing data = new ProcessingImplement();
		OutputDestination outputLoc = Mockito.mock(OutputDestination.class);
		Assertions.assertEquals(data.writeData(outputLoc).getStatus(), Status.SUCCESS);
	}
}
