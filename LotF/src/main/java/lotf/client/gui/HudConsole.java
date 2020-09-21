package main.java.lotf.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.util.ITickable;
import main.java.ulibs.utils.Pair;

public class HudConsole extends Hud implements ITickable {
	
	private static final Font FONT = new Font("Font", Font.PLAIN, 9);
	
	private boolean tb = false;
	private int ti = 0;
	
	@Override
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
					Pair<String, Color> line = console.getLines().get(i);
					
					g.setColor(line.getR());
					g.drawString("<: " + line.getL(), 2, (DebugConsole.getMaxLines() + 1) * 9 - i * 9 - 1);
				}
			}
		}
	}
	
	@Override
	public void tick() {
		if (ti == 0) {
			ti = 50;
			tb = !tb;
		} else {
			ti--;
		}
	}
}