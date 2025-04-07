import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Implementations.CompImplement;
import Interfaces.CompFactor;
import Interfaces.Response;
import src.ComputationComponent;
import src.DataStore;
import src.StreamSource;

public class CompImplementTest {
    @Test
    public void initializeSmokeTest() throws Exception {
        CompFactor compFactor = new CompImplement();
        ComputationComponent computationComponent = Mockito.mock(ComputationComponent.class);
        Assertions.assertEquals(compFactor.initializeComputation().getStatus(), Response.Status.SUCCESS);
    }

    @Test
    public void readStreamSmokeTest() throws Exception {
        CompFactor compFactor = new CompImplement();
        StreamSource streamSource = Mockito.mock(StreamSource.class);
        Assertions.assertEquals(compFactor.readStream(streamSource).getStatus(), Response.Status.SUCCESS);
    }

    @Test
    public void writeOutputSmokeTest() throws Exception {
        CompFactor compFactor = new CompImplement();
        DataStore dataStore = Mockito.mock(DataStore.class);
        Assertions.assertEquals(compFactor.writeOutput(dataStore).getStatus(), Response.Status.SUCCESS);
    }
}
