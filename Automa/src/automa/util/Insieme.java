package automa.util;

import java.util.Set;

public interface Insieme<E> {

	public boolean contiene(E elemento);
	
	public boolean aggiungi(E elemento);
	
	public boolean aggiungi(E elemento, E riferimento);
	
	public boolean rimuovi(E elemento);
	
	public Set<E> insiemeDiAppartenenza(E elemento);
	
	public Set<Set<E>> insiemi();	

}
