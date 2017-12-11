package automa.gui.componenteGrafico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;

import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.domain.Boundary2D;
import math.geom2d.line.Line2D;
import automa.util.Util;

public class TransizioneGUI extends ComponenteGraficoAbs {
	
	private static final long serialVersionUID = 2119047979460034147L;
	
	private static final Color COLORE_FRECCIA = Color.green;
	private static final Color COLORE_FRECCIA_EVIDENZIATO = Color.yellow;
	private static final Color COLORE_TESTO = Color.blue;
	private static final Color COLORE_TESTO_EVIDENZIATO = Color.pink;
	private static final int FONT_SIZE = 16;
	
	private StatoGUI statoUscente;
	private StatoGUI statoEntrante;
	
	private String ingresso;
	private String uscita;
	
	private boolean evidenziato;
	
	private double arco = 0;

	public TransizioneGUI(StatoGUI statoUscente, StatoGUI statoEntrante) {
		super(true);
		Util.checkNull(statoUscente, statoEntrante);
		this.statoUscente = statoUscente;
		this.statoEntrante = statoEntrante;
		this.evidenziato = false;
		ingresso = uscita = "";
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		Path2D punta = new Path2D.Double();
		punta.moveTo(0, 0);
		punta.lineTo(-5, 15);
		punta.lineTo(5, 15);
		punta.closePath();
		g2.setFont(new Font("Default",Font.BOLD, FONT_SIZE));
		String segnale = (ingresso.equals("") || uscita.equals("")) ? "?" : ingresso + "/" + uscita;
		if (statoUscente == statoEntrante) {
			double x1 = statoUscente.getPosizione().x() + StatoGUI.RAGGIO * Math.cos(Math.PI * 5 / 6 + arco * Math.PI / 25);
			double y1 = statoUscente.getPosizione().y() + StatoGUI.RAGGIO * Math.sin(Math.PI * 5 / 6 + arco * Math.PI / 25);
			double x2 = statoUscente.getPosizione().x() + StatoGUI.RAGGIO * 2 * Math.cos(Math.PI * 8 / 9 + arco * Math.PI / 25);
			double y2 = statoUscente.getPosizione().y() + StatoGUI.RAGGIO * 2 * Math.sin(Math.PI * 8 / 9 + arco * Math.PI / 25);
			double x3 = statoUscente.getPosizione().x() + StatoGUI.RAGGIO * 2 * Math.cos(Math.PI * 10 / 9 + arco * Math.PI / 25);
			double y3 = statoUscente.getPosizione().y() + StatoGUI.RAGGIO * 2 * Math.sin(Math.PI * 10 / 9 + arco * Math.PI / 25);
			double x4 = statoUscente.getPosizione().x() + StatoGUI.RAGGIO * Math.cos(Math.PI * 7 / 6 + arco * Math.PI / 25);
			double y4 = statoUscente.getPosizione().y() + StatoGUI.RAGGIO * Math.sin(Math.PI * 7 / 6 + arco * Math.PI / 25);
			double xs = statoUscente.getPosizione().x() + StatoGUI.RAGGIO * 2 * Math.cos(Math.PI * 10 / 9 + arco * Math.PI / 25);
			double ys = statoUscente.getPosizione().y() + StatoGUI.RAGGIO * 2 * Math.sin(Math.PI * 10 / 9 + arco * Math.PI / 25);
			CubicCurve2D curva = new CubicCurve2D.Double(x1, y1, x2, y2, x3, y3, x4, y4);
			punta.transform(AffineTransform.getTranslateInstance(x4, y4));
			punta.transform(AffineTransform.getRotateInstance(Math.PI / 2 + arco * Math.PI / 25, x4, y4));
			Point2D puntoS = new Point2D(xs, ys);
			g2.setColor(isEvidenziato() ? COLORE_FRECCIA_EVIDENZIATO : COLORE_FRECCIA);
			g2.draw(curva);
			g2.fill(punta);
			g2.setColor(isEvidenziato() ? COLORE_TESTO_EVIDENZIATO : COLORE_TESTO);
			g2.drawString(segnale, (float) puntoS.x(), (float) puntoS.y()); 
		} else {
			Point2D pos1 = statoUscente.getPosizione();
			Point2D pos2 = statoEntrante.getPosizione();
			Line2D linea = new Line2D(pos1, pos2);
			if (pos1.distance(pos2) < StatoGUI.RAGGIO * 2) {
				return;
			}
			Point2D punto1 = statoUscente.getSpazio().intersections(linea).iterator().next();
			Point2D punto2 = statoEntrante.getSpazio().intersections(linea).iterator().next();
			double angolo1 = Math.atan2(punto2.x() - punto1.x(), punto1.y() - punto2.y());
			Point2D puntoC = new Point2D((punto1.x() + punto2.x()) / 2 + 4 * arco * Math.cos(angolo1) , (punto1.y() + punto2.y()) / 2 + 4 * arco * Math.sin(angolo1));
			QuadCurve2D curva = new QuadCurve2D.Double(punto1.x(), punto1.y(), puntoC.x(), puntoC.y(), punto2.x(), punto2.y());
			double angolo2 = Math.atan2(punto2.x() - puntoC.x(), puntoC.y() - punto2.y());
			punta.transform(AffineTransform.getTranslateInstance(punto2.x(), punto2.y()));
			punta.transform(AffineTransform.getRotateInstance(angolo2, punto2.x(), punto2.y()));
			g2.setColor(isEvidenziato() ? COLORE_FRECCIA_EVIDENZIATO : COLORE_FRECCIA);
			g2.draw(curva);
			g2.fill(punta);
			double x = ((punto1.x() + punto2.x()) / 2) + 10 + (curva.getFlatness() / 2) * Math.cos(angolo1) * Math.signum(arco);
			double y = ((punto1.y() + punto2.y()) / 2) + 10 + (curva.getFlatness() / 2) * Math.sin(angolo1) * Math.signum(arco);
			g2.setColor(isEvidenziato() ? COLORE_TESTO_EVIDENZIATO : COLORE_TESTO);
			g2.drawString(segnale, (float) x, (float) y);
		}
	}

	@Override
	public Boundary2D getSpazio() {
		return new Circle2D(0,0,0);
	}

	@Override
	public Point2D getPosizione() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPosizione(Point2D posizione) {
		throw new UnsupportedOperationException();

	}
	
	public double getArco() {
		return arco;
	}
	
	public void setArco(double arco) {
		this.arco = arco;
	}

	public StatoGUI getStatoUscente() {
		return statoUscente;
	}

	public void setStatoUscente(StatoGUI statoUscente) {
		this.statoUscente = statoUscente;
	}

	public StatoGUI getStatoEntrante() {
		return statoEntrante;
	}

	public void setStatoEntrante(StatoGUI statoEntrante) {
		this.statoEntrante = statoEntrante;
	}

	public String getIngresso() {
		return ingresso;
	}
	
	public void setIngresso(String ingresso) {
		Util.checkNull(ingresso);
		if (!ingresso.matches("[01]*")) {
			throw new IllegalArgumentException();
		}
		this.ingresso = ingresso;
	}
	
	public String getUscita() {
		return uscita;
	}
	
	public void setUscita(String uscita) {
		Util.checkNull(uscita);
		if (!uscita.matches("[01]*")) {
			throw new IllegalArgumentException();
		}
		this.uscita = uscita;
	}

	public boolean isEvidenziato() {
		return evidenziato;
	}

	public void setEvidenziati(boolean evidenziato) {
		this.evidenziato = evidenziato;
	}
	
	

}
