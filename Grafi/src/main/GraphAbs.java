package main;

import java.util.Set;

public abstract class GraphAbs<T> implements GraphI<T> {
	
	public boolean isEmpty() {
		return nodes().isEmpty();
	}
	
	public int nodeSize() {
		return nodes().size();
	}

	@Override
	public int arcSize() {
		return arcs().size();
	}

	@Override
	public boolean containsNode(Node<T> node) {
		return nodes().contains(node);
	}

	@Override
	public boolean addNode(Node<T> node) {
		if (containsNode(node)) {
			return false;
		}
		addNodeImpl(node);
		return true;
	}
	
	protected abstract void addNodeImpl(Node<T> node);

	@Override
	public boolean removeNode(Node<T> node) {
		if (!containsNode(node)) {
			return false;
		}
		removeNodeImpl(node);
		for (Node<T> incident : incidents(node)) {
			removeArc(node, incident);
		}
		return true;
	}

	protected abstract void removeNodeImpl(Node<T> node);

	@Override
	public boolean containsArc(Node<T> from, Node<T> to) {
		return containsArc(new Arc<T>(from, to));
	}

	@Override
	public boolean containsArc(Arc<T> arc) {
		return arcs().contains(arc);
	}

	@Override
	public boolean addArc(Node<T> from, Node<T> to) {
		checkContainsNode(from, to);
		return addArcImpl(from, to);
	}
	
	protected abstract boolean addArcImpl(Node<T> from, Node<T> to);
	
	@Override
	public boolean addArc(Arc<T> arc) {
		return addArc(arc.getFrom(), arc.getTo());
	}

	@Override
	public boolean removeArc(Node<T> from, Node<T> to) {
		checkContainsNode(from, to);
		return removeArcImpl(from, to);
	}
	
	@Override
	public boolean removeArc(Arc<T> arc) {
		return removeArc(arc.getFrom(), arc.getTo());
	}

	protected abstract boolean removeArcImpl(Node<T> from, Node<T> to);
	
	public Set<Node<T>> incidents(Node<T> node) {
		checkContainsNode(node);
		return incidentsImpl(node);
	}
	
	protected abstract Set<Node<T>> incidentsImpl(Node<T> node);

	@SafeVarargs
	private final void checkContainsNode(Node<T>... nodes) {
		for (Node<T> node : nodes) {
			if (!containsNode(node)) {
				throw new IllegalArgumentException("Nodo non presente");
			}
		}
	}

}
