package automa.gui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.TreeSet;

import automa.Automa;
import automa.AutomaTM;
import automa.automaMinimo.AutomaMinimo;
import automa.automaMinimo.AutomaMinimoImpl;
import automa.eccezioni.EccezioneAutoma;
import automa.editor.EditorAutoma;
import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.oggetti.Segnale;
import automa.oggetti.Stato;
import automa.oggetti.Transizione;

public class MediatoreGUI {
	
	FinestraPrincipale finestraPrincipale;
	PannelloGrafica pannelloGrafica;
	PannelloGraficaAutomaMinimo pannelloGraficaAutomaMinimo;
	PannelloProprieta pannelloProprieta;
	PannelloAutoma pannelloAutoma, pannelloAutomaMinimo;
	PannelloTabelle pannelloTabelle;
	BarraMenu barraMenu;
	
	public FinestraPrincipale getPannelloPrincipale() {
		return finestraPrincipale;
	}
	
	public void setPannelloPrincipale(FinestraPrincipale finestraPrincipale) {
		this.finestraPrincipale = finestraPrincipale;
	}
	
	public PannelloGrafica getPannelloGrafica() {
		return pannelloGrafica;
	}
	
	public void setPannelloGrafica(PannelloGrafica pannelloGrafica) {
		this.pannelloGrafica = pannelloGrafica;
	}
	
	public PannelloGraficaAutomaMinimo getPannelloGraficaAutomaMinimo() {
		return pannelloGraficaAutomaMinimo;
	}

	public void setPannelloGraficaAutomaMinimo(
			PannelloGraficaAutomaMinimo pannelloGraficaAutomaMinimo) {
		this.pannelloGraficaAutomaMinimo = pannelloGraficaAutomaMinimo;
	}

	public PannelloProprieta getPannelloProprieta() {
		return pannelloProprieta;
	}
	
	public void setPannelloProprieta(PannelloProprieta pannelloProprieta) {
		this.pannelloProprieta = pannelloProprieta;
	}
	
	public PannelloAutoma getPannelloAutoma() {
		return pannelloAutoma;
	}

	public void setPannelloAutoma(PannelloAutoma pannelloAutoma) {
		this.pannelloAutoma = pannelloAutoma;
	}

	public PannelloAutoma getPannelloAutomaMinimo() {
		return pannelloAutomaMinimo;
	}

	public void setPannelloAutomaMinimo(PannelloAutoma pannelloAutomaMinimo) {
		this.pannelloAutomaMinimo = pannelloAutomaMinimo;
	}

	public PannelloTabelle getPannelloTabelle() {
		return pannelloTabelle;
	}

	public void setPannelloTabelle(PannelloTabelle pannelloTabelle) {
		this.pannelloTabelle = pannelloTabelle;
	}

	public BarraMenu getBarraMenu() {
		return barraMenu;
	}
	
	public void setBarraMenu(BarraMenu barraMenu) {
		this.barraMenu = barraMenu;
	}
	
	public void setStato(StatoGUI stato) {
		pannelloProprieta.setStato(stato);
	}
	
	public void notifica() {
		pannelloGrafica.setDimensione();
		pannelloGrafica.repaint();
		pannelloGraficaAutomaMinimo.setDimensione();
		pannelloGraficaAutomaMinimo.repaint();
	}
	
	public LinkedList<TransizioneGUI> listaTransizioni(StatoGUI stato) {
		LinkedList<TransizioneGUI> lista = new LinkedList<>();
		for (TransizioneGUI transizione : pannelloGrafica.registroTransizioni) {
			if (transizione.getStatoUscente().equals(stato)) {
				lista.add(transizione);
			}
		}
		for (TransizioneGUI transizione : pannelloGraficaAutomaMinimo.registroTransizioni) {
			if (transizione.getStatoUscente().equals(stato)) {
				lista.add(transizione);
			}
		}
		return lista;
	}
	
	public LinkedList<StatoGUI> listaStati() {
		LinkedList<StatoGUI> lista = new LinkedList<>();
		for (StatoGUI stato : pannelloGrafica.registroStati) {
			lista.add(stato);
		}
		return lista;
	}
	
	public void setStatoIniziale(StatoGUI stato) {
		pannelloGrafica.setStatoIniziale(stato);
	}
	
	public Dimension getDimensioneGrafica() {
		return finestraPrincipale.getDimensioneGrafica();
	}
	
	public void setAutoma(Automa automa) {
		pannelloAutoma.setAutoma(automa);
		pannelloTabelle.setAutoma(automa);
		finestraPrincipale.tpPannelloCentrale.setEnabledAt(1, automa != null);
		finestraPrincipale.tpPannelloCentrale.setEnabledAt(2, automa != null);
		try {
			if (automa != null) {
				AutomaMinimo automaMinimo = new AutomaMinimoImpl(automa);
				EditorAutoma editor = automaMinimo.getAutomaMinimo();
				Automa automaMin = new AutomaTM(editor);
				pannelloAutomaMinimo.setAutoma(automaMin);
			}
		} catch (EccezioneAutoma e) {
			e.printStackTrace();
		}
		pannelloGraficaAutomaMinimo.setAutoma(automa);
		if (automa == null) {
			finestraPrincipale.tpPannelloCentrale.setSelectedIndex(0);
		}
	}
	
	public void setEvidenziati(TreeSet<Stato> stati, TreeSet<Transizione> transizioni, boolean minimo) {
		if (!minimo) {
			for (StatoGUI stato : pannelloGrafica.statiEvidenziati) {
				stato.setEvidenziato(false);
			}
			pannelloGrafica.statiEvidenziati.clear();
			for (TransizioneGUI transizioneGUI : pannelloGrafica.transizioniEvidenziate) {
				transizioneGUI.setEvidenziati(false);
			}
			pannelloGrafica.transizioniEvidenziate.clear();
			if (stati != null) {
				for (StatoGUI stato : pannelloGrafica.registroStati) {
					if (stati.contains(new Stato(stato.getNome()))) {
						stato.setEvidenziato(true);
						pannelloGrafica.statiEvidenziati.add(stato);
					}
				}
			}
			if (transizioni != null) {
				for (TransizioneGUI transizioneGUI : pannelloGrafica.registroTransizioni) {
					Transizione trs = new Transizione(new Stato(transizioneGUI.getStatoUscente().getNome()), new Stato(transizioneGUI.getStatoEntrante().getNome()), Segnale.String2Sig(transizioneGUI.getIngresso()), Segnale.String2Sig(transizioneGUI.getUscita()));
					if (transizioni.contains(trs)) {
						transizioneGUI.setEvidenziati(true);
						pannelloGrafica.transizioniEvidenziate.add(transizioneGUI);
					}
				}
			}
		} else {
			for (StatoGUI stato : pannelloGraficaAutomaMinimo.statiEvidenziati) {
				stato.setEvidenziato(false);
			}
			pannelloGraficaAutomaMinimo.statiEvidenziati.clear();
			for (TransizioneGUI transizioneGUI : pannelloGraficaAutomaMinimo.transizioniEvidenziate) {
				transizioneGUI.setEvidenziati(false);
			}
			pannelloGraficaAutomaMinimo.transizioniEvidenziate.clear();
			if (stati != null) {
				for (StatoGUI stato : pannelloGraficaAutomaMinimo.registroStati) {
					if (stati.contains(new Stato(stato.getNome()))) {
						stato.setEvidenziato(true);
						pannelloGraficaAutomaMinimo.statiEvidenziati.add(stato);
					}
				}
			}
			if (transizioni != null) {
				for (TransizioneGUI transizioneGUI : pannelloGraficaAutomaMinimo.registroTransizioni) {
					Transizione trs = new Transizione(new Stato(transizioneGUI.getStatoUscente().getNome()), new Stato(transizioneGUI.getStatoEntrante().getNome()), Segnale.String2Sig(transizioneGUI.getIngresso()), Segnale.String2Sig(transizioneGUI.getUscita()));
					if (transizioni.contains(trs)) {
						transizioneGUI.setEvidenziati(true);
						pannelloGraficaAutomaMinimo.transizioniEvidenziate.add(transizioneGUI);
					}
				}
			}
		}
		notifica();
	}

}
