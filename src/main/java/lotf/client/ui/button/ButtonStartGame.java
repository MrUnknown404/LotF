package main.java.lotf.client.ui.button;

import main.java.lotf.Main;
import main.java.ulibs.utils.math.Vec2i;

public class ButtonStartGame extends Button {
	public ButtonStartGame(Vec2i pos) {
		super(pos, new Vec2i(64, 16));
	}
	
	@Override
	public boolean onClick(ClickType clickType, ClickModifier clickMod) {
		if (clickType == ClickType.left && clickMod == ClickModifier.none) {
			Main.getMain().startGame();
			return true;
		}
		
		return false;
	}
}
