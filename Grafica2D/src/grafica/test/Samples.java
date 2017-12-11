package grafica.test;

import grafica.objects.animatedObject.Animation;
import grafica.objects.animatedObject.AnimationLL;
import grafica.objects.animatedObject.AnimationSet;
import grafica.objects.animatedObject.AnimationSetTM;
import grafica.objects.animatedObject.ImageAnimatedObject;
import grafica.objects.image.Frame;

public class Samples {
	
	private Samples() {}
	
	public static ImageAnimatedObject tank(double x, double y, double animationSpeed) {
			Frame f1 = new Frame("media//sprites//tank move 1.png", 1, 0);
			Frame f2 = new Frame("media//sprites//tank move 2.png", 0, 0);
			Frame f3 = new Frame("media//sprites//tank move 3.png", 0, 0);
			Frame f4 = new Frame("media//sprites//tank move 4.png", 0, 0);
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
	}

}
