package esempi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Esempio2A extends Application {
	
	public void start(Stage theStage) {
		theStage.setTitle("Timeline example");
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(512, 512);
		root.getChildren().add(canvas);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Image earth = new Image("media\\earth.png");
		Image sun = new Image("media\\sun.png");
		Image space = new Image("media\\space.png");
		
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		final long timeStart = System.currentTimeMillis();
		
		KeyFrame kfSpace = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				gc.drawImage(space, 0, 0);
			}
		});
		
		KeyFrame kfEarth = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				double t = (System.currentTimeMillis() - timeStart) / 1000.0;
				double x = 232 + 128 * Math.cos(t);
				double y = 232 + 128 * Math.sin(t);
				gc.drawImage(earth, x, y);
			}
		});
		
		KeyFrame kfSun = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				gc.drawImage(sun, 196, 196);
			}
		});
		
		gameLoop.getKeyFrames().add(kfSpace);
		gameLoop.getKeyFrames().add(kfEarth);
		gameLoop.getKeyFrames().add(kfSun);
		gameLoop.play();
		
		theStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
