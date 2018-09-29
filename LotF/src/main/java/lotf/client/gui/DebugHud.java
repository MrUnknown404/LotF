package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;

public final class DebugHud {

	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private String activePlayerRoom;
	private String playerRelativePosition;
	private String playerPosition;
	private String playerWorld;
	
	public void getInfo(EntityPlayer player) {
		activePlayerRoom = player.getRoom().toString();
		playerRelativePosition = "X:" + player.getRelativePos().getX() + " Y: " + player.getRelativePos().getY();
		playerPosition = "X:" + player.getPositionX() + " Y: " + player.getPositionY();
		playerWorld = player.getWorld().toString();
	}
	
	public void drawText(Graphics2D g, String fps) {
		if (!Main.getWorldHandler().getPlayer().getInventory().isInventoryOpen) {
			int y = 46;
			
			if (Main.getCommandConsole().isConsoleOpen) {
				y += ((DebugConsole.getMaxLines() + 2) * 12) - 6;
			}
			
			g.setColor(Color.GREEN);
			g.setFont(FONT);
			
			g.drawString(fps, 1, y);
			g.drawString("World : " + playerWorld, 1, y += 15);
			g.drawString("Player room pos : " + activePlayerRoom, 1, y += 15);
			g.drawString("Player Rel-Pos : " + playerRelativePosition, 1, y += 15);
			g.drawString("Player Pos : " + playerPosition, 1, y += 15);
		}
	}
}
