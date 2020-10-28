package main.java.lotf.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.client.gui.GuiButton;
import main.java.lotf.client.ui.button.ButtonStartGame;
import main.java.ulibs.utils.math.Vec2i;

public class MenuMain extends Menu {
	
	public MenuMain() {
		super("main_menu");
	}
	
	@Override
	public List<GuiButton> registerButtons() {
		List<GuiButton> buttons = new ArrayList<GuiButton>();
		
		buttons.add(new GuiButton("button_main_start", new ButtonStartGame(new Vec2i(Main.HUD_WIDTH / 2 - 32, Main.HUD_HEIGHT / 2 - 8))));
		
		return buttons;
	}
}
