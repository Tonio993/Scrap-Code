package grafica;
import grafica.data.draw.DrawData;
import grafica.oggetti.animatedObject.ImageAnimatedObject;

public class AddElementThread extends ActionThread {
	
	private DrawData drawData;
	private ImageAnimatedObject gae;
	
	public AddElementThread(long callTimestamp, DrawData drawData, ImageAnimatedObject gae) {
		super(callTimestamp);
		this.drawData = drawData;
		this.gae = gae;
	}

	@Override
	public void run() {
		try {
			drawData.addDrawObject(gae);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
