import java.io.File;

import Interfaces.WebServer;
import src.InputSource;
import src.OutputDestination;


public class TestUser {
	
	// TODO 3: change the type of this variable to the name you're using for your
	// @NetworkAPI interface; also update the parameter passed to the constructor
	private final WebServer coordinator;

	public TestUser(WebServer coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";
		
		// TODO 4: Call the appropriate method(s) on the coordinator to get it to 
		// run the compute job specified by inputPath, outputPath, and delimiter
		try {
			coordinator.provideInputSource(new InputSource(new File(inputPath)));
			coordinator.provideOutputDestination(new OutputDestination(outputPath));
			coordinator.factor();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print(e);
		}
	}

}
