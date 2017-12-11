package grafica.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import grafica.oggetti.animatedObject.Animation;
import grafica.oggetti.animatedObject.AnimationLL;
import grafica.oggetti.animatedObject.AnimationSet;
import grafica.oggetti.animatedObject.AnimationSetTM;
import grafica.oggetti.animatedObject.ImageAnimatedObject;
import grafica.oggetti.image.Frame;

public class Samples {
	
	private Samples() {}
	
	public static ImageAnimatedObject tank(double x, double y, double animationSpeed) {
		try {
			Frame f1 = new Frame(ImageIO.read(new File("media//sprites//tank move 1.png")), 1, 0);
			Frame f2 = new Frame(ImageIO.read(new File("media//sprites//tank move 2.png")), 0, 0);
			Frame f3 = new Frame(ImageIO.read(new File("media//sprites//tank move 3.png")), 0, 0);
			Frame f4 = new Frame(ImageIO.read(new File("media//sprites//tank move 4.png")), 0, 0);
			Animation a1 = new AnimationLL();
			a1.addFrame(f1);
			a1.addFrame(f2);
			a1.addFrame(f3);
			a1.addFrame(f4);
			a1.setSpeed(animationSpeed);
			AnimationSet as1 = new AnimationSetTM();
			as1.addAnimation("Tank move 1", a1);
			ImageAnimatedObject tank = new ImageAnimatedObject(as1, x, y);
			return tank;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
