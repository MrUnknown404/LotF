package main.java.lotf.entity;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.java.lotf.Main;
import main.java.lotf.inventory.PlayerInventory;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.EnumDirection;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.world.Room;
import main.java.lotf.world.World;

public class EntityPlayer extends Entity {
	
	private PlayerInventory inventory = new PlayerInventory();
	
	public static final int MOVE_SPEED = 2;
	private transient int moveDirX, moveDirY;
	private transient boolean canMove = true, isCliffJumping;
	
	private transient EnumDirection moveDir = EnumDirection.nil;
	private transient Room roomToBe;
	private transient World world;
	private int worldID, roomID;
	
	private List<Integer> hearts = new ArrayList<>();
	private int money, keys, mana;
	private transient int cliffJumpTimer = 10, roomTimer = 15;
	
	public EntityPlayer() {
		super(5 * Tile.TILE_SIZE, 5 * Tile.TILE_SIZE, 16, 16, EntityType.player);
		
		for (int i = 0; i < 3; i++) {
			hearts.add(4);
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!isCliffJumping) {
			if (canMove) {
				checkRoom();
				
				if (moveDirY == 0 && cliffJumpTimer != 10) {
					cliffJumpTimer = 10;
				}
				
				if (moveDirX != 0 || moveDirY != 0) {
					if (moveDirX > 0) {
						if (getInfront(EnumDirection.east) == null) {
							addPosition(moveDirX, 0);
						} else if (getInfront(EnumDirection.east).getTileType() == Tile.TileType.door) {
							useDoor();
						}
					} else if (moveDirX < 0) {
						if (getInfront(EnumDirection.west) == null) {
							addPosition(moveDirX, 0);
						} else if (getInfront(EnumDirection.west).getTileType() == Tile.TileType.door) {
							useDoor();
						}
					}
					
					if (moveDirY > 0) {
						if (getInfront(EnumDirection.south) == null) {
							cliffJumpTimer = 10;
							addPosition(0, moveDirY);
						} else if (getInfront(EnumDirection.south).getTileType() == Tile.TileType.door) {
							useDoor();
						} else if (getInfront(EnumDirection.south).getTileType() == Tile.TileType.stoneWallJump) {
							if (cliffJumpTimer == 0) {
								cliffJumpTimer = 10;
								isCliffJumping = true;
							} else {
								cliffJumpTimer--;
							}
						}
					} else if (moveDirY < 0) {
						if (getInfront(EnumDirection.north) == null) {
							addPosition(0, moveDirY);
						} else if (getInfront(EnumDirection.north).getTileType() == Tile.TileType.door) {
							useDoor();
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
					if (roomTimer != 0) {
						facing = EnumDirection.east;
						
						if (getPositionX() < (room.getRoomPos().getX() * room.getVecRoomSize().getX() + room.getVecRoomSize().getX()) * Tile.TILE_SIZE) {
							addPosition(1, 0);
						}
						
						Main.getCamera().moveDir(facing);
						
						roomTimer--;
					} else {
						moveDir = EnumDirection.nil;
						canMove = true;
						
						room = roomToBe;
						room.onRoomEnter();
						roomToBe.onRoomExit();
						roomToBe = null;
					}
				} else if (moveDir == EnumDirection.east) {
					if (roomTimer != 0) {
						facing = EnumDirection.west;
						
						if (getPositionX() > (room.getRoomPos().getX() * room.getVecRoomSize().getX() * Tile.TILE_SIZE) - width) {
							addPosition(-1, 0);
						}
						
						Main.getCamera().moveDir(facing);
						
						roomTimer--;
					} else {
						canMove = true;
						
						room = roomToBe;
						room.onRoomEnter();
						roomToBe.onRoomExit();
						roomToBe = null;
					}
				} else if (moveDir == EnumDirection.north) {
					if (roomTimer != 0) {
						facing = EnumDirection.south;
						
						if (getPositionY() < (room.getRoomPos().getY() * room.getVecRoomSize().getY() + room.getVecRoomSize().getY()) * Tile.TILE_SIZE) {
							addPosition(0, 1);
						}
						
						Main.getCamera().moveDir(facing);
						
						roomTimer--;
					} else {
						canMove = true;
						
						room = roomToBe;
						room.onRoomEnter();
						roomToBe.onRoomExit();
						roomToBe = null;
					}
				} else if (moveDir == EnumDirection.south) {
					if (roomTimer != 0) {
						facing = EnumDirection.north;
						
						if (getPositionY() > (room.getRoomPos().getY() * room.getVecRoomSize().getY() * Tile.TILE_SIZE) - height) {
							addPosition(0, -1);
						}
						
						Main.getCamera().moveDir(facing);
						
						roomTimer--;
					} else {
						canMove = true;
						
						room = roomToBe;
						room.onRoomEnter();
						roomToBe.onRoomExit();
						roomToBe = null;
					}
				}
			}
		} else {
			if (getInfront(EnumDirection.south) != null) {
				facing = EnumDirection.south;
				addPosition(0, 4);
			} else {
				isCliffJumping = false;
			}
		}
	}
	
	private void checkRoom() {
		if (getBounds().intersects(room.getBoundsNorth())) {
			if (room.getEntranceDir() != EnumDirection.north) {
				if (room.getConnectedSides() != null) {
					for (int i = 0; i < room.getConnectedSides().length; i++) {
						if (room.getConnectedSides()[i] == EnumDirection.north) {
							room = world.getRoomAt(room.getRoomPos().getX(), room.getRoomPos().getY() - 1);
						}
					}
				} else if (world.getRoomAt(room.getRoomPos().getX(), room.getRoomPos().getY() - 1) != null) {
					moveToRoom(world.getRoomAt(room.getRoomPos().getX(), room.getRoomPos().getY() - 1), EnumDirection.south);
				}
			} else {
				useDoor();
			}
		} else if (getBounds().intersects(room.getBoundsEast())) {
			if (room.getEntranceDir() == EnumDirection.east) {
				useDoor();
			} else {
				if (world.getRoomAt(room.getRoomPos().getX() + 1, room.getRoomPos().getY()) != null) {
					moveToRoom(world.getRoomAt(room.getRoomPos().getX() + 1, room.getRoomPos().getY()), EnumDirection.west);
				}
			}
		} else if (getBounds().intersects(room.getBoundsSouth())) {
			if (room.getEntranceDir() == EnumDirection.south) {
				useDoor();
			} else {
				if (world.getRoomAt(room.getRoomPos().getX(), room.getRoomPos().getY() + 1) != null) {
					moveToRoom(world.getRoomAt(room.getRoomPos().getX(), room.getRoomPos().getY() + 1), EnumDirection.north);
				}
			}
		} else if (getBounds().intersects(room.getBoundsWest())) {
			if (room.getEntranceDir() == EnumDirection.west) {
				useDoor();
			} else {
				if (world.getRoomAt(room.getRoomPos().getX() - 1, room.getRoomPos().getY()) != null) {
					moveToRoom(world.getRoomAt(room.getRoomPos().getX() - 1, room.getRoomPos().getY()), EnumDirection.east);
				}
			}
		}
	}
	
	private void useDoor() {
		canMove = false;
		room.onRoomExit();
		
		world = Main.getWorldHandler().getWorlds().get(room.getExitWorldID());
		room = world.getRooms().get(room.getExitRoomID());
		
		setPosition(room.getEnterPos().getX() + ((room.getRoomPos().getX() * room.getVecRoomSize().getX()) * Tile.TILE_SIZE), room.getEnterPos().getY() + ((room.getRoomPos().getY() * room.getVecRoomSize().getY()) * Tile.TILE_SIZE));
		
		canMove = true;
		room.onRoomEnter();
	}
	
	private void moveToRoom(Room room, EnumDirection dir) {
		room.onRoomExit();
		
		roomToBe = room;
		canMove = false;
		roomTimer = 20;
		moveDir = dir;
	}
	
	public void addHeartContainer() {
		if (canAddHeartContainer()) {
			hearts.add(4);
		}
	}
	
	public void heal(int amount) {
		int healed = 0;
		
		for (int i = 0; i < hearts.size(); i++) {
			if (hearts.get(i) == 4) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (hearts.get(i) == 4) {
					break;
				}
				
				hearts.set(i, hearts.get(i) + 1);
				healed++;
			}
			
			amount -= healed;
		}
	}
	
	public void damage(int amount) {
		int damaged = 0;
		
		for (int i = hearts.size() - 1; i > -1; i--) {
			if (hearts.get(i) == 0) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (hearts.get(i) == 0) {
					break;
				}
				
				hearts.set(i, hearts.get(i) - 1);
				damaged++;
			}
			
			amount -= damaged;
		}
	}
	
	public boolean canAddHeartContainer() {
		if (hearts.size() < 24) {
			return true;
		} else {
			return false;
		}
	}
	
	public void savePlayerData() {
		Gson g = new Gson().newBuilder().setPrettyPrinting().create();
		FileWriter fw;
		File f = new File(Main.getSaveLocation() + "player.sav");
		
		roomID = room.getRoomID();
		if (world.getWorldType() == World.WorldType.dungeon) {
			worldID = world.getDungeonType().fId + 3;
		} else {
			worldID = world.getWorldType().fId;
		}
		
		try {
			fw = new FileWriter(f);
			
			g.toJson(this, fw);
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPlayerData() {
		//inv.setupSlots();
		//inv.swords.setupSlots();
		//inv.rings.setupSlots();
		//inv.hiddenItems.setupSlots();
		//inv.hiddenRings.setupSlots();
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
	
	public List<Integer> getHearts() {
		return hearts;
	}
	
	public PlayerInventory getInventory() {
		return inventory;
	}
	
	public Room getRoomToBe() {
		return roomToBe;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(MathHelper.floor(getPositionX() + 2), MathHelper.floor(getPositionY() + 2), width - 4, height - 4);
	}
}
