package grafica.objects.animatedObject;

public interface AnimationSet extends Iterable<String>{
	
	public int size();

	public boolean isEmpty();
	
	public boolean containsAnimation(String name);
	
	public String getDefaultAnimation();
	
	public boolean setDefaultAnimation(String name);
	
	public Animation getAnimation(String name);
	
	public boolean addAnimation(String name, Animation animation);
	
	public boolean removeAnimation(String name);
	
}
