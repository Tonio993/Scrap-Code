package automa.automaMinimo;

import java.util.ArrayList;
import java.util.Set;

import automa.Automa;
import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.editor.EditorAutomaTM;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;
import automa.util.Insieme;
import automa.util.InsiemeImpl;

public class AutomaMinimoImpl extends AutomaMinimoAbs {
	
	InsiemeImpl<Stato> statiEquivalenti;

	public AutomaMinimoImpl(Automa automa) {
		super(automa);
		statiEquivalenti = new InsiemeImpl<Stato>();
		boolean riscontro1, riscontro2;
		ArrayList<Stato> stati = new ArrayList<Stato>(automa.listaStati());
		do {
			riscontro1 = false;
			for (int i=0;i<stati.size() - 1;i++) {
				for (int j=i+1;j<stati.size();j++) {
					riscontro2 = false;
					for (Transizione transizione1 : automa.listaTransizioni(stati.get(i))) {
						riscontro2 = false;
						for (Transizione transizione2 : automa.listaTransizioni(stati.get(j))) {
							if (transizione1.in.equals(transizione2.in)) {
								if (transizione1.out.equals(transizione2.out)) {
									if (transizione1.statoFinale.equals(transizione2.statoFinale) || statiEquivalenti.insiemeDiAppartenenza(transizione1.statoFinale) != null && statiEquivalenti.insiemeDiAppartenenza(transizione1.statoFinale).contains(transizione2.statoFinale)) {
										riscontro2 = true;
										break;
									}
								}
							}
						}
						if (!riscontro2) {
							break;
						}
					}
					if (riscontro2) {
						riscontro1 = statiEquivalenti.aggiungi(stati.get(i), stati.get(j));
					}
				}
			}
		} while (riscontro1);
		for (Stato stato : stati) {
			if (!statiEquivalenti.contiene(stato)) {
				statiEquivalenti.aggiungi(stato);
			}
		}
		
	}

	@Override
	public Insieme<Stato> statiEquivalenti() {
		return statiEquivalenti;
	}

	@Override
	public EditorAutoma getAutomaMinimo() throws EccezioneAutoma {
		EditorAutomaTM automaMinimo = new EditorAutomaTM(dimensioneInput, dimensioneOutput);
		for (Set<Stato> stato : statiEquivalenti.insiemi()) {
			automaMinimo.aggiungiStato(stato.iterator().next());
		}
		for (Transizione transizione : this.automa.listaTransizioni()) {
			Stato statoIniziale = statiEquivalenti.insiemeDiAppartenenza(transizione.statoIniziale).iterator().next();
			Stato statoFinale = statiEquivalenti.insiemeDiAppartenenza(transizione.statoFinale).iterator().next();
			automaMinimo.aggiungiTransizione(statoIniziale, statoFinale, transizione.in, transizione.out);
		}
		automaMinimo.setStatoIniziale(statiEquivalenti.insiemeDiAppartenenza(automa.getStatoIniziale()).iterator().next());
		return automaMinimo;
	}
	
}
