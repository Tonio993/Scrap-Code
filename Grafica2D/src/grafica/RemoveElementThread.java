package grafica;
import grafica.objects.animatedObject.ImageAnimatedObject;

public class RemoveElementThread extends ActionThread {
	
	private GraphicAnimationHandler gah;
	private ImageAnimatedObject gae;
	
	public RemoveElementThread(long callTimestamp, GraphicAnimationHandler gah, ImageAnimatedObject gae) {
		super(callTimestamp);
		this.gah = gah;
		this.gae = gae;
	}

	@Override
	public void run() {
		try {
			gah.remove(gae);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
