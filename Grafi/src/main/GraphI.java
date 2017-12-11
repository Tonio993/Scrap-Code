package main;

import java.util.Set;

public interface GraphI<T> {
	
	public boolean isEmpty();
	
	public int nodeSize();
	public int arcSize();
	
	public boolean containsNode(Node<T> node);
	public boolean addNode(Node<T> node);
	public boolean removeNode(Node<T> node);
	
	public boolean containsArc(Node<T> from, Node<T> to);
	public boolean containsArc(Arc<T> arc);
	public boolean addArc(Node<T> from, Node<T> to);
	public boolean addArc(Arc<T> arc);
	public boolean removeArc(Node<T> from, Node<T> to);
	public boolean removeArc(Arc<T> arc);
	
	public Set<Node<T>> adjacents(Node<T> node);
	public Set<Node<T>> incidents(Node<T> node);
	
	public Set<Node<T>> nodes();
	public Set<Arc<T>> arcs();
	
}
