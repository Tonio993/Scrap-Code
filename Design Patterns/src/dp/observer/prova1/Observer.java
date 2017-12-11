package dp.observer.prova1;

public interface Observer<T extends Subject> {
	
	public T getSubject();
	
	public boolean setSubject(T sbj);
	
	public void getNotified(ObserveRequest on);

}
