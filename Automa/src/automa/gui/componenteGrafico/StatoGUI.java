package automa.gui.componenteGrafico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.domain.Boundary2D;
import automa.util.Util;

public class StatoGUI extends ComponenteGraficoAbs {
	
	private static final long serialVersionUID = -8025070781872918755L;
	private static final Color COLORE_INTERNO = Color.cyan;
	private static final Color COLORE_INTERNO_SELEZIONATO = Color.blue;
	private static final Color COLORE_INTERNO_EVIDENZIATO = Color.red;
	private static final Color COLORE_BORDO = Color.blue;
	private static final Color COLORE_BORDO_SELEZIONATO = Color.cyan;
	private static final Color COLORE_BORDO_EVIDENZIATO = Color.pink;
	private static final Color COLORE_TESTO = Color.blue;
	private static final Color COLORE_TESTO_SELEZIONATO = Color.cyan;
	private static final Color COLORE_TESTO_EVIDENZIATO = Color.pink;

	public static final double RAGGIO = 40;
	
	private static final int FONT_SIZE = 16;
	
	private String nome;
	private double x, y, r;
	private boolean statoIniziale;
	private boolean evidenziato;
	
	public StatoGUI(String nome) {
		this(nome, 0, 0);
	}
	
	public StatoGUI(String nome, double x, double y) {
		super(true);
		setNome(nome);
		setPosizione(new Point2D(x, y));
		this.evidenziato = false;
		this.r = RAGGIO;
	}

	@Override
	public void draw(Graphics2D g2) {
		Ellipse2D.Double ellipse = new Ellipse2D.Double(x - r, y - r, r * 2, r * 2);
		g2.setColor(isEvidenziato() ? COLORE_INTERNO_EVIDENZIATO : isSelezionato() ? COLORE_INTERNO_SELEZIONATO : COLORE_INTERNO);
		g2.fill(ellipse);
		g2.setColor(isEvidenziato() ? COLORE_BORDO_EVIDENZIATO : isSelezionato() ? COLORE_BORDO_SELEZIONATO : COLORE_BORDO);
		g2.setStroke(new BasicStroke(2));
		g2.draw(ellipse);
		g2.setColor(isEvidenziato() ? COLORE_TESTO_EVIDENZIATO : isSelezionato() ? COLORE_TESTO_SELEZIONATO : COLORE_TESTO);
		g2.setFont(new Font("Default",Font.BOLD,FONT_SIZE));
		String stato = this.nome;
		FontMetrics fm = g2.getFontMetrics();
		double x = this.x - fm.stringWidth(stato) / 2;
		double y = this.y + fm.getAscent() / 3;
		g2.drawString(stato, (float) x, (float) y);
		if (statoIniziale) {
			g2.drawString("S", (float) (this.x - RAGGIO), (float) (this.y - RAGGIO));
		}
	}

	@Override
	public Boundary2D getSpazio() {
		return new Circle2D(x, y, r);
	}
	
	@Override
	public Point2D getPosizione() {
		return new Point2D(x,y);
	}
	
	public double getRaggio() {
		return r;
	}
	
	@Override
	public void setPosizione(Point2D posizione) {
		Util.checkNull(posizione);
		this.x = posizione.x();
		this.y = posizione.y();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		Util.checkNull(nome);
		this.nome = nome;
	}
	
	public String toString() {
		return nome;
	}

	public boolean isStatoIniziale() {
		return statoIniziale;
	}

	public void setStatoIniziale(boolean statoIniziale) {
		this.statoIniziale = statoIniziale;
	}

	public boolean isEvidenziato() {
		return evidenziato;
	}

	public void setEvidenziato(boolean evidenziato) {
		this.evidenziato = evidenziato;
	}
	
	
	
}
