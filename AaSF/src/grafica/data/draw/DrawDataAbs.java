package grafica.data.draw;

import grafica.GraphicAnimationHandler;
import grafica.oggetti.DrawObject;

public abstract class DrawDataAbs implements DrawData {

	GraphicAnimationHandler animationHandler = new GraphicAnimationHandler();	
	
	@Override
	@SuppressWarnings("unused")
	public int size() {
		int n = 0;
		for (DrawObject drawObject : this) {
			n++;
		}
		return n;
	}

	@Override
	public boolean isEmpty() {
		return iterator().hasNext();
	}

	@Override
	public boolean containsDrawObject(DrawObject drawObject) {
		for (DrawObject drawObj : this) {
			if (drawObj.equals(drawObject)) {
				return true;
			}
		}
		return false;
	}

}
