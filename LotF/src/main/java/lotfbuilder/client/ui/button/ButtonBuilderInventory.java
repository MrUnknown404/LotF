package main.java.lotfbuilder.client.ui.button;

import main.java.lotf.client.ui.button.Button;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;

public class ButtonBuilderInventory extends Button {
	
	protected TileInfo info;
	
	public ButtonBuilderInventory(Vec2i pos, TileInfo info) {
		super(pos, new Vec2i(16, 16));
		this.info = info;
	}
	
	@Override
	public boolean onClick(ClickType clickType, ClickModifier clickMod) {
		if (!MainBuilder.main.builder.isInvOpen()) {
			return false;
		}
		
		if (clickType == ClickType.left) {
			MainBuilder.main.builder.setMouseTile(info);
			return true;
		} else if (clickType == ClickType.right) {
			MainBuilder.main.builder.setMouseTile(null);
			return true;
		}
		
		return false;
	}
}
