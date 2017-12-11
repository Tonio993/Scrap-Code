package grafica.oggetti.animatedObject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import grafica.oggetti.DrawObject;
import grafica.oggetti.image.Frame;
import util.ObjectUtil;

public class ImageAnimatedObject implements DrawObject {
	
	private AnimationSet animationSet;
	private String currentAnimation;
	private int currentFrame;
	private double currentSpeed;

	private double x;
	private double y;
	
	public ImageAnimatedObject(AnimationSet animationSet) {
		this(animationSet, 0, 0);
	}
	
	public ImageAnimatedObject(AnimationSet animationSet, double x, double y) {
		ObjectUtil.checkNull(animationSet);
		if (animationSet.isEmpty()) {
			throw new IllegalArgumentException("AnimationSet must contains at least 1 animation");
		}
		this.animationSet = animationSet;
		this.currentAnimation = animationSet.getDefaultAnimation();
		
		currentFrame = 0;
		currentSpeed = currentAnimation().getSpeed();
		
		this.x = x;
		this.y = y;
	}
	
	public String getCurrentAnimation() {
		return currentAnimation;
	}
	
	public void setCurrentAnimation(String name) {
		ObjectUtil.checkNull(name);
		if (!animationSet.containsAnimation(name)) {
			throw new IllegalArgumentException("Animation doesn't exist in this AnimationSet");
		}
		currentAnimation = name;
		
		setCurrentFrame(0);
		setCurrentSpeed(currentAnimation().getSpeed());
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public int getCurrentAnimationSize() {
		return currentAnimation().size();
	}
	
	public void setCurrentFrame(int index) {
		if (index < 0 || index >= currentAnimation().size()) {
			throw new IndexOutOfBoundsException();
		}
		currentFrame = index;
	}
	
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	public void setCurrentSpeed(double speed) {
		this.currentSpeed = speed;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	// Metodi di comodo
	
	Animation currentAnimation() {
		return animationSet.getAnimation(currentAnimation);
	}
	
	Frame currentFrame() {
		return currentAnimation().getFrame(currentFrame);
	}

	@Override
	public void draw(Graphics2D g2) {
		double drawX = x - currentFrame().getCenterX();
		double drawY = y - currentFrame().getCenterY();
		AffineTransform affineTransform = AffineTransform.getTranslateInstance(drawX, drawY);
		
		g2.drawImage(currentFrame().getImage(), affineTransform, null);
	}
	
}
