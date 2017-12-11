package automa.karnaugh;

import java.util.ArrayList;
import java.util.Iterator;

import automa.Automa;
import automa.eccezioni.EccezioneAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.test.Test;
import automa.util.Util;

public class TabellaSegnali implements Iterable<Segnale> {
	
	ArrayList<Segnale> listaIngressi;
	ArrayList<Segnale> listaUscite;
	
	public final int dimensione;
	
	public TabellaSegnali(Automa automa) {
		listaIngressi = new ArrayList<>();
		listaUscite = new ArrayList<>();
		ArrayList<Stato> stati = new ArrayList<>(automa.listaStati());
		ArrayList<Segnale> segnali = new ArrayList<>(Util.setBinario(automa.dimensioneInput()));
		TabellaStati tabellaStati = new TabellaStati(automa);
		dimensione = tabellaStati.dimensione + automa.dimensioneInput();
		for (Segnale ingresso : segnali) {
			for (Stato statoIniziale : stati) {
				Stato statoFinale = automa.getStato(statoIniziale, ingresso);
				Segnale uscita = automa.getSegnale(statoIniziale, ingresso);
				Segnale in = Segnale.String2Sig("" + tabellaStati.getSegnale(statoIniziale) + ingresso);
				Segnale out = Segnale.String2Sig("" + tabellaStati.getSegnale(statoFinale) + uscita);
				listaIngressi.add(in);
				listaUscite.add(out);
			}
		}
	}
	
	public Segnale getSegnale(Segnale in) {
		return listaUscite.get(listaIngressi.indexOf(in));
	}
	
	public Segnale getSegnale(Segnale in1, Segnale in2) {
		return listaUscite.get(listaIngressi.indexOf("" + in1 + in2));
	}
	
	@Override
	public Iterator<Segnale> iterator() {
		return listaIngressi.iterator();
	}
	
	public static void main(String[] args) throws EccezioneAutoma {
		Automa automa = Test.testAutoma();
		TabellaSegnali ts = new TabellaSegnali(automa);
		for (Segnale in : ts.listaIngressi) {
			System.out.println(in + " " + ts.getSegnale(in));
		}
	}
}