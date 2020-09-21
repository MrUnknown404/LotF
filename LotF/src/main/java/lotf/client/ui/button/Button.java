package main.java.lotf.client.ui.button;

import main.java.lotf.util.GameObject;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class Button extends GameObject {
	
	protected Button(Vec2i pos, Vec2i size) {
		super(new Vec2f(pos), size);
	}
	
	public abstract boolean onClick(ClickType clickType, ClickModifier clickMod);
}
