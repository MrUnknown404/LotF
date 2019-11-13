package main.java.lotf.entities.util;

import java.util.List;

public interface IDamageable {
	public abstract List<Integer> getHearts();
	
	public default void setupHealth(int totalHearts) {
		for (int i = 0; i < totalHearts; i++) {
			getHearts().add(4);
		}
	}
	
	public default void heal(int amount) {
		int healed = 0;
		
		for (int i = 0; i < getHearts().size(); i++) {
			if (getHearts().get(i) == 4) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (getHearts().get(i) == 4) {
					break;
				}
				
				getHearts().set(i, getHearts().get(i) + 1);
				healed++;
			}
			
			amount -= healed;
		}
	}
	
	public default void damage(int amount) {
		int damaged = 0;
		
		for (int i = getHearts().size() - 1; i > -1; i--) {
			if (getHearts().get(i) == 0) {
				continue;
			}
			
			for (int j = 0; j < amount; j++) {
				if (getHearts().get(i) == 0) {
					break;
				}
				
				getHearts().set(i, getHearts().get(i) - 1);
				damaged++;
			}
			
			amount -= damaged;
		}
	}
}
