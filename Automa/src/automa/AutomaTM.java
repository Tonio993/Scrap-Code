package automa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public class AutomaTM extends AutomaAbs {
	
	TreeMap<Stato,TreeSet<Transizione>> automa;

	public AutomaTM(EditorAutoma editorAutoma) throws EccezioneAutoma {
		super(editorAutoma);
	}

	@Override
	public Stato getStatoCorrente() {
		return statoCorrente;
	}

	@Override
	public void reset() {
		statoCorrente = statoIniziale;
	}

	@Override
	public Transizione inviaSegnale(Segnale in) {
		for (Transizione transizione : automa.get(statoCorrente)) {
			if (transizione.in.equals(in)) {
				statoCorrente = transizione.statoFinale;
				return transizione;
			}
		}
		throw new RuntimeException();
	}
	
	@Override
	public List<Stato> listaStati() {
		return new ArrayList<>(automa.keySet());
	}
	
	@Override
	public List<Transizione> listaTransizioni() {
		List<Transizione> lista = new LinkedList<>();
		for (TreeSet<Transizione> set : automa.values()) {
			lista.addAll(set);
		}
		return lista;
	}
	
	@Override
	public List<Transizione> listaTransizioni(Stato stato) {
		List<Transizione> lista = new LinkedList<>();
		for (Transizione transizione : automa.get(stato)) {
			lista.add(transizione);
		}
		return lista;
	}
	
	@Override
	protected void setStati(Set<Stato> setStati) {
		for (Stato stato : setStati) {
			automa.put(stato, new TreeSet<Transizione>());
		}
	}

	@Override
	protected void setTransizioni(Set<Transizione> setTransizioni) {
		for (Transizione transizione : setTransizioni) {
			automa.get(transizione.statoIniziale).add(transizione);
		}
	}
	
	@Override
	protected void struct() {
		this.automa = new TreeMap<>();
	}

}
