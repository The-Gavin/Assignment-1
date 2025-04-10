package Responses;

import java.util.List;
import Interfaces.Response;

public class ReadResponse implements Response<List<Integer>> {
    //TBD
    private final Response.Status result;
    private List<Integer> list = null;

    public ReadResponse(Response.Status s) {
        this.result = s;
    }

    public ReadResponse(List<Integer> list, Response.Status s) {
        this.list = list;
        this.result = s;
    }

    public Response.Status getStatus() {
        return result;
    }

	@Override
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		return list;
	}
}
