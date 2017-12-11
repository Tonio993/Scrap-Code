package grafica.data.draw;

import java.util.ArrayList;
import java.util.Iterator;

import grafica.objects.DrawObject;
import grafica.objects.animatedObject.ImageAnimatedObject;
import grafica.util.Util;

public class DrawDataAL extends DrawDataAbs {
	
	ArrayList<DrawObject> drawData;
	
	public DrawDataAL() {
		drawData = new ArrayList<>();
	}
	
	@Override
	public int size() {
		return drawData.size();
	}
	
	@Override
	public boolean isEmpty() {
		return drawData.isEmpty();
	}
	
	@Override
	public boolean containsDrawObject(DrawObject drawObject) {
		Util.checkNull(drawObject);
		return drawData.contains(drawObject);
	}

	@Override
	public boolean addDrawObject(DrawObject drawObject) throws InterruptedException {
		Util.checkNull(drawObject);
		if (drawData.add(drawObject)) {
			if (drawObject instanceof ImageAnimatedObject) {
				animationHandler.add((ImageAnimatedObject) drawObject);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeDrawObject(DrawObject drawObject) {
		Util.checkNull(drawObject);
		return drawData.remove(drawObject);
	}

	@Override
	public Iterator<DrawObject> iterator() {
		return drawData.iterator();
	}

}
