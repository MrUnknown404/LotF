package main.java.lotf.inventory;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.items.Item;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2i;

public class PlayerInventory {

	private Inventory normalInventory, swordInventory, potionInventory;
	private RingInventory ringInventory;
	
	private List<Boolean> specialItems = new ArrayList<Boolean>(6);
	
	private EnumSelectables selectedThing = EnumSelectables.NormalInventory;
	private int currentInvScreen, selectedSlot = 0;
	private boolean isOpen;
	
	public PlayerInventory() {
		normalInventory = new Inventory(new Vec2i(5, 5));
		swordInventory = new Inventory(new Vec2i(1, 5));
		potionInventory = new Inventory(new Vec2i(6, 1));
		
		ringInventory = new RingInventory();
		
		normalInventory.addItem(Item.CAPE);
	}
	
	public void toggleInventory() {
		if (Main.getMain().getWorldHandler().getPlayer().getRoomToBe() == null) {
			if (isOpen) {
				Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
			} else {
				Main.getMain().setGamestate(getClass(), Main.Gamestate.softPause);
			}
			
			isOpen = !isOpen;
			currentInvScreen = 0;
		}
	}
	
	public void switchInventoryScreen() {
		if (currentInvScreen == 2) {
			currentInvScreen = 0;
		} else {
			currentInvScreen++;
		}
		
		selectedSlot = 0;
		
		if (currentInvScreen == 0) {
			selectedThing = EnumSelectables.NormalInventory;
		} else if (currentInvScreen == 1) {
			selectedThing = EnumSelectables.RingInventory;
		} else if (currentInvScreen == 2) {
			selectedThing = EnumSelectables.Map;
		}
	}
	
	public void moveSelectedInvSlot(EnumDirection dir) {
		switch (dir) {
			case north:
				switch (selectedThing) {
					case Map: break;
					case NormalInventory:
						if (selectedSlot < normalInventory.getSizeX()) {
							selectedSlot += normalInventory.getSizeX() * (normalInventory.getSizeY() - 1);
						} else {
							selectedSlot -= normalInventory.getSizeX();
						}
						break;
					case SwordInventory:
						if (selectedSlot == 0) {
							selectedSlot = swordInventory.getSizeY() - 1;
						} else {
							selectedSlot--;
						}
						break;
					case PotionInventory:
						selectedThing = EnumSelectables.RingInventory;
						break;
					case RingInventory:
						selectedThing = EnumSelectables.SpecialInventory;
						break;
					case SpecialInventory:
						selectedThing = EnumSelectables.PotionInventory;
						break;
				}
				break;
			case south:
				switch (selectedThing) {
					case Map: break;
					case NormalInventory:
						if (selectedSlot >= normalInventory.getSizeY() * (normalInventory.getSizeX() - 1)) {
							selectedSlot -= normalInventory.getSizeX() * (normalInventory.getSizeY() - 1);
						} else {
							selectedSlot += normalInventory.getSizeX();
						}
						break;
					case SwordInventory:
						if (selectedSlot == swordInventory.getSizeY() - 1) {
							selectedSlot = 0;
						} else {
							selectedSlot++;
						}
						break;
					case PotionInventory:
						selectedThing = EnumSelectables.SpecialInventory;
						break;
					case RingInventory:
						selectedThing = EnumSelectables.PotionInventory;
						break;
					case SpecialInventory:
						selectedThing = EnumSelectables.RingInventory;
						break;
				}
				break;
			case west:
				switch (selectedThing) {
					case Map:
						if (currentInvScreen == 0) {
							selectedThing = EnumSelectables.SwordInventory;
							selectedSlot = 0;
						} else if (currentInvScreen == 1) {
							selectedThing = EnumSelectables.RingInventory;
							selectedSlot = ringInventory.getSizeX() - 1;
						} else if (currentInvScreen == 2) {
							int do_when_i_create_third_screen;
						}
						
						break;
					case NormalInventory:
						if ((double) selectedSlot / normalInventory.getSizeX() == (int) selectedSlot / normalInventory.getSizeX()) { 
							selectedThing = EnumSelectables.Map;
							selectedSlot = 0;
						} else {
							selectedSlot--;
						}
						break;
					case PotionInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
						} else {
							selectedSlot--;
						}
						break;
					case RingInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
						} else {
							selectedSlot--;
						}
						break;
					case SpecialInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
						} else {
							selectedSlot--;
						}
						break;
					case SwordInventory:
						selectedThing = EnumSelectables.NormalInventory;
						selectedSlot = selectedSlot * normalInventory.getSizeX() + normalInventory.getSizeX() - 1;
						break;
				}
				break;
			case east:
				switch (selectedThing) {
					case Map:
						if (currentInvScreen == 0) {
							selectedThing = EnumSelectables.NormalInventory;
							selectedSlot = 0;
						} else if (currentInvScreen == 1) {
							selectedThing = EnumSelectables.RingInventory;
							selectedSlot = 0;
						} else if (currentInvScreen == 2) {
							int do_when_i_create_third_screen;
						}
						break;
					case NormalInventory:
						if ((double) (selectedSlot + 1) / normalInventory.getSizeX() == (int) (selectedSlot + 1) / normalInventory.getSizeX()) {
							selectedThing = EnumSelectables.SwordInventory;
							selectedSlot /= normalInventory.getSizeX();
						} else {
							selectedSlot++;
						}
						break;
					case PotionInventory:
						if (selectedSlot == potionInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = 0;
						} else {
							selectedSlot++;
						}
						break;
					case RingInventory:
						if (selectedSlot == ringInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = 0;
						} else {
							selectedSlot++;
						}
						break;
					case SpecialInventory:
						if (selectedSlot == ringInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = 0;
						} else {
							selectedSlot++;
						}
						break;
					case SwordInventory:
						selectedThing = EnumSelectables.Map;
						selectedSlot = 0;
						break;
				}
				break;
		}
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public int getCurrentScreen() {
		return currentInvScreen;
	}
	
	public Inventory getNormalInventory() {
		return normalInventory;
	}
	
	public Inventory getSwordInventory() {
		return swordInventory;
	}
	
	public Inventory getPotionInventory() {
		return potionInventory;
	}
	
	public RingInventory getRingInventory() {
		return ringInventory;
	}
	
	public EnumSelectables getSelectedThing() {
		return selectedThing;
	}
	
	public static enum EnumSelectables {
		Map,
		NormalInventory,
		SwordInventory,
		RingInventory,
		PotionInventory,
		SpecialInventory;
	}
}
