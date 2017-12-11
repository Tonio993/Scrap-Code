package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Multiterator<T> implements Iterator<T>, Iterable<T> {
	
	Iterator<Iterator<T>> iterators;
	Iterator<T> currIt;
	
	public <C extends Collection<? extends Iterable<T>>> Multiterator(C collection) {
		List<Iterator<T>> itList = new ArrayList<>(collection.size());
		for (Iterable<T> it : collection) {
			itList.add(it.iterator());
		}
		iterators = itList.iterator();
		getNext();
	}
	
	private void getNext() {
		while(currIt == null || !currIt.hasNext()) {
			if (iterators.hasNext()) {
				currIt = iterators.next();
			} else {
				currIt = null;
				break;
			}
		}
	}

	@Override
	public boolean hasNext() {
		return currIt != null;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		T next = currIt.next();
		getNext();
		return next;
	}
	
	@Override
	public Iterator<T> iterator() {
		return this;
	}
}
