package grafica.objects.animatedObject;

import java.util.Iterator;
import java.util.TreeMap;

import grafica.util.Util;

public class AnimationSetTM extends AnimationSetAbs {
	
	private TreeMap<String, Animation> animationSet;
	
	public AnimationSetTM() {
		this.animationSet = new TreeMap<>();
	}
	
	@Override
	public Animation getAnimation(String name) {
		Util.checkNull(name);
		return animationSet.get(name);
	}
	
	@Override
	public boolean addAnimationImpl(String name, Animation animation) {
		Util.checkNull(name);
		Util.checkNull(animation);
		if (!animationSet.containsKey(name)) {
			animationSet.put(name, animation);
			return true;
		}
		return false;
		
	}
	@Override
	public boolean removeAnimationImpl(String name) {
		Util.checkNull(name);
		return animationSet.remove(name) != null;
	}

	@Override
	public Iterator<String> iterator() {
		return animationSet.keySet().iterator();
	}
	
}
