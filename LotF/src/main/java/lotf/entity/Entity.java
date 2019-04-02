package main.java.lotf.entity;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class Entity extends GameObject implements ITickable {

	protected EnumDirection dir = EnumDirection.nil;
	protected List<Integer> hearts = new ArrayList<>();
	
	public Entity(Vec2f pos, Vec2i size, int totalHearts) {
		super(pos, size);
		
		for (int i = 0; i < totalHearts; i++) {
			hearts.add(4);
		}
	}
	
	@Override
	public void tick() {
		
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
	
	public EnumDirection getDirection() {
		return dir;
	}
}
