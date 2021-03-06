package esempi;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import oggetti.Sprite;

public class Esempio6 extends Application {

	protected int BRIEFCASE_SPEED = 50;

	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Collect the Money Bags!");
		
		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);
		
		Canvas canvas = new Canvas(512, 512);
		root.getChildren().add(canvas);
		
		ArrayList<String> input = new ArrayList<>();
		
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
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
		
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		
		gc.setFont(theFont);
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		
		Sprite briefcase = new Sprite();
		briefcase.setImage("media\\briefcase.png");
		briefcase.setPosition(200, 0);
		
		ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();
		
		for (int i=0; i<15; i++) {
			Sprite moneybag = new Sprite();
			moneybag.setImage("media\\moneybag.png");
			double px = 350 * Math.random() + 50;
			double py = 350 * Math.random() + 50;
			moneybag.setPosition(px, py);
			moneybagList.add(moneybag);
		}
		
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		
		IntValue score = new IntValue(0);
		
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				// caluculate time since last update
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
				lastNanoTime.value = currentNanoTime;
				
				// game logic
				briefcase.setVelocity(0, 0);
				if (input.contains("LEFT")) {
					briefcase.addVelocity(-BRIEFCASE_SPEED, 0);
				}
				if (input.contains("RIGHT")) {
					briefcase.addVelocity(BRIEFCASE_SPEED, 0);
				}
				if (input.contains("UP")) {
					briefcase.addVelocity(0, -BRIEFCASE_SPEED);
				}
				if (input.contains("DOWN")) {
					briefcase.addVelocity(0, BRIEFCASE_SPEED);
				}
				briefcase.update(elapsedTime);
				
				// collision detection
				Iterator<Sprite> moneybagIter = moneybagList.iterator();
				while (moneybagIter.hasNext()) {
					Sprite moneybag = moneybagIter.next();
					if (briefcase.intersects(moneybag)) {
						moneybagIter.remove();
						score.value++;
					}
				}
				
				// render
				gc.clearRect(0, 0, 512, 512);
				briefcase.render(gc);
				
				for (Sprite moneybag : moneybagList) {
					moneybag.render(gc);
				}
				
				String pointsText = "Cash: $" + (100 * score.value);
				gc.fillText(pointsText, 360, 36);
				gc.fillText(pointsText, 360, 36);
			}
		}.start();
		
		theStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static class IntValue {
		public int value;
		public IntValue(int value) {
			this.value = value;
		}
	}
	
	private static class LongValue {
		public long value;
		public LongValue(long value) {
			this.value = value;
		}
	}

}
