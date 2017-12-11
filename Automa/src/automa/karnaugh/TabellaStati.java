package automa.karnaugh;

import java.util.ArrayList;
import java.util.TreeMap;

import automa.Automa;
import automa.eccezioni.EccezioneAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.test.Test;
import automa.util.Util;

public class TabellaStati {
	
	private TreeMap<Stato, Segnale> tabella;
	
	final int dimensione;
	
	public TabellaStati(Automa automa) {
		tabella = new TreeMap<>();
		ArrayList<Stato> stati = new ArrayList<>(automa.listaStati());
		dimensione = (int) Math.ceil(Math.log(stati.size()) / Math.log(2));
		ArrayList<Segnale> segnali = new ArrayList<>(Util.setBinario(dimensione));
		for (int i=0; i<stati.size(); i++) {
			tabella.put(stati.get(i), segnali.get(i));
		}
	}
	
	public ArrayList<Stato> getStati() {
		return new ArrayList<Stato>(tabella.keySet());
	}
	
	public Segnale getSegnale(Stato stato) {
		return tabella.get(stato);
	}
	
	public static void main(String[] args) throws EccezioneAutoma {
		Automa automa = Test.testAutoma();
		TabellaStati ts = new TabellaStati(automa);
		for (Stato stato : ts.getStati()) {
			System.out.println(stato.getNome() + " " + ts.getSegnale(stato));
		}
	}

}
