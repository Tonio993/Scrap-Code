package automa.test;

import static automa.oggetti.Valore.V0;
import static automa.oggetti.Valore.V1;
import automa.Automa;
import automa.AutomaTM;
import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.editor.EditorAutomaTM;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Valore;

public class TestAutoma {
	
	public static void main(String[] args) throws EccezioneAutoma {
		EditorAutoma editor = new EditorAutomaTM(1,1);
		Stato SS = new Stato("S");
		Stato ST1 = new Stato("T1");
		Stato ST2 = new Stato("T2");
		Stato ST3 = new Stato("T3");
		Stato SU = new Stato("U");
		Stato SV1 = new Stato("V1");
		Stato SV2 = new Stato("V2");
		Stato SV3 = new Stato("V3");
		editor.aggiungiStato(SS);
		editor.aggiungiStato(ST1);
		editor.aggiungiStato(ST2);
		editor.aggiungiStato(ST3);
		editor.aggiungiStato(SU);
		editor.aggiungiStato(SV1);
		editor.aggiungiStato(SV2);
		editor.aggiungiStato(SV3);
		editor.aggiungiTransizione(SS, SS, new Segnale(V0), new Segnale(V0));
		editor.aggiungiTransizione(SS, ST1, new Segnale(V1), new Segnale(V0));
		editor.aggiungiTransizione(ST1, ST2, new Segnale(V0), new Segnale(V0));
		editor.aggiungiTransizione(ST1, SS, new Segnale(V1), new Segnale(V0));
		editor.aggiungiTransizione(ST2, SS, new Segnale(V0), new Segnale(V0));
		editor.aggiungiTransizione(ST2, ST3, new Segnale(V1), new Segnale(V0));
		editor.aggiungiTransizione(ST3, SU, new Segnale(V0), new Segnale(V1));
		editor.aggiungiTransizione(ST3, SV1, new Segnale(V1), new Segnale(V1));
		editor.aggiungiTransizione(SU, SU, new Segnale(V0), new Segnale(V1));
		editor.aggiungiTransizione(SU, SV1, new Segnale(V1), new Segnale(V1));
		editor.aggiungiTransizione(SV1, SV2, new Segnale(V0), new Segnale(V1));
		editor.aggiungiTransizione(SV1, SV1, new Segnale(V1), new Segnale(V1));
		editor.aggiungiTransizione(SV2, SU, new Segnale(V0), new Segnale(V1));
		editor.aggiungiTransizione(SV2, SV3, new Segnale(V1), new Segnale(V1));
		editor.aggiungiTransizione(SV3, SS, new Segnale(V0), new Segnale(V0));
		editor.aggiungiTransizione(SV3, ST1, new Segnale(V1), new Segnale(V0));
		editor.setStatoIniziale(SS);
		Automa automa = new AutomaTM(editor);
		Valore[] input = {V0,V0,V0,V1,V0,V1,V1,V1,V1,V0,V0,V1,V1,V0,V1,V0,V0,V0};
		for (Valore v : input) {
			automa.inviaSegnale(new Segnale(v));
		}
	}

}
