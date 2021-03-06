package main.java.lotfbuilder.client.ui.button;

import main.java.lotfbuilder.MainBuilder;
import main.java.ulibs.utils.math.Vec2i;

public class ButtonBuilderHotbar extends ButtonBuilderInventory {
	
	protected final int hotbarID;
	
	public ButtonBuilderHotbar(Vec2i pos, int hotbarID) {
		super(pos, null);
		this.hotbarID = hotbarID;
	}
	
	@Override
	public boolean onClick(ClickType clickType, ClickModifier clickMod) {
		if (MainBuilder.main.builder.isInvOpen()) {
			if (clickType == ClickType.left) {
				MainBuilder.main.builder.setHotbarSlotToMouse(hotbarID);
				return true;
			} else if (clickType == ClickType.right) {
				MainBuilder.main.builder.clearHotbarSlot(hotbarID);
				return true;
			}
			
			return false;
		}
		
		return false;
	}
}
