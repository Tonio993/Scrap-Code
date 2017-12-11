package automa.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JPanel;

import math.geom2d.Point2D;
import automa.Automa;
import automa.automaMinimo.AutomaMinimoImpl;
import automa.eccezioni.EccezioneAutoma;
import automa.gui.componenteGrafico.ComponenteGrafico;
import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.oggetti.Stato;
import automa.util.Insieme;
import automa.util.Util;

public class PannelloGraficaAutomaMinimo extends JPanel {

	private static final long serialVersionUID = -1802721350447962786L;
	
	private static final Color COLORE_SFONDO = Color.black;
	private static final Color COLORE_LINK = Color.green;
	
	RegistroComponentiGrafici<StatoGUI> registroStati;
	RegistroComponentiGrafici<TransizioneGUI> registroTransizioni;
	StatoGUI selezionato;
	StatoGUI statoIniziale;
	LinkedList<StatoGUI> statiEvidenziati;
	LinkedList<TransizioneGUI> transizioniEvidenziate;
	
	private MediatoreGUI mediator;
	
	private boolean drag = false, transizione = false;
	int contatoreStati = 1;
	private double mouseX, mouseY, startX, startY;
	private double X1, Y1, X2, Y2;
	
	public PannelloGraficaAutomaMinimo(MediatoreGUI mediator) throws EccezioneAutoma {
		Util.checkNull(mediator);
		this.mediator = mediator;
		mediator.setPannelloGraficaAutomaMinimo(this);
		this.registroStati = new RegistroComponentiGraficiLL<>();
		this.registroTransizioni = new RegistroComponentiGraficiLL<>();
		statiEvidenziati = new LinkedList<>();
		transizioniEvidenziate = new LinkedList<>();
		strutturaFinestra();
	}
	
	private void strutturaFinestra() {
		this.setBackground(COLORE_SFONDO);
		this.setPreferredSize(new Dimension(800,600));
		this.setFocusable(true);
		this.addMouseListener(listenerSelezione);
		this.addMouseMotionListener(listenerDrag);
		this.addMouseMotionListener(listenerTransizioneDrag);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if (transizione) {
			g2.setColor(COLORE_LINK);
			g2.draw(new Line2D.Double(X1, Y1, X2, Y2));
		}
		for (ComponenteGrafico c : registroTransizioni) {
			c.draw(g2);
		}
		for (ComponenteGrafico c : registroStati) {
			c.draw(g2);
		}
	}
	
	MouseListener listenerSelezione = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if (e.getButton() != MouseEvent.BUTTON1) {
				return;
			}
			if (selezionato != null) {
				selezionato.setSelezionato(false);
				selezionato = null;
			}
			for (StatoGUI componente : registroStati) {
				if (componente.getSpazio().isInside(new Point2D(e.getX(), e.getY()))) {
					selezionato = componente;
				}
			}
			mediator.setStato(selezionato);
			
			if (selezionato != null) {
				selezionato.setSelezionato(true);
				Point2D posizione = selezionato.getPosizione();
				mouseX = e.getX();
				mouseY = e.getY();
				startX = posizione.x();
				startY = posizione.y();
				drag = true;
			}
			repaint();
		}
		public void mouseReleased(MouseEvent e) {
			drag = false;
		}
	};
	
	MouseMotionListener listenerDrag = new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			if (drag && selezionato != null && selezionato.isTrascinabile()) {
				double x = startX + e.getX() - mouseX;
				double y = startY + e.getY() - mouseY;
				if (x - StatoGUI.RAGGIO < 0) {
					x = StatoGUI.RAGGIO;
				}
				if (y - StatoGUI.RAGGIO < 0) {
					y = StatoGUI.RAGGIO;
				}
				selezionato.setPosizione(new Point2D(x,y));
				setDimensione();
				repaint();
			} else {
				
			}
		}
	};
	
	public void setDimensione() {
		int x = 0, y = 0;
		for (StatoGUI stato : registroStati) {
			if (stato.getPosizione().x() + StatoGUI.RAGGIO > x) {
				x = (int) (stato.getPosizione().x() + StatoGUI.RAGGIO);
			}
			if (stato.getPosizione().y() + StatoGUI.RAGGIO > y) {
				y = (int) (stato.getPosizione().y() + StatoGUI.RAGGIO);
			}
		}
		setPreferredSize(new Dimension(100 + (int) Math.max(x, mediator.getDimensioneGrafica().getWidth()), 100 + (int) Math.max(y, mediator.getDimensioneGrafica().getHeight())));
		revalidate();
	}
	
	MouseMotionListener listenerTransizioneDrag = new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			if (transizione) {
				X2 = e.getX();
				Y2 = e.getY();
				repaint();
			}
		}
	};
	
	public void setAutoma(Automa automa) {
		registroStati.clear();
		registroTransizioni.clear();
		if (automa == null) {
			return;
		}
		AutomaMinimoImpl automaMinimo = new AutomaMinimoImpl(automa);
		Insieme<Stato> statiEquivalenti = automaMinimo.statiEquivalenti();
		for (Set<Stato> stati : statiEquivalenti.insiemi()) {
			Stato stato = stati.iterator().next();
			for (StatoGUI statoGUI : mediator.pannelloGrafica.registroStati) {
				if (statoGUI.getNome().equals(stato.getNome())) {
					registroStati.aggiungi(new StatoGUI(statoGUI.getNome(), statoGUI.getPosizione().x(), statoGUI.getPosizione().y()));
					break;
				}
			}
		}
		for (TransizioneGUI transizioneGUI : mediator.pannelloGrafica.registroTransizioni) {
			double arco = transizioneGUI.getArco();
			String ingresso = transizioneGUI.getIngresso();
			String uscita = transizioneGUI.getUscita();
			StatoGUI statoGUIIniziale = null, statoGUIFinale = null;
			Stato statoIniziale = statiEquivalenti.insiemeDiAppartenenza(new Stato(transizioneGUI.getStatoUscente().getNome())).iterator().next();
			Stato statoFinale = statiEquivalenti.insiemeDiAppartenenza(new Stato(transizioneGUI.getStatoEntrante().getNome())).iterator().next();
			for (StatoGUI statoGUI : registroStati) {
				if (statoGUI.getNome().equals(statoIniziale.getNome())) {
					statoGUIIniziale = statoGUI;
				}
				if (statoGUI.getNome().equals(statoFinale.getNome())) {
					statoGUIFinale = statoGUI;
				}
			}
			TransizioneGUI transizione = new TransizioneGUI(statoGUIIniziale, statoGUIFinale);
			transizione.setIngresso(ingresso);
			transizione.setUscita(uscita);
			transizione.setArco(arco);
			boolean esistente = false;
			for (TransizioneGUI transizioneGUI2 : registroTransizioni) {
				if (transizioneGUI2.getStatoUscente().equals(statoGUIIniziale) && ingresso.equals(transizioneGUI2.getIngresso())) {
					esistente = true;
					break;
				}
			}
			if (!esistente) {
				registroTransizioni.aggiungi(transizione);
			}
		}
		setDimensione();
	}
	
}
