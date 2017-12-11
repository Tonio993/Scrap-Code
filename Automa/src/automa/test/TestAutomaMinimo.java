package automa.test;

import static automa.oggetti.Valore.V0;
import static automa.oggetti.Valore.V1;
import automa.Automa;
import automa.AutomaTM;
import automa.automaMinimo.AutomaMinimo;
import automa.automaMinimo.AutomaMinimoImpl;
import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.editor.EditorAutomaTM;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Valore;

public class TestAutomaMinimo {
	
	public static void main(String[] args) throws EccezioneAutoma {
		EditorAutoma editor = new EditorAutomaTM(1,1);
		
		Stato statoA = new Stato("A");
		Stato statoB = new Stato("B");
		Stato statoC = new Stato("C");
		Stato statoD = new Stato("D");
		Stato statoE = new Stato("E");
		Stato statoF = new Stato("F");
		Stato statoG = new Stato("G");
		Stato atatoH = new Stato("H");
		
		editor.aggiungiStato(statoA);
		editor.aggiungiStato(statoB);
		editor.aggiungiStato(statoC);
		editor.aggiungiStato(statoD);
		editor.aggiungiStato(statoE);
		editor.aggiungiStato(statoF);
		editor.aggiungiStato(statoG);
		editor.aggiungiStato(atatoH);
		
		Segnale v1 = new Segnale(V1);
		Segnale v0 = new Segnale(V0);

		editor.aggiungiTransizione(statoA, statoA, v0, v1);
		editor.aggiungiTransizione(statoA, statoB, v1, v0);
		editor.aggiungiTransizione(statoB, statoC, v0, v0);
		editor.aggiungiTransizione(statoB, statoD, v1, v0);
		editor.aggiungiTransizione(statoC, statoE, v0, v0);
		editor.aggiungiTransizione(statoC, statoF, v1, v0);
		editor.aggiungiTransizione(statoD, statoG, v0, v1);
		editor.aggiungiTransizione(statoD, atatoH, v1, v0);
		editor.aggiungiTransizione(statoE, statoA, v0, v0);
		editor.aggiungiTransizione(statoE, statoB, v1, v1);
		editor.aggiungiTransizione(statoF, statoC, v0, v0);
		editor.aggiungiTransizione(statoF, statoD, v1, v0);
		editor.aggiungiTransizione(statoG, statoE, v0, v0);
		editor.aggiungiTransizione(statoG, statoF, v1, v0);
		editor.aggiungiTransizione(atatoH, statoG, v0, v0);
		editor.aggiungiTransizione(atatoH, atatoH, v1, v1);
		editor.setStatoIniziale(statoA);
		
		Automa automa = new AutomaTM(editor);
		AutomaMinimo t = new AutomaMinimoImpl(automa);
		
		Automa automaMinimo = new AutomaTM(t.getAutomaMinimo());
		
		Valore[] input = {V0,V0,V0,V1,V0,V1,V1,V1,V1,V0,V0,V1,V1,V0,V1,V0,V0,V0};
		for (Valore v : input) {
			System.out.print(v.valore + " " + automa.getStatoCorrente() + " " + automa.inviaSegnale(new Segnale(v)));
			System.out.println(" " + automa.getStatoCorrente());
		}
		
		System.out.println();
		
		for (Valore v : input) {
			System.out.print(v.valore + " " + automaMinimo.getStatoCorrente() + " " + automaMinimo.inviaSegnale(new Segnale(v)));
			System.out.println(" " + automaMinimo.getStatoCorrente());
		}
	}

}
