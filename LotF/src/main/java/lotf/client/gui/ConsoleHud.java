package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;

public class ConsoleHud {

	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	
	private boolean tb = false;
	private int ti = 0;
	
	public void draw(Graphics2D g) {
		g.setFont(FONT);
		
		DebugConsole console = Main.getMain().getCommandConsole();
		
		if (console.isConsoleOpen()) {
			g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.5f));
			g.fillRect(0, 0, Main.HUD_WIDTH, ((DebugConsole.getMaxLines() + 2) * 9) - 10);
			g.setColor(Color.GREEN);
			
			if (tb) {
				g.drawString(">: " + console.getInput() + ":", 2, ((DebugConsole.getMaxLines() + 2) * 9) - 12);
			} else {
				g.drawString(">: " + console.getInput(), 2, ((DebugConsole.getMaxLines() + 2) * 9) - 12);
			}
			
			if (!console.getLines().isEmpty()) {
				for (int i = 1; i < console.getLines().size(); i++) {
					Map<String, Color> line = console.getLines().get(i);
					
					g.setColor((Color) line.values().toArray()[0]);
					g.drawString("<: " + line.keySet().toArray(new String[0])[0], 2, (DebugConsole.getMaxLines() + 1) * 9 - i * 9 - 1);
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