import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interfaces.WebServer;
import src.CoordinationComponent;
import src.MultiThreadWebServer;

public class TestMultiUser {
	
	// TODO 1: change the type of this variable to the name you're using for your @NetworkAPI
	// interface
	private WebServer coordinator;
	
	@BeforeEach
	public void initializeComputeEngine() {
		//TODO 2: create an instance of the implementation of your @NetworkAPI; this is the component
		// that the user will make requests to
		// Store it in the 'coordinator' instance variable
		Optional<WebServer> initalizer = MultiThreadWebServer.initialize();
		if (initalizer.isPresent()) {
			coordinator = initalizer.get();
		}
	}

	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int numTreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < numTreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}
		
		// Run single threaded
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		for (int i = 0; i < numTreads; i++) {
			File singleThreadedOut = 
					new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			testUsers.get(i).run(singleThreadedOut.getCanonicalPath());
		}
		
		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < numTreads; i++) {
			File multiThreadedOut = 
					new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(() -> testUser.run(multiThreadOutputPath)));
		}
		
		results.forEach(future -> {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		
		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, numTreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, numTreads);
		Assert.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int numTreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < numTreads; i++) {
			File multiThreadedOut = 
					new File(prefix + i);
			result.addAll(Files.readAllLines(multiThreadedOut.toPath()));
		}
		return result;
	}
}
