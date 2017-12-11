package grafica.objects.animatedObject;

import grafica.objects.image.Frame;

public interface Animation {
	
	public int size();
	
	public boolean isEmpty();
	
	public Frame getFrame(int index);
	
	public void addFrame(Frame frame);
	
	public void addFrame(int index, Frame frame);
	
	public void removeFrame(int index);
	
	public void removeFrame(Frame frame);
	
	public double getSpeed();
	
	public void setSpeed(double speed);

}
