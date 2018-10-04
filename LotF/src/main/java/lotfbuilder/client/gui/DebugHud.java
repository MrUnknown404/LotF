package main.java.lotfbuilder.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotfbuilder.MainBuilder;

public final class DebugHud {

	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	private static boolean showDebug = true;
	
	public void drawText(Graphics2D g, String fps) {
		int y;
		if (!MainBuilder.getDoesRoomExist()) {
			y = 14;
		} else {
			y = 62;
		}
		
		g.setFont(FONT);
		g.setColor(Color.WHITE);
		
		if (!MainBuilder.getRoomBuilder().isOpen) {
			g.drawString(fps, 1, y);
			
			if (!showDebug) {
				return;
			}
			
			if (!MainBuilder.getDoesRoomExist()) {
				if (MainBuilder.getRoomBuilder().creationState == 0) {
					g.drawString("Press F1 to create a room!", 1, y += 30);
					g.drawString("Press F2 to load a room!", 1, y += 15);
				} else if (MainBuilder.getRoomBuilder().creationState == 1) {
					g.drawString("Press F1 for RoomSize.small!", 1, y += 30);
					g.drawString("Press F2 for RoomSize.medium!", 1, y += 15);
					g.drawString("Press F3 for RoomSize.big!", 1, y += 15);
					g.drawString("Press F4 for RoomSize.veryBig!", 1, y += 15);
				}
			} else {
				g.drawString("Press F2 to save the room!", 1, y += 30);
				g.drawString("Press F3 to reset the room!", 1, y += 15);
			}
		}
	}
	
	public static void toggleShowDebug() {
		showDebug = !showDebug;
	}
}
