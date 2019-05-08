package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class DebugHud {

	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	
	public void render(Graphics2D g, String fps) {
		g.setFont(FONT);
		g.setColor(Color.GREEN);
		
		int y = 24;
		
		g.drawString("FPS : " + fps, 1, y);
	}
}
