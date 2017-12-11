package automa.gui;

import java.io.Serializable;

import automa.gui.componenteGrafico.ComponenteGrafico;

public interface RegistroComponentiGrafici<T extends ComponenteGrafico> extends Iterable<T>, Serializable {
	
	public boolean aggiungi(T componenteGrafico);
	
	public boolean rimuovi(T componenteGrafico);
	
	public boolean esiste(T componenteGrafico);
	
	public void clear();

}
