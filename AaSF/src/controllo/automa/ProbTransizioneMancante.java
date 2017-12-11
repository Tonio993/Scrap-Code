package controllo.automa;

import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import controllo.Problema;
import util.ObjectUtil;

public class ProbTransizioneMancante extends Problema {
	
	private final Stato stato;
	private final Segnale segnale;
	
	public ProbTransizioneMancante(Stato stato, Segnale segnale) {
		super(true);
		ObjectUtil.checkNull(stato);
		ObjectUtil.checkNull(segnale);
		this.stato = stato;
		this.segnale = segnale;
	}
	
	public Stato getStato() {
		return stato;
	}
	
	public Segnale getSegnale() {
		return segnale;
	}

}
