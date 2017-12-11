package automa.oggetti;

import java.util.TreeSet;

import automa.util.Util;

public class Segnali {
	
	private Segnali() {}
	
	public static TreeSet<Segnale> segnaliCorrispondenti(Segnale segnale) {
		TreeSet<Segnale> segnali = new TreeSet<>();
		TreeSet<Segnale> combinazioni = new TreeSet<>(Util.setBinario(segnale.indefiniti));
		Segnale combinazione;
		int i;
		for (Segnale sign : combinazioni) {
			combinazione = segnale;
			i = 0;
			for (int j=0;j<segnale.size();j++) {
				if (segnale.getValore(j) == Valore.NI) {
					combinazione = combinazione.set(j, sign.getValore(i++));
				}
			}
			segnali.add(combinazione);
		}
		return segnali;
	}
	
	public static void main(String[] args) {
		System.out.println(segnaliCorrispondenti(Segnale.String2Sig("0-0--")));
	}

}
