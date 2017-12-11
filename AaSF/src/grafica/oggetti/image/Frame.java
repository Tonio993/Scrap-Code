package grafica.oggetti.image;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Frame {
	
	private BufferedImage image;
	private double centerX;
	private double centerY;
	
	public Frame(BufferedImage image) {
		this(image, 0, 0);
	}
	
	public Frame(BufferedImage image, double centerX, double centerY) {
		Objects.requireNonNull(image);
		this.image = image;
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
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
