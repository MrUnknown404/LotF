package main.java.lotfbuilder.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.MainBuilder.BuilderLoop;

public class Window {
	public static boolean canRender = true;
	
	private static JFrame frame;
	private static GraphicsDevice gd;
	
	public Window(String title, BuilderLoop main) {
		frame = new JFrame(title);
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		frame.setPreferredSize(new Dimension(256 + 16, 144 + 39));
		frame.setMinimumSize(new Dimension(256 + 16, 144 + 39));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(main);
		
		frame.pack();
	}
	
	public static void toggleFullscreen() {
		canRender = false;
		frame.dispose();
		
		if (frame.isUndecorated()) {
			gd.setFullScreenWindow(null);
			frame.setUndecorated(false);
		} else {
			frame.setUndecorated(true);
			gd.setFullScreenWindow(frame);
		}
		
		frame.setVisible(true);
		MainBuilder.main.requestFocus();
		
		canRender = true;
	}
}
