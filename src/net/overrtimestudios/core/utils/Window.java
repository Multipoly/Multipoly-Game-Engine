package net.overrtimestudios.core.utils;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import net.overrtimestudios.Engine;

public class Window {
	
	protected JFrame window;
	protected String title;
	protected int width, height;
	
	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.window = new JFrame(this.title);
	}
	
	public void initFrame(Engine e) {
		window.add(e, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(3);
		window.setResizable(true);
		window.setVisible(true);
		
		e.start();
	}

}