package main;

public class Node<T> {

	public Node(T value) {
		setValue(value);
	}
	
	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public String toString() {
		return "[Node - value:" + value + "]";
	}

}
