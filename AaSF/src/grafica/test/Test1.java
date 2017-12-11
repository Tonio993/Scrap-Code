package grafica.test;

import javax.swing.JFrame;

import grafica.board.Board;

public class Test1 {
	
	private static final int DELAY = 0;
	
	public static void main(String[] args) throws InterruptedException {
		
		JFrame f = new JFrame();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		f.setLocationRelativeTo(null);
		
		Board b = new Board();
		
		f.setContentPane(b);
		f.setVisible(true);
		
		int distX = 16;
		int distY = 18;
		for (int i=0;i<14;i++) {
			b.drawData.addDrawObject(Samples.tank(i*distX + 60, i*distY + 100, 25));
			Thread.sleep(DELAY);
			b.drawData.addDrawObject(Samples.tank(i*distX + 100, i*distY + 100, 20));
			Thread.sleep(DELAY);
			b.drawData.addDrawObject(Samples.tank(i*distX + 140, i*distY + 100, 15));
			Thread.sleep(DELAY);
			b.drawData.addDrawObject(Samples.tank(i*distX + 180, i*distY + 100, 10));
			Thread.sleep(DELAY);
			b.drawData.addDrawObject(Samples.tank(i*distX + 220, i*distY + 100, 5));
			Thread.sleep(DELAY);
		}
	}

}
