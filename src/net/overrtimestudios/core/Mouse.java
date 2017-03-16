package net.overrtimestudios.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener{

	public static int mx, my;

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}
	
	
}
