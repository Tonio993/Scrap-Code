package automa.builder;

import java.util.Iterator;

import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import controllo.Controllo;
import util.ObjectUtil;

public abstract class BuilderAutomaAbs implements BuilderAutomaI {
	
	private int sizeInput;
	private int sizeOutput;
	
	private Stato statoIniziale;
	
	@Override
	public boolean contieneStati() {
		return getStati().hasNext();
	}
	
	@Override
	public boolean contieneTransizioni() {
		return getTransizioni().hasNext();
	}
	
	@Override
	public int sizeStati() {
		int n = 0;
		Iterator<Stato> itStati = getStati();
		while (itStati.hasNext()) {
			itStati.next();
			n++;
		}
		return n;
	}
	
	@Override
	public int sizeTransizioni() {
		int n = 0;
		Iterator<Transizione> itTransizioni = getTransizioni();
		while (itTransizioni.hasNext()) {
			itTransizioni.next();
			n++;
		}
		return n;
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
	public Controllo controlloBuild() {
		Controllo controllo = new Controllo();
		
		return controllo;
	}
	
	@Override
	public boolean buildValido() {
		return contieneStati() 
			&& sizeTransizioni() == ((int) Math.pow(2, sizeInput)) * sizeStati()
			&& statoIniziale != null;
	}
	
	@Override
	public boolean esisteStato(Stato stato) {
		ObjectUtil.checkNull(stato);
		Iterator<Stato> itStati = getStati();
		while (itStati.hasNext()) {
			if (stato.equals(itStati.next())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void aggiungiStato(Stato stato) {
		ObjectUtil.checkNull(stato);
		if (esisteStato(stato)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato gia' presente");
		}
		aggiungiStatoImpl(stato);
	}

	@Override
	public void rimuoviStato(Stato stato) {
		ObjectUtil.checkNull(stato);
		if (!esisteStato(stato)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato non presente");
		}
		rimuoviStatoImpl(stato);
		Iterator<Transizione> itTransizioni = getTransizioni();
		while (itTransizioni.hasNext()) {
			Transizione transizione = itTransizioni.next();
			if (transizione.statoTo.equals(stato)) {
				rimuoviTransizioneImpl(transizione);
			}
		}
	}
	
	@Override
	public Stato getStatoIniziale() {
		return statoIniziale;
	}

	@Override
	public void setStatoIniziale(Stato stato) {
		ObjectUtil.checkNull(stato);
		if (!esisteStato(stato)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato non presente");
		}
		this.statoIniziale = stato;
	}
	
	@Override
	public boolean esisteTransizione(Transizione transizione) {
		ObjectUtil.checkNull(transizione);
		Iterator<Transizione> itTransizioni = getTransizioni();
		while (itTransizioni.hasNext()) {
			if (transizione.equals(itTransizioni.next())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void aggiungiTransizione(Transizione transizione) {
		ObjectUtil.checkNull(transizione);
		checkStatiTransizione(transizione);
		checkSegnaliTransizione(transizione);
		if (esisteTransizione(transizione)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Transizione gia' esistente");
		}
		if (!contieneTransizioni()) {
			sizeInput = transizione.segnaleIn.size();
			sizeOutput = transizione.segnaleOut.size();
		}
		aggiungiTransizioneImpl(transizione);
	}
	
	@Override
	public void rimuoviTransizione(Transizione transizione) {
		ObjectUtil.checkNull(transizione);
		checkStatiTransizione(transizione);
		checkSegnaliTransizione(transizione);
		if (!esisteTransizione(transizione)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Transizione non presente");
		}
		rimuoviTransizioneImpl(transizione);
		if (!contieneTransizioni()) {
			sizeInput = 0;
			sizeOutput = 0;
		}
	}

	private void checkStatiTransizione(Transizione transizione) {
		Iterator<Stato> itStati = getStati();
		boolean statoFromPresente = false;
		boolean statoToPresente = false;
		while (itStati.hasNext()) {
			Stato stato = itStati.next();
			if (transizione.statoFrom.equals(stato)) {
				statoFromPresente = true;
			}
			if (transizione.statoTo.equals(stato)) {
				statoToPresente = true;
			}
			if (statoFromPresente && statoToPresente) {
				break;
			}
		}
		if (!statoFromPresente || !statoToPresente) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stati di transizione non presenti");
		}
	}
	
	private void checkSegnaliTransizione(Transizione transizione) {
		if (sizeInput != 0 && sizeOutput != 0) {
			if (transizione.segnaleIn.size() != sizeInput || transizione.segnaleOut.size() != sizeOutput) {
				//TODO gestione eccezioni
				throw new IllegalArgumentException("Transizione con dimensione di input/output non coerente con le transizione gia' presenti");
			}
		}
	}
	
	protected abstract void aggiungiStatoImpl(Stato stato);

	protected abstract void rimuoviStatoImpl(Stato stato);

	protected abstract void aggiungiTransizioneImpl(Transizione transizione);

	protected abstract void rimuoviTransizioneImpl(Transizione transizione);
	
}
