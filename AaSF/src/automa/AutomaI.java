package automa;

import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public interface AutomaI {
	
	public int sizeInput();
	
	public int sizeOutput();
	
	public Transizione getRisposta(Segnale input);
	
	public Stato statoCorrente();
	
	public void reset();

}
