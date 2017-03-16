package net.overrtimestudios;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import net.overrtimestudios.core.Keyboard;
import net.overrtimestudios.core.Mouse;
import net.overrtimestudios.core.Parent;
import net.overrtimestudios.core.graphics.DisplayObject;
import net.overrtimestudios.core.graphics.Screen;
import net.overrtimestudios.core.graphics.TextureRequest;

public class Engine extends Canvas implements Runnable{

	public static final long serialVersionUID = 1L;
	public boolean running = false;
	public Thread thread;
	
	private static double cap = 30D;
	
	
	protected int width = 640, height = 480;
	protected BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	protected int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public static Screen Render;
	
	protected Parent parent;
	
	public Engine(Parent parent) {
		this.parent = parent;
		Dimension d = new Dimension(parent.getWidth(), parent.getHeight());
		
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		setFocusable(true);
		addKeyListener(new Keyboard());
		addMouseMotionListener(new Mouse());
	}
	
	public void init() {
		Render = new Screen(width, height);
		
		parent.init();
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		
		thread = new Thread(this);
		thread.run();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			dispose();
			thread.join();
		}catch(InterruptedException ie) {}
	}
	
	public void dispose() {
		TextureRequest.disposeTextures();
		parent.dispose();
	}
	
	public void tick() {
		parent.tick();
		
		if(Keyboard.isKey(KeyEvent.VK_ESCAPE)) 
			System.exit(0);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}

		Render.clear();
		Render.renderScreen();
		parent.render();
		Render.process();
		
		for(int i = 0; i < Render.pixels.length; i++) {
			pixels[i] = Render.pixels[i];
		}

		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public void run() {
		init();
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double UPDATE_CAP = 1.0 / cap;
		double passedTime = 0;
		double unprocessedTime = 0;
		double frameTime = 0;
		double frames = 0;
		double FPS = 0;
		
		while (running) {
			render = false; 
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameTime += passedTime;
	
			
			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				tick();
				if(frameTime >= 1.0) {
					frameTime = 0;
					FPS = frames;
					frames = 0;
					System.out.println("FPS:" + FPS);
				}
			}
			
			if(render) {
				render();
				frames++;
			}else {
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static DisplayObject Render() {
		return Render;
	}

	public static void setCap(double c) {
		cap = c;
	}
	
	
}