package project.annotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NetworkTest {
	@Test
	public void smokeTest() {
		// mock out the dependencies so that we're just checking the ComputationCoordinator
		DataStore dataStore = mock(DataStore.class);
		CompFactor compFactor = mock(CompFactor.class);
		
		when(dataStore.read(any(InputConfig.class)))
		   .thenReturn(new ReadResponse(new ArrayList<Integer>()));
		
		WebServer server = new CoordinatorImpl(dataStore, compFactor);
		
		// mock out the parameters
		ComputeRequest mockRequest = mock(ComputeRequest.class);
        when(mockRequest.getInputConfig()).thenReturn(mock(InputConfig.class));
        when(mockRequest.getOutputConfig()).thenReturn(mock(OutputConfig.class));
        
		ComputeResult result = server.compute(mockRequest);
		
		// simple check for right now - just say the result must be successful
		Assertions.assertEquals(result.getStatus(), ComputeResult.ComputeResultStatus.SUCCESS);
	}
}
