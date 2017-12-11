package automa.karnaugh;

import java.util.TreeSet;

import automa.Automa;
import automa.eccezioni.EccezioneAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Segnali;
import automa.oggetti.Valore;
import automa.test.Test;

public class MappaDiKarnaugh {
	
	private TreeSet<Segnale> segnale;
	private TreeSet<Segnale> mappa;
	
	public final int index;
	public final int dimensione;
	
	public MappaDiKarnaugh(Automa automa, int index) {
		this.index = index;
		TreeSet<Segnale> mappa = new TreeSet<>();
		TabellaSegnali tabellaSegnali = new TabellaSegnali(automa);
		dimensione = tabellaSegnali.dimensione;
		for (Segnale ingresso : tabellaSegnali) {
			if (tabellaSegnali.getSegnale(ingresso).getValore(index) == (Valore.V1)) {
				mappa.add(ingresso);
			}
		}
		this.mappa = new TreeSet<Segnale>(mappa);
		TreeSet<Segnale> mappaTemp1 = new TreeSet<>(mappa);
		TreeSet<Segnale> mappaTemp2 = new TreeSet<>();
		boolean riscontro;
		int adiacente;
		do {
			riscontro = false;
			for (Segnale segnale1 : mappaTemp1) {
				for (Segnale segnale2 : mappaTemp1) {
					adiacente = adiacente(segnale1, segnale2);
					if (adiacente != -1) {
						Segnale newSign = segnale1.set(adiacente, Valore.NI);
						mappa.add(newSign);
						mappaTemp2.add(newSign);
						riscontro = true;
					}
				}
			}
			mappaTemp1 = mappaTemp2;
			mappaTemp2 = new TreeSet<>();
		} while (riscontro);
		int dimensione = 1;
		for (Segnale segnale : mappa) {
			if (segnale.indefiniti > dimensione) {
				dimensione = segnale.indefiniti;
			}
		}
		this.segnale = new TreeSet<Segnale>();
		TreeSet<Segnale> ingressi = new TreeSet<>(tabellaSegnali.listaIngressi);
		while(dimensione > 0) {
			for (Segnale segnale : mappa) {
				if (segnale.indefiniti == dimensione) {
					TreeSet<Segnale> corrispondenti = Segnali.segnaliCorrispondenti(segnale);
					for (Segnale corrispondente : corrispondenti) {
						if (ingressi.contains(corrispondente)) {
							this.segnale.add(segnale);
							ingressi.removeAll(corrispondenti);
							break;
						}
					}
				}
			}
			dimensione--;
		}
	}
	
	public TreeSet<Segnale> getSegnale() {
		return new TreeSet<Segnale>(segnale);
	}
	
	public TreeSet<Segnale> getMappa() {
		return new TreeSet<Segnale>(mappa);
	}
	
	private int adiacente(Segnale s1, Segnale s2) {
		int bitDiverso = -1;
		for (int i=0;i<s1.size();i++) {
			if (s1.getValore(i) != s2.getValore(i)) {
				if (bitDiverso != -1 || s1.getValore(i) == Valore.NI || s2.getValore(i) == Valore.NI) {
					return -1;
				} else {
					bitDiverso = i;
				}
			}
		}
		return bitDiverso;
	}
	
	public static void main(String[] args) throws EccezioneAutoma {
		Automa automa = Test.testAutoma();
		MappaDiKarnaugh m = new MappaDiKarnaugh(automa,0);
		System.out.println(m.getSegnale());
	}
	
}
