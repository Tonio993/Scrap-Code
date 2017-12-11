package automa.editor;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import automa.eccezioni.EccezioneAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public class EditorAutomaTM extends EditorAutomaAbs {
	
	TreeMap<Stato, TreeSet<Transizione>> automa;

	public EditorAutomaTM(int dimensioneInput, int dimensioneOutput)
			throws EccezioneAutoma {
		super(dimensioneInput, dimensioneOutput);
		this.automa = new TreeMap<>();
	}

	@Override
	public boolean aggiungiStato(Stato stato) {
		if (esisteStato(stato)) {
			return false;
		}
		automa.put(stato, new TreeSet<Transizione>());
		return true;
	}

	@Override
	public boolean rimuoviStato(Stato stato) {
		if (!esisteStato(stato)) {
			return false;
		}
		automa.remove(stato);
		rimuoviTransizioni(stato);
		return true;
	}

	@Override
	public boolean aggiungiTransizione(Stato stato1, Stato stato2, Segnale in,
			Segnale out) {
		if (!esisteStato(stato1) || !esisteStato(stato2)) {
			return false;
		}
		if (in.size() != dimensioneInput || out.size() != dimensioneOutput) {
			return false;
		}
		if (esisteTransizione(stato1, in)) {
			return false;
		}
		Transizione transizione = new Transizione(stato1, stato2, in, out);
		return automa.get(stato1).add(transizione);
		
	}

	@Override
	public boolean rimuoviTransizione(Stato stato, Segnale in) {
		if (!esisteStato(stato)) {
			return false;
		}
		for (Transizione transizione : automa.get(stato)) {
			if (transizione.in.equals(in)) {
				automa.get(stato).remove(transizione);
				return true;
			}
		}
		return false;
	}

	@Override
	public int transizioniStato(Stato stato) {
		if (!automa.keySet().contains(stato)) {
			return 0;
		}
		return automa.get(stato).size();
	}

	@Override
	public Set<Stato> setStatiAutoma() {
		return automa.keySet();
	}

	@Override
	public Set<Transizione> setTransizioniStato(Stato stato) {
		return new TreeSet<Transizione>(automa.get(stato));
	}

	@Override
	public Set<Transizione> setTransizioniAutoma() {
		TreeSet<Transizione> set = new TreeSet<>();
		for (Stato stato : automa.keySet()) {
			set.addAll(automa.get(stato));
		}
		return set;
	}

}
