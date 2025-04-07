
//package project.
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Implementations.WebServerImplement;
import Interfaces.CompFactor;
import Interfaces.Response;
import Interfaces.WebServer;
import Responses.InputResponse;
import Responses.OutputResponse;
import Responses.ReadResponse;
import src.DataStore;
import src.InputConfig;
import src.InputSource;
import src.OutputDestination;

//Import classes from the rest of the project

public class NetworkTest {
	@Test
	public void smokeTest() {
		// mock out the dependencies so that we're just checking the ComputationCoordinator
		DataStore dataStore = mock(DataStore.class);
		CompFactor compFactor = mock(CompFactor.class);
		InputSource inputSource = mock(InputSource.class);
		OutputDestination outputDestination = mock(OutputDestination.class);
		
		when(dataStore.read(any(InputConfig.class)))
		   .thenReturn(new ReadResponse(new ArrayList<Integer>(), Response.Status.SUCCESS));
		
		WebServer server = new WebServerImplement();
		
		InputResponse input = new InputResponse(Response.Status.FAILURE);
		try {
			input = server.provideInputSource(inputSource);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		OutputResponse output = new OutputResponse(Response.Status.FAILURE);
		try {
			output = server.provideOutputDestination(outputDestination);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		Assertions.assertEquals(input.getStatus(), Response.Status.SUCCESS);
		Assertions.assertEquals(output.getStatus(), Response.Status.SUCCESS);
	}
}
