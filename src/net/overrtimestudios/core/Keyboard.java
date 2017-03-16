package net.overrtimestudios.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private static boolean keys[] = new boolean[256];
	
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		if(keys[keycode])
			return;
		keys[keycode] = true;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		if(!keys[keycode])
			return;
		keys[keycode] = false;
	}
	
	public static boolean isKey(int keycode) {
		return keys[keycode];
	}
	
}
