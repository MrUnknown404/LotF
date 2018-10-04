package main.java.lotf.entity;

import java.awt.Rectangle;

import main.java.lotf.Main;
import main.java.lotf.entity.util.Hearts;
import main.java.lotf.inventory.PlayerInventory;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotf.world.World;

public class EntityPlayer extends Entity {

	public static final int MOVE_SPEED = 2;
	private int moveDirX, moveDirY;
	private boolean canMove = true;
	
	private EnumDirection moveDir = EnumDirection.nil, facing = EnumDirection.north;
	private EnumDungeonType curDungeon = EnumDungeonType.nil;
	private Room roomToBe;
	private World world;
	
	private PlayerInventory inv = new PlayerInventory();
	private Hearts hearts;
	
	private int money, keys, mana;
	
	public EntityPlayer() {
		super(5 * Tile.TILE_SIZE, 5 * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE, EntityType.player);
		relativePos = new Vec2i(5 * Tile.TILE_SIZE, 5 * Tile.TILE_SIZE);
		
		hearts = new Hearts(3);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (canMove) {
			checkRoom();
			
			if (moveDirX != 0 || moveDirY != 0) {
				if (moveDirX > 0) {
					if (getInfront(EnumDirection.east) == null) {
						addRelativePos(moveDirX, 0);
					}
				} else if (moveDirX < 0) {
					if (getInfront(EnumDirection.west) == null) {
						addRelativePos(moveDirX, 0);
					}
				}
				
				if (moveDirY > 0) {
					if (getInfront(EnumDirection.south) == null) {
						addRelativePos(0, moveDirY);
					}
				} else if (moveDirY < 0) {
					if (getInfront(EnumDirection.north) == null) {
						addRelativePos(0, moveDirY);
					}
				}
				
				if (moveDirX > 0) {
					facing = EnumDirection.east;
				} else if (moveDirX < 0) {
					facing = EnumDirection.west;
				} else if (moveDirY > 0) {
					facing = EnumDirection.south;
				} else if (moveDirY < 0) {
					facing = EnumDirection.north;
				}
			}
		} else {
			if (moveDir == EnumDirection.west) {
				if (getRelativePos().getX() < room.getVecRoomSize().getX() * Tile.TILE_SIZE) {
					facing = EnumDirection.east;
					addRelativePos(1, 0);
					Main.getCamera().moveDir(facing);
				}
				
				if (getRelativePos().getX() == room.getVecRoomSize().getX() * Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX() - (room.getVecRoomSize().getX() * Tile.TILE_SIZE), getRelativePos().getY());
				}
			} else if (moveDir == EnumDirection.east) {
				if (getRelativePos().getX() > -Tile.TILE_SIZE) {
					facing = EnumDirection.west;
					addRelativePos(-1, 0);
					Main.getCamera().moveDir(facing);
				}
				
				if (getRelativePos().getX() == -Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX() + (room.getVecRoomSize().getX() * Tile.TILE_SIZE), getRelativePos().getY());
				}
			} else if (moveDir == EnumDirection.north) {
				if (getRelativePos().getY() < room.getVecRoomSize().getY() * Tile.TILE_SIZE) {
					facing = EnumDirection.south;
					addRelativePos(0, 1);
					Main.getCamera().moveDir(facing);
				}
				
				if (getRelativePos().getY() == room.getVecRoomSize().getY() * Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX(), getRelativePos().getY() - (room.getVecRoomSize().getY() * Tile.TILE_SIZE));
				}
			} else if (moveDir == EnumDirection.south) {
				if (getRelativePos().getY() > -Tile.TILE_SIZE) {
					facing = EnumDirection.north;
					addRelativePos(0, -1);
					Main.getCamera().moveDir(facing);
				}
				
				if (getRelativePos().getY() == -Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX(), getRelativePos().getY() + (room.getVecRoomSize().getY() * Tile.TILE_SIZE));
				}
			}
		}
	}
	
	private void checkRoom() {
		World w = Main.getWorldHandler().getPlayerWorld();
		for (int i = 0; i < w.getRooms().size(); i++) {
			if (!w.getRooms().get(i).equals(room) && !w.getRooms().get(i).equals(roomToBe)) {
				if (getBounds().intersects(w.getRooms().get(i).getBoundsWest())) {
					if (room.getExitID() == -1) {
						moveToRoom(w.getRooms().get(i), EnumDirection.west);
					} else {
						gotoRoom(room.getExitID(), room.getWorldID(), EnumDirection.west);
					}
				} else if (getBounds().intersects(w.getRooms().get(i).getBoundsEast())) {
					if (room.getExitID() == -1) {
						moveToRoom(w.getRooms().get(i), EnumDirection.east);
					} else {
						gotoRoom(room.getExitID(), room.getWorldID(), EnumDirection.east);
					}
				} else if (getBounds().intersects(w.getRooms().get(i).getBoundsNorth())) {
					if (room.getExitID() == -1) {
						moveToRoom(w.getRooms().get(i), EnumDirection.north);
					} else {
						gotoRoom(room.getExitID(), room.getWorldID(), EnumDirection.north);
					}
				} else if (getBounds().intersects(w.getRooms().get(i).getBoundsSouth())) {
					if (room.getExitID() == -1) {
						moveToRoom(w.getRooms().get(i), EnumDirection.south);
					} else {
						gotoRoom(room.getExitID(), room.getWorldID(), EnumDirection.south);
					}
				}
			}
		}
	}
	
	private void gotoRoom(int roomID, int worldID, EnumDirection dir) {
		canMove = false;
		room.onRoomExit();
		
		world = Main.getWorldHandler().getWorlds().get(worldID);
		room = world.getRooms().get(roomID);
		
		setRelativePos(room.getExitPos().getX(), room.getExitPos().getY());
		
		canMove = true;
		room.onRoomEnter();
	}
	
	private void moveToRoom(Room room, EnumDirection dir) {
		room.onRoomExit();
		
		roomToBe = room;
		canMove = false;
		moveDir = dir;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setMoveDirectionX(int moveDirX) {
		this.moveDirX = moveDirX;
	}
	
	public void setMoveDirectionY(int moveDirY) {
		this.moveDirY = moveDirY;
	}
	
	public void addMoney(int amount) {
		money = MathHelper.clamp(money + amount, 0, 99999);
	}
	
	public void addKeys(int amount) {
		keys = MathHelper.clamp(keys + amount, 0, 9);
	}
	
	public void addMana(int amount) {
		mana = MathHelper.clamp(mana + amount, 0, 16);
	}
	
	public void removeMoney(int amount) {
		money -= amount;
	}
	
	public void removeKeys(int amount) {
		keys -= amount;
	}
	
	public void removeMana(int amount) {
		mana -= amount;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getKeys() {
		return keys;
	}
	
	public int getMana() {
		return mana;
	}
	
	public boolean getMovingToRoom() {
		return !canMove;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Hearts getHearts() {
		return hearts;
	}
	
	public PlayerInventory getInventory() {
		return inv;
	}
	
	public Room getRoomToBe() {
		return roomToBe;
	}
	
	public EnumDungeonType getDungeon() {
		return curDungeon;
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPositionX() + 2), MathHelper.floor(getPositionY() + 2), width - 4, height - 4);
	}
}
