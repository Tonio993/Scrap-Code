package automa;

import java.util.Set;

import automa.eccezioni.EccezioneAutoma;
import automa.eccezioni.EccezioneAutomaNonValido;
import automa.editor.EditorAutoma;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public abstract class AutomaAbs implements Automa {
	
	protected final int dimensioneInput;
	protected final int dimensioneOutput;
	
	protected final Stato statoIniziale;
	protected Stato statoCorrente;
	
	public AutomaAbs(EditorAutoma editorAutoma) throws EccezioneAutoma {
		if (!editorAutoma.verificaAutoma()) {
			throw new EccezioneAutomaNonValido();
		}
		dimensioneInput = editorAutoma.dimensioneInput();
		dimensioneOutput = editorAutoma.dimensioneOutput();
		statoIniziale = statoCorrente = editorAutoma.getStatoIniziale();
		struct();
		setStati(editorAutoma.setStatiAutoma());
		setTransizioni(editorAutoma.setTransizioniAutoma());
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
	public Stato getStatoIniziale() {
		return statoIniziale;
	}
	
	@Override
	public Stato getStato(Stato statoIniziale, Segnale in) {
		for (Transizione transizione : listaTransizioni()) {
			if (transizione.statoIniziale.equals(statoIniziale) && transizione.in.equals(in)) {
				return transizione.statoFinale;
			}
		}
		throw new RuntimeException();
	}

	@Override
	public Segnale getSegnale(Stato statoIniziale, Segnale in) {
		for (Transizione transizione : listaTransizioni()) {
			if (transizione.statoIniziale.equals(statoIniziale) && transizione.in.equals(in)) {
				return transizione.out;
			}
		}
		throw new RuntimeException();
	}
	
	protected abstract void setStati(Set<Stato> setStati);
	
	protected abstract void setTransizioni(Set<Transizione> setTransizioni);
	
	protected abstract void struct();

}
