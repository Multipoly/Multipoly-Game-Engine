package net.overrtimestudios.core.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	
	protected BufferedImage image;
	protected boolean isAlpha;
	protected int id, lightBlock = Light.TOTAL;

	protected int width, height;
	protected int[] pixels;
	
	public Texture(String name, int id, boolean isAlpha) {
		try {image = ImageIO.read(getClass().getResource("/" + name + ".png")); width = image.getWidth(); height = image.getHeight();}catch(IOException ioe){}
	
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		image.flush();
		image = null;
		this.id = id;
		this.isAlpha = isAlpha;
		this.lightBlock = -1;
	}

	public boolean isAlpha() {
		return isAlpha;
	}

	public void setAlpha(boolean isAlpha) {
		this.isAlpha = isAlpha;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}

	public int getLightBlock() {
		return lightBlock;
	}

	public void setLightBlock(int lightBlock) {
		this.lightBlock = lightBlock;
	}
	
}