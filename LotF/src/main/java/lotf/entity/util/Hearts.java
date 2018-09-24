package main.java.lotf.entity.util;

import java.util.ArrayList;
import java.util.List;

public final class Hearts {
	
	private List<Integer> hearts = new ArrayList<>();
	
	public Hearts(int heartContainers) {
		for (int i = 0; i < heartContainers; i++) {
			hearts.add(4);
		}
	}
	
	public void addHeartContainer() {
		if (canAddHeartContainer()) {
			hearts.add(4);
		}
	}
	
	public boolean canAddHeartContainer() {
		if (hearts.size() < 20) {
			return true;
		} else {
			return false;
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
	
	public List<Integer> getHearts() {
		return hearts;
	}
}
