package grafica.oggetti.animatedObject.animationHandler;

import java.util.HashMap;
import java.util.TreeSet;

import grafica.oggetti.animatedObject.IAObjects;
import grafica.oggetti.animatedObject.ImageAnimatedObject;
import util.ObjectUtil;

public class AnimationHandlerImpl implements AnimationHandler {
	
	HashMap<ImageAnimatedObject, FrameNode> animationMap;
	
	public AnimationHandlerImpl() {
		animationMap = new HashMap<>();
		engine.start();
	}

	@Override
	public boolean addAnimatedObject(ImageAnimatedObject imageAnimatedObject) {
		ObjectUtil.checkNull(imageAnimatedObject);
		if (animationMap.containsKey(imageAnimatedObject)) {
			return false;
		}
		long currTime = System.currentTimeMillis();
		long timestamp = (long) (1000 / imageAnimatedObject.getCurrentSpeed());
		FrameTimestamp frameTimestamp = new FrameTimestamp(currTime + timestamp);
		FrameNode frameNode = new FrameNode(frameTimestamp, imageAnimatedObject);
		animationMap.put(imageAnimatedObject, frameNode);
		return true;
	}
	
	@Override
	public boolean removeAnimatedObject(ImageAnimatedObject imageAnimatedObject) {
		ObjectUtil.checkNull(imageAnimatedObject);
		return animationMap.remove(imageAnimatedObject) != null;
	}
	
	Thread engine = new Thread(new Runnable() {
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (true) {
				while (AnimationHandlerImpl.this.animationMap.isEmpty()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
				TreeSet<FrameNode> nodes = new TreeSet<>(animationMap.values());
				FrameNode nextNode = nodes.first();
				long currTime = System.currentTimeMillis();
				long nextTimeStamp = nextNode.frameTimestamp.timestamp;
				if (currTime < nextTimeStamp) {
					long delta = nextTimeStamp - currTime;
					try {
						Thread.sleep(delta);
					} catch (InterruptedException e) {
					}
				}
				IAObjects.nextFrame(nextNode.imageAnimatedObject);
				nextNode.frameTimestamp.timestamp = currTime + (long) (1000 / nextNode.imageAnimatedObject.getCurrentSpeed());
			}
		}
	});
		
}
	
class FrameTimestamp implements Comparable<FrameTimestamp>{
	
	long timestamp;
	long frameDelay;
	
	FrameTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int compareTo(FrameTimestamp frameTimestamp) {
		return ((Long) timestamp).compareTo(((Long) frameTimestamp.timestamp));
	}
	
}

class FrameNode implements Comparable<FrameNode>{
	
	final FrameTimestamp frameTimestamp;
	final ImageAnimatedObject imageAnimatedObject;
	
	public FrameNode(FrameTimestamp frameTimestamp, ImageAnimatedObject imageAnimatedObject) {
		ObjectUtil.checkNull(frameTimestamp);
		ObjectUtil.checkNull(imageAnimatedObject);
		this.frameTimestamp = frameTimestamp;
		this.imageAnimatedObject = imageAnimatedObject;
	}
	
	@Override
	public int compareTo(FrameNode frameNode) {
		return frameTimestamp.compareTo(frameNode.frameTimestamp);
	}

}