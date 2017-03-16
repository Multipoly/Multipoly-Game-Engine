package net.overrtimestudios.core.graphics;

public class Screen extends DisplayObject{

	public DisplayObject render;
	
	public Screen(int width, int height) {
		super(width, height);
		
		render = new DisplayObject(width, height);	
	}
	
	double tickCount = 0;
	
	public void renderScreen() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		
		// TODO Render all graphical stuff here
		
		render.clear();
		
		drawObject(render, 0, 0);
	}
	
}