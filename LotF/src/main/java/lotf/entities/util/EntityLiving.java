package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public abstract class EntityLiving extends Entity {
	
	protected List<Integer> hearts = new ArrayList<Integer>();
	private final List<Integer> startHearts;
	
	protected EntityLiving(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size);
		for (int i = 0; i < totalHearts; i++) {
			hearts.add(4);
		}
		startHearts = hearts;
	}
	
	@Override
	public void reset() {
		super.reset();
		hearts = startHearts;
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
	
	public List<Integer> getHearts() {
		return hearts;
	}
	
	public EnumDirection getFacing() {
		return facing;
	}
}
