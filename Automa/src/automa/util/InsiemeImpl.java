package automa.util;

import java.util.HashSet;
import java.util.Set;

public class InsiemeImpl<E> implements Insieme<E> {
	
	Set<Set<E>> insiemi = new HashSet<>();

	@Override
	public boolean contiene(E elemento) {
		for (Set<E> insieme : insiemi) {
			if (insieme.contains(elemento)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean aggiungi(E elemento) {
		if (contiene(elemento)) {
			return false;
		}
		HashSet<E> nuovo = new HashSet<>();
		nuovo.add(elemento);
		insiemi.add(nuovo);
		return true;
	}

	@Override
	public boolean aggiungi(E elemento, E riferimento) {
		if (contiene(elemento)) {
			return false;
		}
		if (contiene(riferimento)) {
			insiemeAppartenenza(riferimento).add(elemento);
		} else {
			HashSet<E> nuovo = new HashSet<>();
			nuovo.add(elemento);
			nuovo.add(riferimento);
			insiemi.add(nuovo);
		}
		return true;
	}

	@Override
	public boolean rimuovi(E elemento) {
		Set<E> insieme = insiemeAppartenenza(elemento);
		if (insieme == null) {
			return false;
		}
		insieme.remove(elemento);
		return true;
	}

	@Override
	public Set<E> insiemeDiAppartenenza(E elemento) {
		for (Set<E> insieme : insiemi) {
			if (insieme.contains(elemento)) {
				return new HashSet<E>(insieme);
			}
		}
		return null;
	}

	@Override
	public Set<Set<E>> insiemi() {
		return new HashSet<Set<E>>(insiemi);
	}
	
	private Set<E> insiemeAppartenenza(E elemento) {
		for (Set<E> insieme : insiemi) {
			if (insieme.contains(elemento)) {
				return insieme;
			}
		}
		return null;
	}

}
