package main.java.lotf.inventory;

import main.java.lotf.Main;
import main.java.lotf.init.InitItems;
import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemUseable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.World;

public class PlayerInventory {

	private Inventory normalInventory, swordInventory, potionInventory, specialItems;
	private RingInventory ringInventory;
	private CollectibleInventory collectiblesInventory;
	
	private ItemUseable leftItem, rightItem, selectedSword;
	@SuppressWarnings("unused")
	private ItemBase selectedRing; //TODO write this
	
	private EnumSelectables selectedThing = EnumSelectables.NormalInventory;
	private int currentInvScreen, selectedSlot = 0;
	private boolean isOpen;
	
	public PlayerInventory() {
		normalInventory = new Inventory(new Vec2i(5, 5));
		swordInventory = new Inventory(new Vec2i(1, 5));
		potionInventory = new Inventory(new Vec2i(6, 1));
		collectiblesInventory = new CollectibleInventory();
		
		ringInventory = new RingInventory();
		
		specialItems = new Inventory(new Vec2i(6, 1));
		
		normalInventory.addItem(InitItems.CAPE);
		normalInventory.addItem(InitItems.BOW);
		swordInventory.addItem(InitItems.SWORD);
		ringInventory.addSelectedRing(InitItems.BASIC);
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
	
	public void switchInventoryScreen() {
		if (currentInvScreen == 3) {
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
			selectedThing = EnumSelectables.CollectiblesInventory;
		} else if (currentInvScreen == 3) {
			selectedThing = EnumSelectables.Map;
			selectedSlot = Main.getMain().getWorldHandler().getPlayerWorld().getFirstActiveRoom().getRoomID();
		}
	}
	
	public void moveSelectedInvSlot(EnumDirection dir) {
		World w = Main.getMain().getWorldHandler().getPlayerWorld();
		Vec2i startBounds = w.getWorldType().getStartActiveBounds();
		Vec2i endBounds = w.getWorldType().getEndActiveBounds();
		
		switch (dir) {
			case north:
				switch (selectedThing) {
					case Map:
						if (selectedSlot < w.getFirstActiveRoom().getRoomID() + startBounds.getX() - 1) {
							selectedSlot += ((endBounds.getY() - startBounds.getY() - 2) * 17);
						} else {
							selectedSlot -= 17;
						}
						break;
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
					case CollectiblesInventory:
						if (selectedSlot < collectiblesInventory.getSizeX()) {
							selectedSlot += collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1);
						} else {
							selectedSlot -= collectiblesInventory.getSizeX();
						}
						break;
				}
				break;
			case south:
				switch (selectedThing) {
					case Map:
						if (selectedSlot >= w.getLastActiveRoom().getRoomID() - endBounds.getX()) {
							selectedSlot -= 17 * ((endBounds.getY() - startBounds.getY()) - 2);
						} else {
							selectedSlot += 17;
						}
						break;
					case NormalInventory:
						if (selectedSlot >= normalInventory.getSizeX() * (normalInventory.getSizeY() - 1)) {
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
					case CollectiblesInventory:
						if (selectedSlot >= collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1)) {
							selectedSlot -= collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1);
						} else {
							selectedSlot += collectiblesInventory.getSizeX();
						}
						break;
				}
				break;
			case west:
				switch (selectedThing) {
					case Map:
						if (w.getRoom(selectedSlot - 1) == null) {
							if (currentInvScreen == 0) {
								selectedThing = EnumSelectables.SwordInventory;
								selectedSlot = 0;
							} else if (currentInvScreen == 1) {
								selectedThing = EnumSelectables.RingInventory;
								selectedSlot = ringInventory.getSizeX() - 1;
							} else if (currentInvScreen == 2) {
								selectedThing = EnumSelectables.CollectiblesInventory;
								selectedSlot = collectiblesInventory.getSizeX() - 1;
							} else if (currentInvScreen == 3) {
								//TODO do_when_i_create_fourth_screen;
							}
						} else {
							selectedSlot--;
						}
						break;
					case NormalInventory:
						if ((double) selectedSlot / normalInventory.getSizeX() == (int) selectedSlot / normalInventory.getSizeX()) { 
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
					case PotionInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
					case RingInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
					case SpecialInventory:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
					case SwordInventory:
						selectedThing = EnumSelectables.NormalInventory;
						selectedSlot = selectedSlot * normalInventory.getSizeX() + normalInventory.getSizeX() - 1;
						break;
					case CollectiblesInventory:
						if ((double) selectedSlot / collectiblesInventory.getSizeX() == (int) selectedSlot / collectiblesInventory.getSizeX()) { 
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case east:
				switch (selectedThing) {
					case Map:
						if (w.getRoom(selectedSlot + 1) == null) {
							if (currentInvScreen == 0) {
								selectedThing = EnumSelectables.NormalInventory;
								selectedSlot = 0;
							} else if (currentInvScreen == 1) {
								selectedThing = EnumSelectables.RingInventory;
								selectedSlot = 0;
							} else if (currentInvScreen == 2) {
								selectedThing = EnumSelectables.CollectiblesInventory;
								selectedSlot = 0;
							} else if (currentInvScreen == 3) {
								//TODO do_when_i_create_fourth_screen;
							}
						} else {
							selectedSlot++;
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
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case RingInventory:
						if (selectedSlot == ringInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case SpecialInventory:
						if (selectedSlot == specialItems.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case SwordInventory:
						selectedThing = EnumSelectables.Map;
						selectedSlot = w.getFirstActiveRoom().getRoomID();
						break;
					case CollectiblesInventory:
						if ((double) (selectedSlot + 1) / collectiblesInventory.getSizeX() == (int) (selectedSlot + 1) / collectiblesInventory.getSizeX()) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
				}
				break;
		}
	}
	
	public ItemBase getSelectedItem() {
		if (!isOpen) {
			return null;
		}
		
		switch (selectedThing) {
			case NormalInventory:
				return normalInventory.getItem(selectedSlot);
			case PotionInventory:
				return potionInventory.getItem(selectedSlot);
			case RingInventory:
				return ringInventory.getSelectedRing(selectedSlot);
			case SpecialInventory:
				return specialItems.getItem(selectedSlot);
			case SwordInventory:
				return swordInventory.getItem(selectedSlot);
			default:
				return null;
		}
	}
	
	public void selectLeft() {
		switch (selectedThing) {
			case NormalInventory:
				if (leftItem == null) {
					leftItem = (ItemUseable) getSelectedItem();
					normalInventory.setItem(selectedSlot, null);
				} else {
					ItemUseable temp = (ItemUseable) getSelectedItem();
					normalInventory.setItem(selectedSlot, leftItem);
					leftItem = temp;
				}
				
				break;
			case PotionInventory:
				//TODO usePotion;
				break;
			case RingInventory:
				selectedRing = ringInventory.getSelectedRing(selectedSlot);
				break;
			case SwordInventory:
				selectedSword = (ItemUseable) swordInventory.getItem(selectedSlot);
				break;
			default:
				break;
		}
	}
	
	public void selectRight() {
		switch (selectedThing) {
			case NormalInventory:
				if (rightItem == null) {
					rightItem = (ItemUseable) getSelectedItem();
					normalInventory.setItem(selectedSlot, null);
				} else {
					ItemUseable temp = (ItemUseable) getSelectedItem();
					normalInventory.setItem(selectedSlot, rightItem);
					rightItem = temp;
				}
				
				break;
			case PotionInventory:
				//TODO usePotion;
				break;
			case RingInventory:
				selectedRing = ringInventory.getSelectedRing(selectedSlot);
				break;
			case SwordInventory:
				selectedSword = (ItemUseable) swordInventory.getItem(selectedSlot);
				break;
			default:
				break;
		}
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public ItemUseable getLeftItem() {
		return leftItem;
	}
	
	public ItemUseable getRightItem() {
		return rightItem;
	}
	
	public ItemUseable getSelectedSword() {
		return selectedSword;
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
	
	public CollectibleInventory getCollectiblesInventory() {
		return collectiblesInventory;
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
		SpecialInventory,
		CollectiblesInventory;
	}
}
