package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.world.WorldHandler;

public class DebugHud {

	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	
	public void render(Graphics2D g, String fps) {
		g.setFont(FONT);
		g.setColor(Color.RED);
		
		int y = 24;
		
		g.drawString("FPS : " + fps, 1, y);
		
		WorldHandler worldHandler = Main.getMain().getWorldHandler();
		if (worldHandler.getPlayer() != null) {
			g.drawString("World : " + worldHandler.getPlayerWorldType(), 1, y += 9);
			g.drawString("RoomID : " + worldHandler.getPlayerRoom().getRoomID(), 1, y += 9);
		}
	}
}
