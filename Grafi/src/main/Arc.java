package main;

import util.Util;

public class Arc<T> {
	
	private final Node<T> from;
	private final Node<T> to;
	
	public Arc(Node<T> from, Node<T> to) {
		Util.checkNull(from);
		Util.checkNull(to);
		this.from = from;
		this.to = to;
	}
	
	public Node<T> getFrom() {
		return from;
	}
	
	public Node<T> getTo() {
		return to;
	}

}
