package main.java.lotf.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.java.lotf.Main;

public class Window {
	public Window(String title, Main main) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(256 + 16, 144 + 39));
		frame.setMinimumSize(new Dimension(256 + 16, 144 + 39));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(main);
	}
}
