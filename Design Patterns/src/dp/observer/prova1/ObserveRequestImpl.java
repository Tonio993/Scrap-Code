package dp.observer.prova1;

import java.util.Objects;

public class ObserveRequestImpl implements ObserveRequest {

	private String request;
	
	public ObserveRequestImpl(String request) {
		Objects.requireNonNull(request);
		this.request = request;
	}
	
	@Override
	public String getRequest() {
		return request;
	}

}
