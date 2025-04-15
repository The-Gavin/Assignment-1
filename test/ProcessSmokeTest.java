import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import implementations.ProcessingImplement;
import interfaces.Processing;
import interfaces.Response;
import src.DataDestination;
import src.DataSource;
import src.InputSource;
import src.OutputDestination;

public class ProcessSmokeTest {

    @Test
    public void readSmokeTest() throws Exception {
        Processing data = new ProcessingImplement();
        InputSource userData = Mockito.mock(InputSource.class);
        Assertions.assertEquals(data.readData(userData).getStatus(), Response.Status.SUCCESS);
    }

    @Test
    public void sendSmokeTest() throws Exception {
        Processing data = new ProcessingImplement();
        DataDestination outputFile = Mockito.mock(DataDestination.class);
        Assertions.assertEquals(data.sendDataStream(outputFile).getStatus(), Response.Status.SUCCESS);
    }

    @Test
    public void receiveSmokeTest() throws Exception {
        Processing data = new ProcessingImplement();
        DataSource processedData = Mockito.mock(DataSource.class);
        Assertions.assertEquals(data.receiveData(processedData).getStatus(), Response.Status.SUCCESS);
    }
}