package main.java.lotf.client;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.ITickable;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2f;

public class Camera implements ITickable {

	private Vec2f pos = new Vec2f();
	
	@Override
	public void tick() {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		if (p.getRoomToBe() == null) {
			pos.setX(MathH.fClamp((p.getPosX() + p.getWidth() / 2) - Main.HUD_WIDTH / 2, p.getRoom().getPosX(),
					p.getRoom().getPosX() + (p.getRoom().getWidth() * Tile.TILE_SIZE) - Main.HUD_WIDTH));
			pos.setY(MathH.fClamp((p.getPosY() + p.getHeight() / 2) - Main.HUD_HEIGHT / 2, p.getRoom().getPosY() - 16,
					p.getRoom().getPosY() + (p.getRoom().getHeight() * Tile.TILE_SIZE) - Main.HUD_HEIGHT));
		} else {
			switch (p.getRoomToBeDir()) {
				case north:
					pos.addY(-((p.getRoomToBe().getHeight() * Tile.TILE_SIZE) / 30f));
					break;
				case east:
					pos.addX((p.getRoomToBe().getWidth() * Tile.TILE_SIZE) / 30f);
					break;
				case south:
					pos.addY((p.getRoomToBe().getHeight() * Tile.TILE_SIZE) / 30f);
					break;
				case west:
					pos.addX(-((p.getRoomToBe().getWidth() * Tile.TILE_SIZE) / 30f));
					break;
			}
		}
	}
	
	public float getPosX() {
		return pos.getX();
	}
	
	public float getPosY() {
		return pos.getY();
	}
}
