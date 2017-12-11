package grafica.oggetti.animatedObject;

public class IAObjects {
	
	private IAObjects() {}
	
	public static void nextFrame(ImageAnimatedObject IAObject) {
		int frame = IAObject.getCurrentFrame();
		int size = IAObject.currentAnimation().size();
		IAObject.setCurrentFrame(frame == size - 1 ? 0 : frame + 1);
	}
	
	public static void previousFrame(ImageAnimatedObject IAObject) {
		int frame = IAObject.getCurrentFrame();
		int size = IAObject.currentAnimation().size();
		IAObject.setCurrentFrame(frame == 0 ? size - 1 : frame - 1);
	}

}
