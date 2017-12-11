package albero_espressione.node;

import java.util.Iterator;

public interface Node<T> {
	
	public boolean isEmpty();
	
	public Node<T> getFather();
	
	public void setFather(Node<T> node);
	
	public Iterator<Node<T>> getChildIterator();
	
	public T getElement();
	
	public void setElement(T elem);

}
