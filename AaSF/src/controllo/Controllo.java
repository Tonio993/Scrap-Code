package controllo;

import java.util.LinkedList;

import util.ObjectUtil;

public class Controllo {
	
	LinkedList<Problema> problemi;
	
	public boolean isOk() {
		for (Problema problema : problemi) {
			if (problema.isBloccante()) {
				return false;
			}
		}
		return true;
	}
	
	public void aggiungi(Problema problema) {
		ObjectUtil.checkNull(problema);
		problemi.add(problema);
	}
	
	public void rimuovi(Problema problema) {
		ObjectUtil.checkNull(problema);
		problemi.remove(problema);
	}

}
