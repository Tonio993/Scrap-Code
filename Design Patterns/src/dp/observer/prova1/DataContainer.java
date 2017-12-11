package dp.observer.prova1;

import java.util.HashSet;
import java.util.Iterator;

public class DataContainer implements Subject {
	
	HashSet<Observer<?>> observerSet = new HashSet<>();
	
	int valueA = 0;
	int valueB = 0;
	int valueC = 0;
	public int getValueA() {
		return valueA;
	}
	public void setValueA(int valueA) {
		this.valueA = valueA;
		this.notifyAll(new ObserveRequestImpl("A"));
	}
	public int getValueB() {
		return valueB;
	}
	public void setValueB(int valueB) {
		this.valueB = valueB;
		this.notifyAll(new ObserveRequestImpl("B"));
	}
	public int getValueC() {
		return valueC;
	}
	public void setValueC(int valueC) {
		this.valueC = valueC;
		this.notifyAll(new ObserveRequestImpl("C"));
	}
	@Override
	public boolean addObserver(Observer<?> obs) {
		return observerSet.add(obs);
	}
	@Override
	public boolean removeObserver(Observer<?> obs) {
		return observerSet.remove(obs);
	}
	@Override
	public Iterator<Observer<?>> getObservers() {
		return observerSet.iterator();
	}
}
	