package src;

import interfaces.Response;
import responses.ReadResponse;
import responses.WriteOutputResponse;

public class DataStore {
	public ReadResponse read(InputConfig i) {
		return new ReadResponse(Response.Status.SUCCESS);
	}

	public WriteOutputResponse recieveData(OutputData outputData) {
		// TODO Auto-generated method stub
		return new WriteOutputResponse(Response.Status.SUCCESS, "");
	}
}
