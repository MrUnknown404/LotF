package main.java.lotfbuilder.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.lotfbuilder.MainBuilder;

public final class Window {
	public Window(String title, MainBuilder main) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(224 * 2 + 16, 144 * 2 + 3));
		frame.setMinimumSize(new Dimension(224 * 2 + 16, 144 * 2 + 3));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(main);
		
		main.start();
	}
}
