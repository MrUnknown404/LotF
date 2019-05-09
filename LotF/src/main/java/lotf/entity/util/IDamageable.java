package main.java.lotf.entity.util;

import java.util.ArrayList;
import java.util.List;

public interface IDamageable {
	public List<Integer> HEARTS = new ArrayList<>();
	
	public default void setupHealth(int totalHearts) {
		for (int i = 0; i < totalHearts; i++) {
			HEARTS.add(4);
		}
	}
	
	public default void heal(int amount) {
		int healed = 0;
		
		for (int i = 0; i < HEARTS.size(); i++) {
			if (HEARTS.get(i) == 4) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (HEARTS.get(i) == 4) {
					break;
				}
				
				HEARTS.set(i, HEARTS.get(i) + 1);
				healed++;
			}
			
			amount -= healed;
		}
	}
	
	public default void damage(int amount) {
		int damaged = 0;
		
		for (int i = HEARTS.size() - 1; i > -1; i--) {
			if (HEARTS.get(i) == 0) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (HEARTS.get(i) == 0) {
					break;
				}
				
				HEARTS.set(i, HEARTS.get(i) - 1);
				damaged++;
			}
			
			amount -= damaged;
		}
	}
	
	public default List<Integer> getHearts() {
		return HEARTS;
	}
}
