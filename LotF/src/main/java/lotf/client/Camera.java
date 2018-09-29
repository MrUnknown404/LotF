package main.java.lotf.client;

import main.java.lotf.Main;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.RoomPos;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public final class Camera {

	private Vec2i pos = new Vec2i();
	
	public void tick() {
		EntityPlayer p = Main.getWorldHandler().getPlayer();
		Room r = p.getRoom();
		RoomPos rpos = r.getRoomPos();
		
		if (!p.getMovingToRoom()) {
			int xt = MathHelper.clamp((p.getPositionX() + (p.getWidth() / 2)) - Main.getHudWidth() / 2,
					(rpos.getX() * r.getRoomSize().getX()) * Tile.TILE_SIZE,
					(((rpos.getX() + 1) * r.getRoomSize().getX()) * Tile.TILE_SIZE) - Main.getHudWidth());
			
			int yt = MathHelper.clamp((p.getPositionY() + (p.getHeight() / 2)) - Main.getHudHeight() / 2,
					(rpos.getY() * r.getRoomSize().getY()) * Tile.TILE_SIZE - Tile.TILE_SIZE,
					(((rpos.getY() + 1) * r.getRoomSize().getY()) * Tile.TILE_SIZE) - Main.getHudHeight());
			
			if (pos.getX() != xt) {
				pos.setX(xt);
			}
			
			if (pos.getY() != yt) {
				pos.setY(yt);
			}
		}
	}
	
	public void moveDir(EnumDirection dir) {
		Room r = Main.getWorldHandler().getPlayerRoom();
		
		if (dir == EnumDirection.north) {
			pos.add(0, -((r.getRoomSize().getY() * Tile.TILE_SIZE) / 36));
		} else if (dir == EnumDirection.east) {
			pos.add((r.getRoomSize().getX() * Tile.TILE_SIZE) / 32, 0);
		} else if (dir == EnumDirection.south) {
			pos.add(0, (r.getRoomSize().getY() * Tile.TILE_SIZE) / 36);
		} else if (dir == EnumDirection.west) {
			pos.add(-((r.getRoomSize().getX() * Tile.TILE_SIZE) / 32), 0);
		}
	}
	
	public Vec2i getPos() {
		return pos;
	}
}
