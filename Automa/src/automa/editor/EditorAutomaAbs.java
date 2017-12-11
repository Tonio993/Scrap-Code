package automa.editor;

import automa.eccezioni.EccezioneAutoma;
import automa.eccezioni.EccezioneDimensioneInput;
import automa.eccezioni.EccezioneDimensioneOutput;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.Util;


public abstract class EditorAutomaAbs implements EditorAutoma {
	
	protected final int dimensioneInput;
	protected final int dimensioneOutput;
	
	protected Stato statoIniziale;
	
	public EditorAutomaAbs(int dimensioneInput, int dimensioneOutput) throws EccezioneAutoma {
		if (dimensioneInput <= 0) {
			throw new EccezioneDimensioneInput(dimensioneInput);
		}
		if (dimensioneOutput <= 0) {
			throw new EccezioneDimensioneOutput(dimensioneOutput);
		}
		this.dimensioneInput = dimensioneInput;
		this.dimensioneOutput = dimensioneOutput;
	}
	
	@Override
	public int dimensioneInput() {
		return dimensioneInput;
	}

	@Override
	public int dimensioneOutput() {
		return dimensioneOutput;
	}
	
	@Override
	public int numeroStati() {
		int n = 0;
		for (@SuppressWarnings("unused") Stato stato : setStatiAutoma()) {
			n++;
		}
		return n;
	}
	
	@Override
	public int numeroTransizioni() {
		int n = 0;
		for (@SuppressWarnings("unused") Transizione transizione : setTransizioniAutoma()) {
			n++;
		}
		return n;
	}

	@Override
	public boolean esisteStato(Stato stato) {
		return setStatiAutoma().contains(stato);
	}
	
	@Override
	public boolean esisteTransizione(Stato stato, Segnale in) {
		for  (Transizione transizione : this.setTransizioniStato(stato)) {
			if (transizione.in.equals(in)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean esisteTransizione(Stato stato1, Stato stato2) {
		for  (Transizione transizione : this.setTransizioniStato(stato1)) {
			if (transizione.statoFinale.equals(stato2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean esisteTransizione(Stato stato1, Stato stato2, Segnale in) {
		for  (Transizione transizione : this.setTransizioniStato(stato1)) {
			if (transizione.in.equals(in) && transizione.statoFinale.equals(stato2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Stato getStatoIniziale() {
		return statoIniziale;
	}

	@Override
	public boolean setStatoIniziale(Stato stato) {
		Util.checkNull(stato);
		if (!setStatiAutoma().contains(stato)) {
			return false;
		}
		this.statoIniziale = stato;
		return true;
	}

	@Override
	public boolean verificaStato(Stato stato) {
		return transizioniStato(stato) == (int) Math.pow(2, dimensioneInput);
	}
	
	@Override
	public boolean verificaAutoma() {
		if (getStatoIniziale() == null) {
			return false;
		}
		for (Stato stato : setStatiAutoma()) {
			if (!verificaStato(stato)) {
				return false;
			}
		}
		return true;
	}
	
	protected void rimuoviTransizioni(Stato stato) {
		for (Transizione transizione : setTransizioniAutoma()) {
			if (transizione.statoFinale.equals(stato)) {
				rimuoviTransizione(transizione.statoIniziale, transizione.in);
			}
		}
	}
	
}