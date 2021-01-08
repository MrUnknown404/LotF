package main.java.lotf.entities.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.world.Room;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class EntityLiving extends Entity {
	
	private final List<Integer> hearts = new ArrayList<Integer>();
	private final List<Integer> startHearts;
	
	protected EntityLiving(EntityInfo info, Room room, Vec2f pos, Vec2i size, int totalHearts) {
		super(info, room, pos, size);
		for (int i = 0; i < totalHearts; i++) {
			hearts.add(4);
		}
		startHearts = new ArrayList<Integer>(hearts);
	}
	
	@Override
	public void reset() {
		super.reset();
		hearts.clear();
		hearts.addAll(startHearts);
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
		
		if (hearts.get(0) == 0) {
			kill();
		}
	}
	
	public void hit(int damage) {
		damage(damage);
	}
	
	public List<Integer> getHearts() {
		return hearts;
	}
	
	public abstract boolean isWalking();
}
