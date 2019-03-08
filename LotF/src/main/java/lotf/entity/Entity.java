package main.java.lotf.entity;

import main.java.lotf.tile.Tile;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.TickableGameObject;
import main.java.lotf.world.Room;

public abstract class Entity extends TickableGameObject {
	
	protected transient Room room;
	
	protected String name;
	protected int meta;
	protected EntityType type;
	protected EnumDirection facing = EnumDirection.north;
	private transient boolean hasCollision = true;
	
	public Entity(int x, int y, int width, int height, EntityType type) {
		super(x, y, width, height);
		this.type = type;
		this.name = type.toString();
	}
	
	public void tick() {
		
	}
	
	public Tile getInfront(EnumDirection dir) {
		if (!hasCollision) {
			return null;
		}
		
		for (int i = 0; i < room.getTileLayer1().size(); i++) {
			Tile t = room.getTileLayer1().get(i);
			
			if (t.getTileType() != Tile.TileType.air) {
				if (dir == EnumDirection.east) {
					if (getBounds().intersects(t.getBounds().x - 2, t.getBounds().y, t.getBounds().width, t.getBounds().height)) {
						return t;
					}
				} else if (dir == EnumDirection.west) {
					if (getBounds().intersects(t.getBounds().x + 2, t.getBounds().y, t.getBounds().width, t.getBounds().height)) {
						return t;
					}
				} else if (dir == EnumDirection.north) {
					if (getBounds().intersects(t.getBounds().x, t.getBounds().y + 2, t.getBounds().width, t.getBounds().height)) {
						return t;
					}
				} else if (dir == EnumDirection.south) {
					if (getBounds().intersects(t.getBounds().x, t.getBounds().y - 2, t.getBounds().width, t.getBounds().height)) {
						return t;
					}
				}
			}
		}
		
		return null;
	}
	
	public void toggleCollision() {
		hasCollision = !hasCollision;
	}
	
	public void setFacing(EnumDirection facing) {
		this.facing = facing;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setMeta(int meta) {
		this.meta = meta;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public String getName() {
		return name;
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
	
	public EntityType getEntityType() {
		return type;
	}
	
	public enum EntityType {
		player (0),
		monster(1),
		npc    (2);
		
		public final int fId;
		
		private EntityType(int id) {
			this.fId = id;
		}
		
		public static EntityType getFromNumber(int id) {
			for (EntityType type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}
