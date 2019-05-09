package main.java.lotf.entity;

import java.util.HashMap;
import java.util.Map;

import main.java.lotf.Main;
import main.java.lotf.entity.util.Entity;
import main.java.lotf.entity.util.IDamageable;
import main.java.lotf.util.enums.EnumWorldType;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class EntityPlayer extends Entity implements IDamageable {

	private static final float MOVE_SPEED = 1.2f;
	
	private Room r;
	private int money, arrows, bombs;
	private int moveX, moveY;
	
	private CountableUpgradeState arrowsUpgradeState = CountableUpgradeState.one;
	private CountableUpgradeState bombsUpgradeState = CountableUpgradeState.one;
	
	private Map<EnumWorldType, Integer> keys = new HashMap<EnumWorldType, Integer>();
	
	public EntityPlayer(Vec2f pos, Room r) {
		super(pos, new Vec2i(14, 14));
		this.r = r;
		setupHealth(24);
		
		for (EnumWorldType type : EnumWorldType.values()) {
			keys.put(type, 0);
		}
	}
	
	@Override
	public void tick() {
		if (moveX != 0) {
			addPosX(moveX * MOVE_SPEED);
		}
		
		if (moveY != 0) {
			addPosY(moveY * MOVE_SPEED);
		}
	}
	
	@Override
	public void softReset() {
		
	}
	
	@Override
	public void hardReset() {
		
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
	
	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}
	
	public void setMoveY(int moveY) {
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
		return keys.get(Main.getMain().getWorldHandler().getPlayerWorldType());
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
	
	public Room getRoom() {
		return r;
	}
	
	private enum CountableUpgradeState {
		one  (10),
		two  (30),
		three(50),
		four (99);
		
		public final int amount;

		private CountableUpgradeState(int amount) {
			this.amount = amount;
		}
	}
}
