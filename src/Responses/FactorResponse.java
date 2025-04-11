package Responses;

import java.util.ArrayList;
import java.util.List;

import Interfaces.Response;

public class FactorResponse implements Response<List<List<Integer>>>{

	private Response.Status status;
	private List<List<Integer>> factorLists;
	
	public FactorResponse(Response.Status s) {
		this.status = s;
		this.factorLists = new ArrayList<>();
	}
	
	@Override
	public Response.Status getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public FactorResponse ReciveFactors(List<Integer> factors) {
		// TODO Auto-generated method stub
		if(this.factorLists.add(factors)) {
			return this;
		}
		this.status = Response.Status.FAILURE;
		return this;
	}

	public List<List<Integer>> getData() {
		// TODO Auto-generated method stub
		return factorLists;
	}

}
