package main.java.lotf;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.java.lotf.client.ConsoleWindow;

public final class MainConsole extends Canvas {
	private static final long serialVersionUID = 359980311258365686L;
	
	private static ConsoleWindow w;
	private static StyledDocument doc;
	private static Style style;
	
	public MainConsole() {
		w = new ConsoleWindow(this);
		doc = w.getTextArea().getStyledDocument();
		style = w.getTextArea().addStyle("Color Style", null);
	}
	
	public static void print(String str) {
		print(Color.WHITE, str);
	}
	
	public static void print(Color color, String str) {
		StyleConstants.setForeground(style, color);
		
		try {
			doc.insertString(doc.getLength(), str + System.getProperty("line.separator"), style);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}