package automa.karnaugh;

import automa.util.Util;

public class Coppia<R, C> {
	
	public final R elementoA;
	public final C elementoB;
	
	public Coppia(R elementoA, C elementoB) {
		Util.checkNull(elementoA, elementoB);
		this.elementoA = elementoA;
		this.elementoB = elementoB;
	}

}
