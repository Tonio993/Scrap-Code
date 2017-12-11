package automa.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import math.geom2d.Point2D;
import automa.eccezioni.EccezioneAutoma;
import automa.gui.componenteGrafico.ComponenteGrafico;
import automa.gui.componenteGrafico.StatoGUI;
import automa.gui.componenteGrafico.TransizioneGUI;
import automa.util.Util;

public class PannelloGrafica extends JPanel {

	private static final long serialVersionUID = -1802721350447962786L;
	
	private static final Color COLORE_SFONDO = Color.black;
	private static final Color COLORE_LINK = Color.green;
	private static final int TEMPO_DOPPIO_CLICK = 200;
	
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
	private Timer doppioClick;
	private double X1, Y1, X2, Y2;
	
	public PannelloGrafica(MediatoreGUI mediator) throws EccezioneAutoma {
		Util.checkNull(mediator);
		this.mediator = mediator;
		mediator.setPannelloGrafica(this);
		this.registroStati = new RegistroComponentiGraficiLL<>();
		this.registroTransizioni = new RegistroComponentiGraficiLL<>();
		this.doppioClick = new Timer(TEMPO_DOPPIO_CLICK, null);
		this.doppioClick.addActionListener(listenerDoppioClickStop);
		statiEvidenziati = new LinkedList<>();
		transizioniEvidenziate = new LinkedList<>();
		strutturaFinestra();
	}
	
	private void strutturaFinestra() {
		this.setBackground(COLORE_SFONDO);
		this.setPreferredSize(new Dimension(800,600));
		this.setFocusable(true);
		this.addMouseListener(listenerSelezione);
		this.addMouseListener(listenerDoppioClick);
		this.addMouseListener(listenerTransizione);
		this.addMouseMotionListener(listenerDrag);
		this.addMouseMotionListener(listenerTransizioneDrag);
		this.addKeyListener(listenerCancella);
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
	
	ActionListener listenerDoppioClickStop = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			doppioClick.stop();
		}
	};
	
	MouseListener listenerDoppioClick = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			if (!doppioClick.isRunning()) {
				doppioClick.start();
			} else {
				doppioClick.stop();
				for (ComponenteGrafico stato : registroStati) {
					if (stato.getSpazio().isInside(new Point2D(e.getX(), e.getY()))) {
						return;
					}
				}
				aggiungiStato(new StatoGUI("Stato " + contatoreStati++, e.getX(), e.getY()));
			}
		}
	};

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
	
	MouseListener listenerTransizione = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			requestFocus();
			if (e.getButton() != MouseEvent.BUTTON3) {
				return;
			}
			X1 = X2 = e.getX();
			Y1 = Y2 = e.getY();
			transizione = true;
		}
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() != MouseEvent.BUTTON3) {
				return;
			}
			Point2D p1 = new Point2D(X1, Y1);
			Point2D p2 = new Point2D(X2, Y2);
			StatoGUI stato1 = null;
			StatoGUI stato2 = null;
			for (ComponenteGrafico componente : registroStati) {
				if (componente.getSpazio().isInside(p1)) {
					stato1 = (StatoGUI) componente;
				}
			}
			for (ComponenteGrafico componente : registroStati) {
				if (componente.getSpazio().isInside(p2)) {
					stato2 = (StatoGUI) componente;
				}
			}
			if (stato1 != null && stato2 != null) {
				aggiungiTransizione(stato1, stato2);
			}
			transizione = false;
			repaint();
		}
	};
	
	MouseMotionListener listenerTransizioneDrag = new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			if (transizione) {
				X2 = e.getX();
				Y2 = e.getY();
				repaint();
			}
		}
	};
	
	KeyListener listenerCancella = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE && selezionato != null) {
				eliminaStato(selezionato);
				selezionato = null;
				mediator.setStato(null);
			}
		}
	};
	
	public void aggiungiStato(StatoGUI stato) {
		Util.checkNull(stato);
		registroStati.aggiungi(stato);
		mediator.setAutoma(null);
		setDimensione();
	}
	
	public void eliminaStato(StatoGUI stato) {
		Util.checkNull(stato);
		registroStati.rimuovi(stato);
		LinkedList<TransizioneGUI> lista = new LinkedList<>();
		for (TransizioneGUI transizione : registroTransizioni) {
			lista.add(transizione);
		}
		for (TransizioneGUI transizione : lista) {
			if (transizione.getStatoEntrante().equals(stato) || transizione.getStatoUscente().equals(stato)) {
				eliminaTransizione(transizione);
			}
		}
		mediator.setAutoma(null);
		setDimensione();
		repaint();
	}
	
	public void aggiungiTransizione(StatoGUI stato1, StatoGUI stato2) {
		registroTransizioni.aggiungi(new TransizioneGUI(stato1, stato2));
		mediator.pannelloProprieta.aggiornaTransizioni(selezionato);
		setDimensione();
		mediator.setAutoma(null);
	}

	public void eliminaTransizione(TransizioneGUI transizione) {
		Util.checkNull(transizione);
		registroTransizioni.rimuovi(transizione);
		mediator.setAutoma(null);
		repaint();
	}
	
	public void setStatoIniziale(StatoGUI stato) {
		Util.checkNull(stato);
		if (!registroStati.esiste(stato)) {
			throw new IllegalArgumentException();
		}
		if (statoIniziale != null) {
			statoIniziale.setStatoIniziale(false);
		}
		this.statoIniziale = stato;
		stato.setStatoIniziale(true);
	}
	
}
