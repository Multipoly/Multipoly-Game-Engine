package net.overrtimestudios.core;


public abstract class Parent {

	protected int width, height;
	
	public abstract void init();
	public abstract void tick();
	public abstract void render();
	public abstract void dispose();
	
	public Parent(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
