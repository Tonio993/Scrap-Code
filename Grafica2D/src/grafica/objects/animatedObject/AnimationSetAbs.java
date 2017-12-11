package grafica.objects.animatedObject;

import grafica.util.Util;

public abstract class AnimationSetAbs implements AnimationSet {
	
	protected String defaultAnimation;

	@Override
	@SuppressWarnings("unused")
	public int size() {
		int n = 0;
		for (String animation : this) {
			n++;
		}
		return n;
	}

	@Override
	public boolean isEmpty() {
		return !iterator().hasNext();
	}
	
	@Override
	public boolean containsAnimation(String name) {
		for (String animation : this) {
			if (animation.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getDefaultAnimation() {
		return defaultAnimation;
	}
	
	@Override
	public boolean setDefaultAnimation(String name) {
		Util.checkNull(name);
		if (getAnimation(name) != null) {
			defaultAnimation = name;
			return true;
		}
		return false;
	}

	@Override
	public final boolean addAnimation(String name, Animation animation) {
		Util.checkNull(name);
		Util.checkNull(animation);
		if (addAnimationImpl(name, animation)) {
			if (defaultAnimation == null) {
			defaultAnimation = name;
			}
			return true;
		}
		return false;
	}
	
	protected abstract boolean addAnimationImpl(String name, Animation animation);

	@Override
	public final boolean removeAnimation(String name) {
		Util.checkNull(name);
		if (removeAnimationImpl(name)) {
			if (name.equals(defaultAnimation)) {
				if (iterator().hasNext()) {
					defaultAnimation = iterator().next();
				} else {
					defaultAnimation = null;
				}
			}
			return true;
		}
		return false;
	}
	
	protected abstract boolean removeAnimationImpl(String name);

}