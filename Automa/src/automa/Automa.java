package automa;

import java.util.List;

import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public interface Automa {
	
	public int dimensioneInput();
	
	public int dimensioneOutput();
	
	public Stato getStatoCorrente();
	
	public Stato getStatoIniziale();

	public void reset();
	
	public Transizione inviaSegnale(Segnale in);
	
	public List<Stato> listaStati();
	
	public List<Transizione> listaTransizioni();
	
	public List<Transizione> listaTransizioni(Stato stato);
	
	public Stato getStato(Stato statoIniziale, Segnale in);
	
	public Segnale getSegnale(Stato statoIniziale, Segnale in);
}
