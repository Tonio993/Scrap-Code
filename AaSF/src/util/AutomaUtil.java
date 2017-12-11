package util;

import java.util.Iterator;

import automa.builder.BuilderAutomaI;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public class AutomaUtil {
	
	private AutomaUtil() {}
	
	public static boolean statiEquivalenti(BuilderAutomaI automa, Stato statoA, Stato statoB) {
		
		if (!automa.buildValido()) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Richiesto automa valido");
		}
		if (!automa.esisteStato(statoA)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato " + statoA.nome + " non presente");
		}
		if (!automa.esisteStato(statoB)) {
			//TODO gestione eccezioni
			throw new IllegalArgumentException("Stato " + statoB.nome + " non presente");
		}
		
		Iterator<Transizione> it = automa.getTransizioni(statoA);
		while (it.hasNext()) {
			if (!contieneTransizioneEquivalente(automa, statoB, it.next())) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean contieneTransizioneEquivalente(BuilderAutomaI automa, Stato stato, Transizione transizione) {
		Iterator<Transizione> it = automa.getTransizioni(stato);
		while (it.hasNext()) {
			if (TransizioneUtil.equivalente(transizione, it.next())) {
				return true;
			}
		}
		return false;
	}
}