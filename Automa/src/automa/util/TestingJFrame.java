package automa.util;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

public class TestingJFrame extends JFrame {

	private static final long serialVersionUID = -8395567076006137556L;
	
	public TestingJFrame(Component component) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(component, BorderLayout.CENTER);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
