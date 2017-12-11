package automa.test;

import java.util.Set;

import automa.util.Insieme;
import automa.util.InsiemeImpl;

public class TestInsiemi {
	
	public static void main(String[] args) {
		Insieme<Integer> insiemi = new InsiemeImpl<>();
		insiemi.aggiungi(1, 2);
		insiemi.aggiungi(3);
		insiemi.aggiungi(5,1);
		insiemi.aggiungi(4,3);
		for (Set<Integer> set : insiemi.insiemi()) {
			for (int i : set) {
				System.out.print(i + "\t");
			}
			System.out.println();
		}
	}

}
