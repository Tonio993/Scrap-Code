package albero_espressione.node;

public interface BNode<T> extends Node<T> {
	
	public Node<T> getChild(int index);
	
	public void setChild(int index, Node<T> node);

}
