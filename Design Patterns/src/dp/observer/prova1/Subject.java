package dp.observer.prova1;

import java.util.Iterator;

public interface Subject {
	
	public boolean addObserver(Observer<?> obs);
	
	public boolean removeObserver(Observer<?> obs);
	
	public Iterator<Observer<?>> getObservers();
	
	default public void notifyAll(ObserveRequest on) {
		Iterator<Observer<?>> it = getObservers();
		while (it.hasNext()) {
			it.next().getNotified(on);
		}
	}

}
