package main.java.lotf.client.ui.button;

import main.java.lotf.util.GameObject;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class Button extends GameObject {
	
	public Button(Vec2i pos, Vec2i size) {
		super(new Vec2f(pos), size);
	}
	
	/** @return true of click was successful, otherwise false */
	public abstract boolean onClick(ClickType clickType, ClickModifier clickMod);
	
	public enum ClickType {
		left,
		right,
		middle;
	}
	
	public enum ClickModifier {
		none,
		alt,
		shift,
		ctrl;
	}
}
