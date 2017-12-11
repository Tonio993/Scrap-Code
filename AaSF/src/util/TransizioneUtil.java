package util;

import automa.oggetti.Transizione;

public class TransizioneUtil {
	
	private TransizioneUtil() {}
	
	public static boolean equivalente(Transizione transizioneA, Transizione transizioneB) {
		return transizioneA.segnaleIn.equals(transizioneB.segnaleIn)
			&& transizioneA.segnaleOut.equals(transizioneB.segnaleOut)
			&& transizioneA.statoTo.equals(transizioneB.statoTo);
	}
	
}
