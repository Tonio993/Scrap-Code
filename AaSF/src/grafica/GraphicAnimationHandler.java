package grafica;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import grafica.oggetti.animatedObject.IAObjects;
import grafica.oggetti.animatedObject.ImageAnimatedObject;

public class GraphicAnimationHandler {
	
	Semaphore mutex;
	Semaphore isNotEmpty;
	
	HashMap<ImageAnimatedObject, Node> gaeRefData;
	HashMap<Long, Node> tsRefData;
	TreeMap<Node, Long> nodeData;
	
	Thread frameFlow;
	
	public GraphicAnimationHandler() {
		gaeRefData = new HashMap<>();
		tsRefData = new HashMap<>();
		nodeData = new TreeMap<>();
		
		mutex = new Semaphore(1);
		isNotEmpty = new Semaphore(0);
		
		frameFlow = new Thread(new FrameFlowThread());
		frameFlow.start();
	}
	
	public boolean add(ImageAnimatedObject gae) throws InterruptedException {
		mutex.acquire();
		long runTimestamp = System.currentTimeMillis();
		boolean newElement = !gaeRefData.containsKey(gae);
		if (newElement) {
			if (nodeData.isEmpty()) {
				isNotEmpty.release();
			}
			innerAdd(gae, runTimestamp);
		}
		mutex.release();
		return newElement;
	}
	
	private void innerAdd(ImageAnimatedObject gae, long runTimestamp) {
		Long delay = (long) (1000 / gae.getCurrentSpeed());
		Long timestamp = runTimestamp + delay;
		if (!tsRefData.containsKey(timestamp)) {
			Node node = new Node(timestamp);
			gaeRefData.put(gae, node);
			tsRefData.put(timestamp, node);
			nodeData.put(node, timestamp);
		} 
		tsRefData.get(timestamp).add(gae);
	}
	
	public boolean remove(ImageAnimatedObject gae) throws InterruptedException {
		mutex.acquire();
		boolean existsElement = gaeRefData.containsKey(gae);
		if (existsElement) {
			innerRemove(gae);
		}
		if (nodeData.isEmpty()) {
			isNotEmpty.acquire();
		}
		mutex.release();
		return existsElement;
	}
	
	private void innerRemove(ImageAnimatedObject gae) {
		Node node = gaeRefData.get(gae);
		node.remove(gae);
		if (node.isEmpty()) {
			tsRefData.remove(nodeData.get(node));
			nodeData.remove(node);
		}
		gaeRefData.remove(gae);
	}
	
	public void frameFlow() throws InterruptedException {
		isNotEmpty.acquire();
		mutex.acquire();
		long runTimestamp = System.currentTimeMillis();
		Node node = nodeData.firstKey();
		long delay = nodeData.get(node) - System.currentTimeMillis();
		if (delay <= 0) {
			tsRefData.remove(node.timestamp);
			nodeData.remove(node);
			ArrayList<ImageAnimatedObject> list = new ArrayList<>(node.getGae());
			for (ImageAnimatedObject gae : list) {
				IAObjects.nextFrame(gae);
				innerAdd(gae, runTimestamp);
			}
		}
		node = nodeData.firstKey();
		delay = System.currentTimeMillis() - nodeData.get(node);
		mutex.release();
		isNotEmpty.release();
	}
	
	static class Node implements Comparable<Node>, Iterable<ImageAnimatedObject> {
		HashSet<ImageAnimatedObject> gaeSet;
		Long timestamp;
		public Node(long timestamp) {
			this.timestamp = timestamp;
			gaeSet = new HashSet<>();
		}
		public boolean isEmpty() {
			return gaeSet.isEmpty();
		}
		public int size() {
			return gaeSet.size();
		}
		public boolean add(ImageAnimatedObject gae) {
			return gaeSet.add(gae);
		}
		public boolean remove(ImageAnimatedObject gae) {
			return gaeSet.remove(gae);
		}
		public Set<ImageAnimatedObject> getGae() {
			return gaeSet;
		}
		@Override
		public int compareTo(Node node) {
			return timestamp.compareTo(node.timestamp);
		}
		@Override
		public Iterator<ImageAnimatedObject> iterator() {
			return gaeSet.iterator();
		}
		
	}
	
	class FrameFlowThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					GraphicAnimationHandler.this.frameFlow();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
