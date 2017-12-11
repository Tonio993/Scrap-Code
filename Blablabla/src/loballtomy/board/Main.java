package loballtomy.board;

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

public class Main extends Application {

	@Override
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Loballtomy");
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(800, 600);
		root.getChildren().add(canvas);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Image loballtomy = new Image("media/Loballtomy.png");
		
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kfLoball = new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				gc.drawImage(loballtomy, 400, 300);
			}
		});
		
		gameLoop.getKeyFrames().add(kfLoball);
		gameLoop.play();
		
		theStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
