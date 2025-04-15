package responses;

import java.util.List;

import interfaces.Response;

public class ReadResponse implements Response<List<Integer>> {
    //TBD
    private final Response.Status status;
    private List<Integer> list = null;

    public ReadResponse(Response.Status s) {
        this.status = s;
    }

    public ReadResponse(List<Integer> list, Response.Status s) {
        this.list = list;
        this.status = s;
    }

    public Response.Status getStatus() {
        return status;
    }

	@Override
	public List<Integer> getData() {
		// TODO Auto-generated method stub
		return list;
	}
}
