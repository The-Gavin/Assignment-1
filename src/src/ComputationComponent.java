package src;

import Interfaces.Response;
import Responses.InitializationResponse;

public class ComputationComponent {
	
	public ComputationComponent() {
		
	}

	public InitializationResponse initialize() {
		// TODO Auto-generated method stub
		return new InitializationResponse(Response.Status.SUCCESS, "Initialized");
	}
    //TBD
}
