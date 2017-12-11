package grafica.objects.animatedObject;

import java.util.LinkedList;
import java.util.Objects;

import grafica.objects.image.Frame;

public class AnimationLL implements Animation {

	private final static double DEFAULT_ANIMATION_SPEED = 1;
	
	LinkedList<Frame> animation;
	
	private double speed;
	
	public AnimationLL() {
		animation = new LinkedList<>();
		speed = DEFAULT_ANIMATION_SPEED;
	}

	@Override
	public int size() {
		return animation.size();
	}
	
	@Override
	public boolean isEmpty() {
		return animation.isEmpty();
	}

	@Override
	public Frame getFrame(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return animation.get(index);
	}
	
	@Override
	public void addFrame(Frame frame) {
		addFrame(size(), frame);
	}

	@Override
	public void addFrame(int index, Frame frame) {
		Objects.requireNonNull(frame);
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		animation.add(index, frame);
		
	}

	@Override
	public void removeFrame(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		animation.remove(index);
	}

	@Override
	public void removeFrame(Frame frame) {
		animation.remove(frame);
	}
	
	@Override
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
