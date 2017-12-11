package automa.builder;

import java.util.Iterator;

import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import controllo.Controllo;

public interface BuilderAutomaI {
	
	public boolean contieneStati();
	
	public boolean contieneTransizioni();
	
	public int sizeStati();
	
	public int sizeTransizioni();
	
	public int sizeInput();
	
	public int sizeOutput();
	
	public Controllo controlloBuild();
	
	public boolean buildValido();
	
	public boolean esisteStato(Stato stato);
	
	public Iterator<Stato> getStati();
	
	public void aggiungiStato(Stato stato);
	
	public void rimuoviStato(Stato stato);

	public Stato getStatoIniziale();
	
	public void setStatoIniziale(Stato stato);
	
	public boolean esisteTransizione(Transizione transizione);
	
	public Iterator<Transizione> getTransizioni();
	
	public Iterator<Transizione> getTransizioni(Stato stato);
	
	public void aggiungiTransizione(Transizione transizione);
	
	public void rimuoviTransizione(Transizione transizione);
	
}
