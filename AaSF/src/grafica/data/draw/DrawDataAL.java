package grafica.data.draw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import grafica.oggetti.DrawObject;
import grafica.oggetti.animatedObject.ImageAnimatedObject;
import util.ObjectUtil;

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
		ObjectUtil.checkNull(drawObject);
		return drawData.contains(drawObject);
	}

	@Override
	public boolean addDrawObject(DrawObject drawObject) throws InterruptedException {
		ObjectUtil.checkNull(drawObject);
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
		ObjectUtil.checkNull(drawObject);
		return drawData.remove(drawObject);
	}

	@Override
	public Iterator<DrawObject> iterator() {
		return drawData.iterator();
	}
	
	public Collection<DrawObject> getDrawData() {
		return drawData;
	}

}
