package main;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GraphHM<T> extends GraphAbs<T> {
	
	TreeMap<Node<T>, TreeSet<Node<T>>> graph;
	
	public GraphHM() {
		this.graph = new TreeMap<>();
	}

	@Override
	protected void addNodeImpl(Node<T> node) {
		graph.put(node, new TreeSet<>());
	}

	@Override
	protected void removeNodeImpl(Node<T> node) {
		graph.remove(node);
	}

	@Override
	protected boolean addArcImpl(Node<T> from, Node<T> to) {
		return graph.get(from).add(to);
	}

	@Override
	protected boolean removeArcImpl(Node<T> from, Node<T> to) {
		return graph.get(from).remove(to);
	}

	@Override
	public Set<Node<T>> adjacents(Node<T> node) {
		return new HashSet<>(graph.get(node));
	}

	@Override
	public Set<Node<T>> incidentsImpl(Node<T> node) {
		Set<Node<T>> incidents = new HashSet<>();
		for (Node<T> fromNode : graph.keySet()) {
			for (Node<T> toNode : graph.get(fromNode)) {
				if (fromNode == node) {
					incidents.add(toNode);
				} else if (toNode == node) {
					incidents.add(fromNode);
				}
			}
		}
		return incidents;
	}

	@Override
	public Set<Node<T>> nodes() {
		return graph.keySet();
	}

	@Override
	public Set<Arc<T>> arcs() {
		Set<Arc<T>> arcs = new HashSet<>();
		for (Node<T> fromNode : graph.keySet()) {
			for (Node<T> toNode : graph.get(fromNode)) {
				arcs.add(new Arc<>(fromNode, toNode));
			}
		}
		return arcs;
	}

}
