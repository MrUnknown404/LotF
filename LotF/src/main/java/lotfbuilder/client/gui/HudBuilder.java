package main.java.lotfbuilder.client.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import main.java.lotf.client.gui.Hud;
import main.java.lotf.init.Tiles;
import main.java.lotf.tile.TileInfo;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.math.MathH;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.MouseHandler;

public class HudBuilder extends Hud {
	
	private BufferedImage hud, hudSelect;
	private Map<TileInfo, BufferedImage> tiles = new HashMap<TileInfo, BufferedImage>();
	
	@Override
	protected void onSetupTextures() {
		hud = registerGUITexture("hud");
		hudSelect = registerGUITexture("hud_select");
		
		for (TileInfo info : Tiles.getAll()) {
			if (info.getTextureCount() == 1) {
				tiles.put(info, GetResource.getTexture(false, ResourceType.tile, info.getName()));
			} else {
				tiles.put(info, GetResource.getTexture(false, ResourceType.tile, info.getName() + "/" + info.getName() + "_0"));
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(hud, 0, 0, 256, 16, null);
		g.drawImage(hudSelect, 26 + MainBuilder.main.builder.getSelectedSlot() * 21, 0, 18, 16, null);
		
		g.setColor(Color.BLACK);
		g.drawString("" + MainBuilder.main.builder.getTileLayer(), 1, 12);
		
		if (MainBuilder.main.builder.isInvOpen()) {
			for (int i = 0; i < MathH.ceil(Tiles.getAll().size() / 11f); i++) {
				g.drawImage(hud, 0, 18 + i * 17, 256, 16, null);
			}
			for (int i = 0; i < Tiles.getAll().size(); i++) {
				g.drawImage(tiles.get(Tiles.getAll().get(i)), 27 + i * 21 - (231 * MathH.floor(i / 11)), 18 + MathH.floor(i / 11) * 17, 16, 16, null);
			}
		}
		
		for (int i = 0; i < 11; i++) {
			TileInfo t = MainBuilder.main.builder.getHotbar().get(i);
			if (t != null) {
				g.drawImage(tiles.get(t), 27 + (i * 21), 0, 16, 16, null);
			}
		}
		
		TileInfo tm = MainBuilder.main.builder.getMouseTile();
		if (tm != null) {
			g.drawImage(tiles.get(tm), MouseHandler.MOUSE_POS.getX(), MouseHandler.MOUSE_POS.getY(), 16, 16, null);
		}
	}
}
