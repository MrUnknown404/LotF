package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;

public final class ConsoleHud {

	private static final Font FONT = new Font("Font", Font.BOLD, 16);
	
	private boolean tb = false;
	private int ti = 0;
	
	public void draw(Graphics2D g) {
		g.setFont(FONT);
		
		DebugConsole console = Main.getCommandConsole();
		
		if (console.isConsoleOpen) {
			g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.5f));
			g.fillRect(0, 0, Main.getHudWidth(), ((DebugConsole.getMaxLines() + 2) * 16) - 10);
			g.setColor(Color.GREEN);
			
			if (tb) {
				g.drawString(">: " + console.input + ":", 2, ((DebugConsole.getMaxLines() + 2) * 16) - 12);
			} else {
				g.drawString(">: " + console.input, 2, ((DebugConsole.getMaxLines() + 2) * 16) - 12);
			}
			
			if (!console.lines.isEmpty()) {
				for (int i = 1; i < console.lines.size(); i++) {
					if (console.lines.get(i).startsWith("*")) {
						g.setColor(Color.RED);
					} else if (console.lines.get(i).startsWith("$")) {
						g.setColor(new Color(0, 150, 255));
					} else {
						g.setColor(Color.GREEN);
					}
					
					g.drawString("<: " + console.lines.get(i), 2, ((DebugConsole.getMaxLines() + 1) * 16) - (i * 16));
				}
			}
		}
	}
	
	public void tick() {
		if (ti == 0) {
			ti = 50;
			tb = !tb;
		} else {
			ti--;
		}
	}
}
