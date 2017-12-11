package automa;

import java.util.Iterator;

import automa.builder.BuilderAutomaI;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public abstract class AutomaAbs implements AutomaI {
	
	private final Stato statoIniziale;
	private Stato statoCorrente;
	
	private int sizeInput;
	private int sizeOutput;
	
	public AutomaAbs(BuilderAutomaI builder) {
		if (!builder.buildValido()) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato builder non valido");
		}
		initStrutturaDati();
		this.statoIniziale = builder.getStatoIniziale();
		this.statoCorrente = builder.getStatoIniziale();
		this.sizeInput = builder.sizeInput();
		this.sizeOutput = builder.sizeOutput();
		Iterator<Stato> itStati = builder.getStati();
		while (itStati.hasNext()) {
			aggiungiStato(itStati.next());
		}
		Iterator<Transizione> itTransizioni = builder.getTransizioni();
		while (itTransizioni.hasNext()) {
			aggiungiTransizione(itTransizioni.next());
		}
	}
	
	@Override
	public int sizeInput() {
		return sizeInput;
	}
	
	@Override
	public int sizeOutput() {
		return sizeOutput;
	}

	@Override
	public Transizione getRisposta(Segnale input) {
		Iterator<Transizione> itTransizioni = getTransizioni();
		Transizione transizione;
		while (itTransizioni.hasNext()) {
			transizione = itTransizioni.next();
			if (transizione.statoFrom.equals(statoCorrente) && transizione.segnaleIn.equals(input)) {
				statoCorrente = transizione.statoTo;
				return transizione;
			}
		}
		//TODO gestione eccezioni
		throw new IllegalArgumentException("Nessuna transizione trovata");
	}
	
	@Override
	public Stato statoCorrente() {
		return statoCorrente;
	}
	
	@Override
	public void reset() {
		statoCorrente = statoIniziale;
	}
	protected abstract void initStrutturaDati();
	
	protected abstract void aggiungiStato(Stato stato);
	protected abstract void aggiungiTransizione(Transizione transizione);
	
	protected abstract Iterator<Stato> getStati();
	protected abstract Iterator<Transizione> getTransizioni();
	
}
