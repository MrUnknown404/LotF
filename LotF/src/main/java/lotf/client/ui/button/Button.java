package main.java.lotf.client.ui.button;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;

public abstract class Button extends GameObject {
	
	protected Button(Vec2i pos, Vec2i size) {
		super(new Vec2f(pos), size);
	}
	
	public abstract void onClick(ClickType clickType, ClickModifier clickMod);
}
