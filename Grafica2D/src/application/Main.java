package application;

import grafica.data.draw.DrawData;
import grafica.data.draw.DrawDataAL;
import grafica.objects.DrawObject;
import grafica.test.Samples;
import grafica.util.Util;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private DrawData drawData;
	
	public DrawData getDrawData() {
		return drawData;
	}
	
	public void setDrawData(DrawData drawData) {
		Util.checkNull(drawData);
		this.drawData = drawData;
	}
	
	public void start(Stage theStage) throws Exception {
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		DrawData theDrawData = new DrawDataAL();
		setDrawData(theDrawData);
		
		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				for (DrawObject obj : drawData) {
					obj.draw(gc);
				}
			}
		});
		
		int distX = 16;
		int distY = 18;
		for (int i=0;i<14;i++) {
			drawData.addDrawObject(Samples.tank(i*distX + 60, i*distY + 100, 25));
			drawData.addDrawObject(Samples.tank(i*distX + 100, i*distY + 100, 20));
			drawData.addDrawObject(Samples.tank(i*distX + 140, i*distY + 100, 15));
			drawData.addDrawObject(Samples.tank(i*distX + 180, i*distY + 100, 10));
			drawData.addDrawObject(Samples.tank(i*distX + 220, i*distY + 100, 5));
		}
		
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		theStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
