package main.java.lotf.entity;

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
	private int mt = 0;
	
	private PlayerInventory inv = new PlayerInventory();
	private Hearts hearts;
	
	private int money, keys, mana;
	
	public EntityPlayer(int x, int y, boolean isAlive) {
		super(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE, isAlive, "ENT_player", 4);
		relativePos = new Vec2i(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		
		hearts = new Hearts(3);
	}
	
	@Override
	public void tickAlive() {
		if (canMove) {
			checkRoom();
			
			if (moveDirX != 0 || moveDirY != 0) {
				addRelativePos(moveDirX, moveDirY);
				
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
				if (getRelativePos().getX() < room.getRoomSize().getX() * Tile.TILE_SIZE) {
					if (mt == 0) {
						facing = EnumDirection.east;
						addRelativePos(1, 0);
						Main.getCamera().moveDir(facing);
					}
				}
				
				if (getRelativePos().getX() == room.getRoomSize().getX() * Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX() - (room.getRoomSize().getX() * Tile.TILE_SIZE), getRelativePos().getY());
				}
			} else if (moveDir == EnumDirection.east) {
				if (getRelativePos().getX() > -Tile.TILE_SIZE) {
					if (mt == 0) {
						facing = EnumDirection.west;
						addRelativePos(-1, 0);
						Main.getCamera().moveDir(facing);
					}
				}
				
				if (getRelativePos().getX() == -Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX() + (room.getRoomSize().getX() * Tile.TILE_SIZE), getRelativePos().getY());
				}
			} else if (moveDir == EnumDirection.north) {
				if (getRelativePos().getY() < room.getRoomSize().getY() * Tile.TILE_SIZE) {
					if (mt == 0) {
						facing = EnumDirection.south;
						addRelativePos(0, 1);
						Main.getCamera().moveDir(facing);
					}
				}
				
				if (getRelativePos().getY() == room.getRoomSize().getY() * Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX(), getRelativePos().getY() - (room.getRoomSize().getY() * Tile.TILE_SIZE));
				}
			} else if (moveDir == EnumDirection.south) {
				if (getRelativePos().getY() > -Tile.TILE_SIZE) {
					if (mt == 0) {
						facing = EnumDirection.north;
						addRelativePos(0, -1);
						Main.getCamera().moveDir(facing);
					}
				}
				
				if (getRelativePos().getY() == -Tile.TILE_SIZE) {
					moveDir = EnumDirection.nil;
					canMove = true;
					
					room = roomToBe;
					room.onRoomEnter();
					roomToBe = null;
					
					setRelativePos(getRelativePos().getX(), getRelativePos().getY() + (room.getRoomSize().getY() * Tile.TILE_SIZE));
				}
			}
		}
		
		if (mt == 1) {
			mt = 0;
		} else {
			mt++;
		}
	}
	
	private void checkRoom() {
		for (int i = 0; i < Main.getWorldHandler().getPlayerWorld().getRooms().size(); i++) {
			if (!Main.getWorldHandler().getPlayerWorld().getRooms().get(i).equals(room) && !Main.getWorldHandler().getPlayerWorld().getRooms().get(i).equals(roomToBe)) {
				if (getBoundsAll().intersects(Main.getWorldHandler().getPlayerWorld().getRooms().get(i).getBoundsWest())) {
					moveToRoom(Main.getWorldHandler().getPlayerWorld().getRooms().get(i), EnumDirection.west);
				} else if (getBoundsAll().intersects(Main.getWorldHandler().getPlayerWorld().getRooms().get(i).getBoundsEast())) {
					moveToRoom(Main.getWorldHandler().getPlayerWorld().getRooms().get(i), EnumDirection.east);
				} else if (getBoundsAll().intersects(Main.getWorldHandler().getPlayerWorld().getRooms().get(i).getBoundsNorth())) {
					moveToRoom(Main.getWorldHandler().getPlayerWorld().getRooms().get(i), EnumDirection.north);
				} else if (getBoundsAll().intersects(Main.getWorldHandler().getPlayerWorld().getRooms().get(i).getBoundsSouth())) {
					moveToRoom(Main.getWorldHandler().getPlayerWorld().getRooms().get(i), EnumDirection.south);
				}
			}
		}
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
}
