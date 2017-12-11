package automa.gui;

import automa.Automa;
import automa.AutomaTM;
import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.editor.EditorAutomaTM;
import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;

public class AutomaDaGUI {
	
	public static Automa getAutoma(PannelloGrafica pannelloGrafica, int ingressi, int uscite) throws EccezioneAutoma {
		EditorAutoma automa = new EditorAutomaTM(ingressi, uscite);
		for (StatoGUI statoGUI : pannelloGrafica.registroStati) {
			Stato stato = new Stato(statoGUI.getNome());
			automa.aggiungiStato(stato);
			if (statoGUI.isStatoIniziale()) {
				automa.setStatoIniziale(stato);
			}
		}
		for (TransizioneGUI transizione : pannelloGrafica.registroTransizioni) {
			Stato statoIniziale = new Stato(transizione.getStatoUscente().getNome());
			Stato statoFinale = new Stato(transizione.getStatoEntrante().getNome());
			Segnale in = Segnale.String2Sig(transizione.getIngresso());
			Segnale out = Segnale.String2Sig(transizione.getUscita());
			automa.aggiungiTransizione(statoIniziale, statoFinale, in, out);
		}
		return new AutomaTM(automa);
	}

}
