package test;

import java.util.stream.Collectors;

import main.GraphI;
import main.GraphTM;
import main.Node;

public class Test {
	
	public static void main(String[] args) {
		GraphI<String> g = new GraphTM<>();
		Node<String> nodeA = new Node<>("A");
		Node<String> nodeB = new Node<>("B");
		Node<String> nodeC = new Node<>("C");
		Node<String> nodeD = new Node<>("D");
		g.addNode(nodeA);
		g.addNode(nodeB);
		g.addNode(nodeC);
		g.addNode(nodeD);
		
		g.addArc(nodeA, nodeC);
		g.addArc(nodeB, nodeA);
		g.addArc(nodeB, nodeC);
		g.addArc(nodeC, nodeB);
		g.addArc(nodeC, nodeD);
		
		System.out.println("Nodi adiacenti");
		for (Node<String> node : g.nodes()) {
			System.out.println("Nodo " + node.getValue() + ": " + g.adjacents(node).stream().map((n) -> n.getValue()).collect(Collectors.toList()));
		}
		System.out.println("Nodi incidenti");
		for (Node<String> node : g.nodes()) {
			System.out.println("Nodo " + node.getValue() + ": " + g.incidents(node).stream().map((n) -> n.getValue()).collect(Collectors.toList()));
		}
	}

}
