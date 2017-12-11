package grafica.board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import grafica.data.draw.DrawData;
import grafica.data.draw.DrawDataAL;
import grafica.oggetti.DrawObject;
import util.GraphicsUtil;

public class Board extends JPanel {

	private static final long serialVersionUID = -5458632356620110085L;
	
	public DrawData drawData;
	
	public Board() {
		drawData = new DrawDataAL();
		start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = GraphicsUtil.getGraphics2D(g);
		ArrayList<DrawObject> list = new ArrayList<>(drawData.getDrawData());
		for (DrawObject drawObject : list) {
			drawObject.draw(g2);
		}
	}
	
	//TODO Temporaneo: frame rate
	
	private void start() {
		new Thread(new Runnable() {
			public void run() {
				try {
				while(true) {
					Board.this.repaint();
						Thread.sleep(1000/50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
