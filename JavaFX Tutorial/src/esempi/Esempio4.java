package esempi;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Esempio4 extends Application {
	
	public void start(Stage theStage) {
		theStage.setTitle("Keyboard Example");
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(512 - 64, 256);
		root.getChildren().add(canvas);
		
		ArrayList<String> input = new ArrayList<>();
		
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				
				// add only once... prevent duplicates
				if (!input.contains(code)) {
					input.add(code);
				}
			}
		});
		
		theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				input.remove(code);
			}
		});
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Image left = new Image("media\\left.png");
		Image leftG = new Image("media\\leftG.png");
		
		Image right = new Image("media\\right.png");
		Image rightG = new Image("media\\rightG.png");
		
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// Clear the canvas
				gc.clearRect(0, 0, 512, 512);
				gc.drawImage(input.contains("LEFT")? leftG:left, 64, 64);
				gc.drawImage(input.contains("RIGHT")? rightG:right, 256, 64);
			}
		}.start();
		
		theStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
