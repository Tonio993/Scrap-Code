package automa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import automa.builder.BuilderAutomaI;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import util.Multiterator;

public class AutomaHM extends AutomaAbs {
	
	private HashMap<Stato, Set<Transizione>> automa;
	
	public AutomaHM(BuilderAutomaI builder) {
		super(builder);
	}
	
	@Override
	protected void initStrutturaDati() {
		automa = new HashMap<>();
	}

	@Override
	protected void aggiungiStato(Stato stato) {
		automa.put(stato, new HashSet<Transizione>());
	}

	@Override
	protected void aggiungiTransizione(Transizione transizione) {
		automa.get(transizione.statoFrom).add(transizione);
	}

	@Override
	protected Iterator<Stato> getStati() {
		return automa.keySet().iterator();
	}

	@Override
	protected Iterator<Transizione> getTransizioni() {
		return new Multiterator<Transizione>(automa.values());
	}

}
