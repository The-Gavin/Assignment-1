package responses;

import interfaces.Response;

public class SendResponse implements Response<String> {
    //TBD
    private final Status status;
    private String data;

    public SendResponse(Status s, String data) {
        this.status = s;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}
    
    
}
