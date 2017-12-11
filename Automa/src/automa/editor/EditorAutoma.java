package automa.editor;

import java.util.Set;

import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public interface EditorAutoma {
	
	public int dimensioneInput();
	
	public int dimensioneOutput();
	
	public int numeroStati();
	
	public int numeroTransizioni();
	
	public boolean esisteStato(Stato stato);
	
	public boolean aggiungiStato(Stato stato);
	
	public boolean rimuoviStato(Stato stato);
	
	public boolean esisteTransizione(Stato stato, Segnale in);
	
	public boolean esisteTransizione(Stato stato1, Stato stato2);
	
	public boolean esisteTransizione(Stato stato1, Stato stato2, Segnale in);
	
	public boolean aggiungiTransizione(Stato stato1, Stato stato2, Segnale in, Segnale out);
	
	public boolean rimuoviTransizione(Stato stato, Segnale in);
	
	public Stato getStatoIniziale();
	
	public boolean setStatoIniziale(Stato stato);
	
	public int transizioniStato(Stato stato);
	
	public Set<Stato> setStatiAutoma();
	
	public Set<Transizione> setTransizioniStato(Stato stato);
	
	public Set<Transizione> setTransizioniAutoma();
	
	public boolean verificaStato(Stato stato);
	
	public boolean verificaAutoma();

}
