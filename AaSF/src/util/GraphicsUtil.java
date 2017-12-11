package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsUtil {
	
	private GraphicsUtil() {}
	
	public static Graphics2D getGraphics2D(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		return g2;
	}

}
