package grafica.objects.image;

import java.util.Objects;

import javafx.scene.image.Image;

public class Frame {
	
	private Image image;
	private double centerX;
	private double centerY;
	
	public Frame(Image image) {
		this(image, 0, 0);
	}
	
	public Frame(String path) {
		this(new Image(path));
	}
	
	public Frame(Image image, double centerX, double centerY) {
		Objects.requireNonNull(image);
		this.image = image;
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public Frame(String path, double centerX, double centerY) {
		this(new Image(path), centerX, centerY);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
}
