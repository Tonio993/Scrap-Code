package grafica;

public abstract class ActionThread implements Runnable {
	
	protected long callTimestamp;
	
	public ActionThread() {
		this.callTimestamp = System.currentTimeMillis();
	}
	
	public ActionThread(long callTimestamp) {
		this.callTimestamp = callTimestamp;
	}

}
