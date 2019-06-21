package main.java.lotf.client;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;

public class Camera implements ITickable {

	private Vec2f pos = new Vec2f();
	
	public void tick() {
		EntityPlayer p = Main.getMain().getWorldHandler().getPlayer();
		
		if (p.getRoomToBe() == null) {
			pos.setX(MathH.fClamp((p.getPosX() + p.getWidth() / 2) - Main.getHudWidth() / 2, p.getRoom().getPosX(),
					p.getRoom().getPosX() + (p.getRoom().getSizeX() * Tile.TILE_SIZE) - Main.getHudWidth()));
			pos.setY(MathH.fClamp((p.getPosY() + p.getHeight() / 2) - Main.getHudHeight() / 2, p.getRoom().getPosY() - 16,
					p.getRoom().getPosY() + (p.getRoom().getSizeY() * Tile.TILE_SIZE) - Main.getHudHeight()));
		} else {
			switch (p.getRoomToBeDir()) {
				case north:
					pos.addY(-((p.getRoom().getSizeY() * Tile.TILE_SIZE) / 30f));
					break;
				case east:
					pos.addX((p.getRoom().getSizeX() * Tile.TILE_SIZE) / 30f);
					break;
				case south:
					pos.addY((p.getRoom().getSizeY() * Tile.TILE_SIZE) / 30f);
					break;
				case west:
					pos.addX(-((p.getRoom().getSizeX() * Tile.TILE_SIZE) / 30f));
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
