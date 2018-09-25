package main.java.lotf.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

import main.java.lotf.ConsoleMain;

public final class ConsoleWindow {
	
	private JTextPane textArea = new JTextPane();
	private JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public ConsoleWindow(ConsoleMain main) {
		JFrame frame = new JFrame("Console");
		
		frame.setPreferredSize(new Dimension(224 * 6 + 16, 144 * 5 + 3));
		frame.setMinimumSize(new Dimension(240, 147));
		
		Container pane = frame.getContentPane();
		pane.setBackground(Color.BLACK);
		
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		((DefaultCaret)textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new Font("Font", Font.BOLD, 16));
		textArea.setEditable(false);
		
		frame.add(scroll);
	}
	
	public JTextPane getTextArea() {
		return textArea;
	}
	
	public JScrollPane getScroll() {
		return scroll;
	}
}
