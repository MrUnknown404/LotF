package main.java.lotfbuilder.client.ui;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.client.ui.button.Button;
import main.java.lotf.init.Tiles;
import main.java.lotfbuilder.client.ui.button.ButtonBuilderHotbar;
import main.java.lotfbuilder.client.ui.button.ButtonBuilderInventory;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2i;

public class UIHandler {
	private static final List<Button> BUTTONS = new ArrayList<Button>();
	
	public static void registerUI() {
		for (int i = 0; i < 11 * MathH.ceil(Tiles.getAll().size() / 11f); i++) {
			BUTTONS.add(new ButtonBuilderInventory(new Vec2i(27 + i * 21 - (231 * MathH.floor(i / 11)), 18 + MathH.floor(i / 11) * 17),
					Tiles.getAll().size() > i ? Tiles.getAll().get(i) : null));
		}
		for (int i = 0; i < 11; i++) {
			BUTTONS.add(new ButtonBuilderHotbar(new Vec2i(27 + i * 21 - (231 * MathH.floor(i / 11)), 0), i));
		}
	}
	
	public static boolean checkClick(Vec2i click, ClickType clickType, ClickModifier clickMod) {
		for (Button b : BUTTONS) {
			if (b.getBounds().intersects(click.getX(), click.getY(), 1, 1)) {
				return b.onClick(clickType, clickMod);
			}
		}
		
		return false;
	}
	
	public static List<Button> getAllButtions() {
		return BUTTONS;
	}
	
	public enum ClickType {
		left, right, middle
	}
	
	public enum ClickModifier {
		none, alt, shift, ctrl
	}
}
