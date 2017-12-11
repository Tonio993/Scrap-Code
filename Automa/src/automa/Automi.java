package automa;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;

import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.util.Util;

public class Automi {
	
	private Automi() {}
	
	public static TreeMap<Stato, Segnale> daStatoASegnale(Automa automa) {
		ArrayList<Stato> stati = new ArrayList<>(automa.listaStati());
		stati.sort(new Comparator<Stato>() {
			public int compare(Stato s1, Stato s2) {
				return s1.getNome().compareTo(s2.getNome());
			}
		});
		int numero = 1;
		int p = 2;
		while (p < stati.size()) {
			numero++;
			p *= 2;
		}
		LinkedList<Segnale> segnali = new LinkedList<>(Util.setBinario(numero));
		TreeMap<Stato, Segnale> tabella = new TreeMap<>();
		for (Stato stato : stati) {
			tabella.put(stato, segnali.removeFirst());
		}
		return tabella;
	}
}
