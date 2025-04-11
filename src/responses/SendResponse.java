package responses;

import interfaces.Response;

public class SendResponse implements Response<String> {
    //TBD
    private final Status result;
    private String data;

    public SendResponse(Status s, String data) {
        this.result = s;
        this.data = data;
    }

    public Status getStatus() {
        return result;
    }

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}
    
    
}
