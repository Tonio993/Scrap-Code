package controllo;

public abstract class Problema {
	
	private boolean bloccante;
	
	public Problema(boolean bloccante) {
		this.bloccante = bloccante;
	}
	
	public boolean isBloccante() {
		return bloccante;
	}

}
