package automa.gui;

import java.util.Iterator;
import java.util.LinkedList;

import automa.gui.componenteGrafico.ComponenteGrafico;

public class RegistroComponentiGraficiLL<T extends ComponenteGrafico> implements RegistroComponentiGrafici<T> {
	
	private static final long serialVersionUID = 2813362929720057143L;
	LinkedList<T> registro = new LinkedList<>();

	@Override
	public Iterator<T> iterator() {
		return registro.iterator();
	}

	@Override
	public boolean aggiungi(T componenteGrafico) {
		return registro.add(componenteGrafico);
	}

	@Override
	public boolean rimuovi(T componenteGrafico) {
		return registro.remove(componenteGrafico);
	}

	@Override
	public boolean esiste(T componenteGrafico) {
		return registro.contains(componenteGrafico);
	}
	
	@Override
	public void clear() {
		registro.clear();
	}

}
