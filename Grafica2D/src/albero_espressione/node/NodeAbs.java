package albero_espressione.node;

public abstract class NodeAbs<T> implements Node<T> {
	
	protected Node<T> father;
	
	protected T element;
	
	@Override
	public boolean isEmpty() {
		return element == null;
	}
	
	@Override
	public Node<T> getFather() {
		return father;
	}
	
	@Override
	public void setFather(Node<T> father) {
		this.father = father;
	}
	
	@Override
	public T getElement() {
		return element;
	}
	
	@Override
	public void setElement(T elem) {
		this.element = elem;
	}
	
}
