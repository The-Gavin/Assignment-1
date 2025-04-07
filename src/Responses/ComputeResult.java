package Responses;

import Interfaces.Response;

public class ComputeResult implements Response{
	
	public Response.Status getStatus() {
        return Response.Status.FAILURE;
    }

}
