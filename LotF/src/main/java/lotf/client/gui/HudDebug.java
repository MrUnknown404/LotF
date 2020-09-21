package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.ulibs.utils.math.MathH;

public class HudDebug extends Hud {

	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	public static boolean shouldRender;
	
	@Override
	public void render(Graphics2D g) {
		if (!Main.isDebug) {
			return;
		}
		
		EntityPlayer pl = Main.getMain().getWorldHandler().getPlayer();
		if ((pl != null && pl.getInventory().isOpen()) || !shouldRender) {
			return;
		}
		
		g.setFont(FONT);
		g.setColor(Color.RED);
		
		int y = 24;
		
		if (Main.getMain().getCommandConsole().isConsoleOpen()) {
			y += 55;
		}
		
		g.drawString("FPS : " + Main.getMain().getFPS(), 1, y);
		
		if (pl != null) {
			g.drawString("Pos : " + MathH.roundTo(pl.getPosX(), 1) + ", " + MathH.roundTo(pl.getPosY(), 1), 1, y += 9);
			g.drawString("C Pos : " + MathH.roundTo(Main.getMain().getCamera().getPosX(), 1) + ", " + MathH.roundTo(Main.getMain().getCamera().getPosY(), 1), 1, y += 9);
			g.drawString("World : " + pl.getWorldType(), 1, y += 9);
			g.drawString("RoomID : " + pl.getRoom().getRoomID(), 1, y += 9);
		}
	}
	
	@Override
	public void setup() {
		
	}
}
