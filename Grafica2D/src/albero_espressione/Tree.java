package albero_espressione;

public interface Tree<T> extends Collection<T> {
	
	public T lower();
	
	public T higher();

}
