package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;

public final class DebugHud {

	private static final Font FONT = new Font("Font", Font.BOLD, 9);
	
	private String activePlayerRoom;
	private String playerWorld;
	
	public void getInfo(EntityPlayer player) {
		activePlayerRoom = player.getRoom().toString() + ", " + player.getRoom().getRoomID();
		playerWorld = player.getWorld().toString();
	}
	
	public void drawText(Graphics2D g, String fps) {
		if (!Main.getWorldHandler().getPlayer().getInventory().isInventoryOpen) {
			int y = 24;
			
			if (Main.getCommandConsole().isConsoleOpen) {
				y += DebugConsole.getMaxLines() * 9 - 8;
			}
			
			g.setColor(Color.RED);
			g.setFont(FONT);
			
			g.drawString(fps, 1, y);
			g.drawString("World : " + playerWorld, 1, y += 8);
			g.drawString("Player room : " + activePlayerRoom, 1, y += 8);
		}
	}
}
