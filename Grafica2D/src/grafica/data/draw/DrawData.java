package grafica.data.draw;

import grafica.objects.DrawObject;

public interface DrawData extends Iterable<DrawObject>{
	
	public int size();
	
	public boolean isEmpty();
	
	public boolean containsDrawObject(DrawObject drawObject);
	
	public boolean addDrawObject(DrawObject drawObject) throws InterruptedException;
	
	public boolean removeDrawObject(DrawObject drawObject);
	
}
