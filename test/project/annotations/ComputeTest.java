package project.annotations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComputeTest {

	@Test
	public void smokeTestCompute() {
		// The computation component has very simple inputs/outputs and no dependencies, so we can
		// write a smoke test with no mocks at all
		
		ComputeEngine engine = new ComputeEngineImpl();
		Assertions.assertEquals("1", engine.compute(1));
	}
}
