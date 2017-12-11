package albero_espressione;

public interface Collection<T> extends Iterable<T> {
	
	public boolean isEmpty();
	
	public int size();

	public boolean contains(T elem);
	
	public boolean add(T elem);
	
	public boolean remove(T elem);

}
