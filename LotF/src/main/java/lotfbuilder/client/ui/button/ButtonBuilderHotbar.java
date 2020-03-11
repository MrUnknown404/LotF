package main.java.lotfbuilder.client.ui.button;

import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;

public class ButtonBuilderHotbar extends ButtonBuilderInventory {
	
	protected final int hotbarID;
	
	public ButtonBuilderHotbar(Vec2i pos, int hotbarID) {
		super(pos, null);
		this.hotbarID = hotbarID;
	}
	
	@Override
	public void onClick(ClickType clickType, ClickModifier clickMod) {
		if (MainBuilder.main.builder.isInvOpen()) {
			if (clickType == ClickType.left) {
				MainBuilder.main.builder.setHotbarSlotToMouse(hotbarID);
			} else if (clickType == ClickType.right) {
				MainBuilder.main.builder.clearHotbarSlot(hotbarID);
			}
		} else {
			//TODO
		}
	}
}
