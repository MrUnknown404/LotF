package main.java.lotf.inventory;

import main.java.lotf.Main;
import main.java.lotf.util.math.Vec2i;

public class PlayerInventory {

	private Inventory normalInventory, swordInventory;
	private RingInventory ringInventory;
	
	private boolean isOpen;
	
	public PlayerInventory() {
		normalInventory = new Inventory(new Vec2i(4, 5));
		swordInventory = new Inventory(new Vec2i(1, 5));
		
		ringInventory = new RingInventory();
	}
	
	public void toggleInventory() {
		if (Main.getMain().getWorldHandler().getPlayer().getRoomToBe() == null) {
			if (isOpen) {
				Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
			} else {
				Main.getMain().setGamestate(getClass(), Main.Gamestate.softPause);
			}
			
			isOpen = !isOpen;
		}
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public Inventory getNormalInventory() {
		return normalInventory;
	}
	
	public Inventory getSwordInventory() {
		return swordInventory;
	}
	
	public RingInventory getRingInventory() {
		return ringInventory;
	}
}
