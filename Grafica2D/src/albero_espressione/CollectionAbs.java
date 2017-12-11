package albero_espressione;

public abstract class CollectionAbs<T> implements Collection<T> {

	@Override
	public boolean isEmpty() {
		return this.iterator().hasNext();
	}

	@Override
	public int size() {
		int n = 0;
		for (@SuppressWarnings("unused") T element : this) {
			n++;
		}
		return n;
	}

	@Override
	public boolean contains(T elem) {
		for (T element : this) {
			if (elem.equals(element)) {
				return true;
			}
		}
		return false;
	}

}
