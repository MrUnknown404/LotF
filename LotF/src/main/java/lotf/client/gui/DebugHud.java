package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class DebugHud {

	private static final Font FONT = new Font("Font", Font.BOLD, 9);
	
	public void render(Graphics2D g, String fps) {
		g.setFont(FONT);
		g.setColor(Color.GREEN);
		
		int y = 9;
		
		g.drawString("FPS : " + fps, 1, y);
	}
}
