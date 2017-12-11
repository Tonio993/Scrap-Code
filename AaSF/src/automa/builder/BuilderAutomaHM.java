package automa.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import util.Multiterator;

public class BuilderAutomaHM extends BuilderAutomaAbs {
	
	HashMap<Stato, Set<Transizione>> automa;
	
	public BuilderAutomaHM() {
		automa = new HashMap<>();
	}

	@Override
	public Iterator<Stato> getStati() {
		return automa.keySet().iterator();
	}

	@Override
	public Iterator<Transizione> getTransizioni() {
		return new Multiterator<Transizione>(automa.values());
	}

	@Override
	public Iterator<Transizione> getTransizioni(Stato stato) {
		return automa.get(stato).iterator();
	}

	@Override
	protected void aggiungiStatoImpl(Stato stato) {
		automa.put(stato, new HashSet<>());
		
	}

	@Override
	protected void rimuoviStatoImpl(Stato stato) {
		automa.remove(stato);
	}

	@Override
	protected void aggiungiTransizioneImpl(Transizione transizione) {
		automa.get(transizione.statoFrom).add(transizione);
	}

	@Override
	protected void rimuoviTransizioneImpl(Transizione transizione) {
		automa.get(transizione.statoFrom).remove(transizione);
	}

}
