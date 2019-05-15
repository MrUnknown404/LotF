package main.java.lotf.entities;

import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.IDamageable;
import main.java.lotf.inventory.PlayerInventory;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotf.world.World;

public class EntityPlayer extends Entity implements IDamageable {

	private static final float MOVE_SPEED = 1.5f, CHANGE_ROOM_SPEED = 0.333f;
	
	private EnumWorldType worldType;
	private EnumDirection toBeRoomDirection;
	private int roomID, toBeRoomID = -1, money, arrows, bombs;
	private float moveX, moveY;
	
	private CountableUpgradeState arrowsUpgradeState = CountableUpgradeState.one, bombsUpgradeState = CountableUpgradeState.one;
	
	private Map<EnumWorldType, Integer> keys = new HashMap<EnumWorldType, Integer>();
	private Map<EnumWorldType, Map<Integer, Boolean>> exploredMaps = new HashMap<EnumWorldType, Map<Integer, Boolean>>();
	
	private PlayerInventory inv;
	
	public EntityPlayer(EnumWorldType worldType, Vec2f pos, Room room) {
		super(new Vec2f(room.getPosX() + pos.getX(), room.getPosY() + pos.getY()), new Vec2i(14, 14));
		this.roomID = room.getRoomID();
		this.worldType = worldType;
		setupHealth(24);
		
		for (EnumWorldType type : EnumWorldType.values()) {
			keys.put(type, 0);
		}
		
		for (EnumWorldType type : EnumWorldType.values()) {
			Map<Integer, Boolean> exploredRooms = new HashMap<Integer, Boolean>();
			for (int i = 0; i < World.WORLD_SIZE.getBothMulti(); i++) {
				exploredRooms.put(i, false);
			}
			
			exploredMaps.put(type, exploredRooms);
		}
		
		exploreRoom(roomID);
		
		inv = new PlayerInventory();
	}
	
	@Override
	public void tick() {
		if (toBeRoomID != -1) {
			switch (toBeRoomDirection) {
				case north:
					setMoveX(0);
					setMoveY(-CHANGE_ROOM_SPEED);
					if (getRoom().getPosY() > pos.getY() + size.getY()) {
						roomID = toBeRoomID;
						toBeRoomID = -1;
						setMoveY(0);
						
						getRoom().onEnter(this);
						setPosY(getRoom().getPosY() + (getRoom().getSizeY() * Tile.TILE_SIZE) - size.getY());
						
						Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
					}
					break;
				case east:
					setMoveX(CHANGE_ROOM_SPEED);
					setMoveY(0);
					if (getRoom().getPosX() + (getRoom().getSizeX() * Tile.TILE_SIZE) < pos.getX()) {
						roomID = toBeRoomID;
						toBeRoomID = -1;
						setMoveX(0);
						
						getRoom().onEnter(this);
						setPosX(getRoom().getPosX());
						
						Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
					}
					break;
				case south:
					setMoveX(0);
					setMoveY(CHANGE_ROOM_SPEED);
					if (getRoom().getPosY() + (getRoom().getSizeY() * Tile.TILE_SIZE) < pos.getY()) {
						roomID = toBeRoomID;
						toBeRoomID = -1;
						setMoveY(0);
						
						getRoom().onEnter(this);
						setPosY(getRoom().getPosY());
						
						Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
					}
					break;
				case west:
					setMoveX(-CHANGE_ROOM_SPEED);
					setMoveY(0);
					if (getRoom().getPosX() > pos.getX() + size.getX()) {
						roomID = toBeRoomID;
						toBeRoomID = -1;
						setMoveX(0);
						
						getRoom().onEnter(this);
						setPosX(getRoom().getPosX() + (getRoom().getSizeX() * Tile.TILE_SIZE) - size.getX());
						
						Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
					}
					break;
				default:
					Console.print(Console.WarningType.FatalError, "Invalid EnumDirection : " + toBeRoomDirection + "!");
					break;
			}
			
			if (moveX != 0) {
				addPosX(moveX * MOVE_SPEED);
			}
			
			if (moveY != 0) {
				addPosY(moveY * MOVE_SPEED);
			}
		}
		
		if (Main.getMain().shouldPlayerHaveControl()) {
			if (moveX != 0) {
				addPosX(moveX * MOVE_SPEED);
			}
			
			if (moveY != 0) {
				addPosY(moveY * MOVE_SPEED);
			}
		}
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
	}
	
	private void moveRoom(Room room, EnumDirection type) {
		getRoom().onLeave(this);
		toBeRoomDirection = type;
		toBeRoomID = room.getRoomID();
		
		Main.getMain().setGamestate(getClass(), Main.Gamestate.softPause);
		
		switch (type) {
			case north:
				setPosX(MathH.clamp(pos.getX(), room.getPosX(), room.getPosX() + (room.getSizeX() * Tile.TILE_SIZE)));
				setPosY(getRoom().getPosY());
				break;
			case east:
				setPosX(getRoom().getPosX() + (getRoom().getSizeX() * Tile.TILE_SIZE) - size.getX());
				setPosY(MathH.clamp(pos.getY(), room.getPosY(), room.getPosY() + (room.getSizeY() * Tile.TILE_SIZE)));
				break;
			case south:
				setPosX(MathH.clamp(pos.getX(), room.getPosX(), room.getPosX() + (room.getSizeX() * Tile.TILE_SIZE)));
				setPosY(getRoom().getPosY() + (getRoom().getSizeY() * Tile.TILE_SIZE) - size.getY());
				break;
			case west:
				setPosX(getRoom().getPosX());
				setPosY(MathH.clamp(pos.getY(), room.getPosY(), room.getPosY() + (room.getSizeY() * Tile.TILE_SIZE)));
				break;
			default:
				Console.print(Console.WarningType.FatalError, "Invalid EnumDirection : " + type + "!");
				break;
		}
	}
	
	private void attemptMoveRoom() {
		for (EnumDirection type : EnumDirection.values()) {
			if (getRoom().getRoomBounds(type).intersects(getBounds())) {
				for (Room r : getWorld().getRooms()) {
					if (r.isActive() && r != getRoom() && getRoom().getRoomBounds(type).intersects(r.getBounds())) {
						moveRoom(r, type);
						return;
					}
				}
			}
		}
	}
	
	@Override
	public void addPosX(float x) {
		pos.addX(x);
		
		
		if (toBeRoomID == -1) {
			attemptMoveRoom();
			pos.setX(MathH.clamp(pos.getX(), getRoom().getPosX(), getRoom().getPosX() + (getRoom().getSizeX() * Tile.TILE_SIZE) - size.getX()));
		}
	}
	
	@Override
	public void addPosY(float y) {
		pos.addY(y);
		
		
		if (toBeRoomID == -1) {
			attemptMoveRoom();
			pos.setY(MathH.clamp(pos.getY(), getRoom().getPosY(), getRoom().getPosY() + (getRoom().getSizeY() * Tile.TILE_SIZE) - size.getY()));
		}
	}
	
	public void addHeartContainer() {
		if (canAddHeartContainer()) {
			HEARTS.add(4);
		}
	}
	
	public boolean canAddHeartContainer() {
		if (HEARTS.size() < 24) {
			return true;
		} else {
			return false;
		}
	}
	
	public void exploreRoom(int roomID) {
		exploredMaps.get(worldType).put(roomID, true);
	}
	
	public void forceSetRoom(Room room) {
		this.roomID = room.getRoomID();
	}
	
	public void setMoveX(float moveX) {
		this.moveX = moveX;
	}
	
	public void setMoveY(float moveY) {
		this.moveY = moveY;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setArrows(int arrows) {
		this.arrows = arrows;
	}
	
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	
	public int getKeys() {
		return keys.get(worldType);
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getArrows() {
		return arrows;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public int getMaxArrows() {
		return arrowsUpgradeState.amount;
	}
	
	public int getMaxBombs() {
		return bombsUpgradeState.amount;
	}
	
	public PlayerInventory getInventory() {
		return inv;
	}
	
	public boolean didExploredRoom(int roomID) {
		return exploredMaps.get(worldType).get(roomID);
	}
	
	public Room getRoom() {
		return getWorld().getRoom(roomID);
	}
	
	public World getWorld() {
		return Main.getMain().getWorldHandler().getPlayerWorld();
	}
	
	public Room getRoomToBe() {
		if (toBeRoomID == -1) {
			return null;
		}
		
		return getWorld().getRoom(toBeRoomID);
	}
	
	public EnumWorldType getWorldType() {
		return worldType;
	}
	
	private enum CountableUpgradeState {
		one  (10),
		two  (30),
		three(50),
		four (99);
		
		private final int amount;
		
		private CountableUpgradeState(int amount) {
			this.amount = amount;
		}
	}
}
