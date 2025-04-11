package src;

import interfaces.Response;
import responses.InitializationResponse;

public class ComputationComponent {
	
	public ComputationComponent() {
		
	}

	public InitializationResponse initialize() {
		// TODO Auto-generated method stub
		return new InitializationResponse(Response.Status.SUCCESS, "Initialized");
	}
    //TBD
}
