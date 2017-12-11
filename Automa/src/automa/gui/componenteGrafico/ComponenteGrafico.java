package automa.gui.componenteGrafico;

import java.awt.Graphics2D;
import java.io.Serializable;

import math.geom2d.Point2D;
import math.geom2d.domain.Boundary2D;

public interface ComponenteGrafico extends Serializable {
	
	public void draw(Graphics2D g2);
	
	public Boundary2D getSpazio();
	
	public Point2D getPosizione();
	
	public void setPosizione(Point2D posizione);
	
	public boolean isTrascinabile();
	
	public boolean isSelezionato();
	
	public void setSelezionato(boolean b);

}
