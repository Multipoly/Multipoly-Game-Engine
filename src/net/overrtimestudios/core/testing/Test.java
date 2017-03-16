package net.overrtimestudios.core.testing;

import static net.overrtimestudios.core.graphics.TextureRequest.registerTexture;
import static net.overrtimestudios.core.graphics.TextureRequest.requestTexture;

import net.overrtimestudios.Engine;
import net.overrtimestudios.core.Mouse;
import net.overrtimestudios.core.Parent;
import net.overrtimestudios.core.graphics.Light;
import net.overrtimestudios.core.graphics.Texture;
import net.overrtimestudios.core.utils.Window;

public class Test extends Parent{
	private Light light = new Light(50, 0xffffffff);
	private Light l = new Light(50, 0xff00ffff);
	
	public Test(int width, int height) {
		super(width, height);
	}
	
	public void init() {
		Engine.Render().enableLighting(true);
		
		Texture t1 = new Texture("bg", 0, false);
		registerTexture(t1);
		
		Texture t = new Texture("test", 1, false);
		t.setLightBlock(Light.TOTAL);
		registerTexture(t);
	}
	
	int time = 0;
	
	public void tick() {
		time++;

	}


	public void render() {

		Engine.Render().setzDepth(0);

		
		Engine.Render().drawLight(light, Mouse.mx, Mouse.my);
		Engine.Render().drawLight(l, time, 30);
		
		Engine.Render().drawTexture(requestTexture(0), 0, 0);
		Engine.Render().drawTexture(requestTexture(1), 200, 200);			

	}
	
	public void dispose() {
		
	}
	
	public static void main(String[] args) {
		Engine e = new Engine(new Test(800, 600));
		Window w = new Window("Test", 640, 480);
		w.initFrame(e);
		e.start();
	}
	
}