package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.util.math.MathH;
import main.java.lotf.world.WorldHandler;

public class DebugHud {

	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	
	public void draw(Graphics2D g, String fps) {
		WorldHandler worldHandler = Main.getMain().getWorldHandler();
		if (worldHandler.getPlayer() != null && worldHandler.getPlayer().getInventory().isOpen()) {
			return;
		}
		
		g.setFont(FONT);
		g.setColor(Color.RED);
		
		int y = 24;
		
		if (Main.getMain().getCommandConsole().isConsoleOpen()) {
			y += 55;
		}
		
		g.drawString("FPS : " + fps, 1, y);
		
		if (worldHandler.getPlayer() != null) {
			g.drawString("Pos : " + MathH.roundTo(worldHandler.getPlayer().getPosX(), 1) + ", " + MathH.roundTo(worldHandler.getPlayer().getPosY(), 1), 1, y += 9);
			g.drawString("C Pos : " + MathH.roundTo(Main.getMain().getCamera().getPosX(), 1) + ", " + MathH.roundTo(Main.getMain().getCamera().getPosY(), 1), 1, y += 9);
			g.drawString("World : " + worldHandler.getPlayerWorldType(), 1, y += 9);
			g.drawString("RoomID : " + worldHandler.getPlayerRoom().getRoomID(), 1, y += 9);
		}
	}
}
