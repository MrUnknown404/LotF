package main.java.lotf.inventory;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.init.Items;
import main.java.lotf.items.potions.Potion;
import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.swords.Sword;
import main.java.lotf.items.util.Item;
import main.java.lotf.items.util.ItemUseable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.world.World;
import main.java.ulibs.utils.math.Vec2i;

public class PlayerInventory {
	
	private Inventory<Item> normalInventory, specialItems;
	private Inventory<Sword> swordInventory;
	private Inventory<Potion> potionInventory;
	private InventoryRing ringInventory;
	private CollectibleInventory collectiblesInventory;
	
	private ItemUseable leftItem, rightItem;
	private Sword equipedSword;
	private Ring equipedRing;
	
	private EnumSelectables selectedThing = EnumSelectables.NormalInventory;
	private int currentInvScreen, selectedSlot = 0;
	private boolean isOpen;
	
	private EntityPlayer player;
	
	public PlayerInventory(EntityPlayer player) {
		this.player = player;
		
		normalInventory = new Inventory<Item>(new Vec2i(5, 5));
		swordInventory = new Inventory<Sword>(new Vec2i(1, 5));
		potionInventory = new Inventory<Potion>(new Vec2i(6, 1));
		collectiblesInventory = new CollectibleInventory();
		
		ringInventory = new InventoryRing();
		
		specialItems = new Inventory<Item>(new Vec2i(6, 1));
		
		normalInventory.addItem(Items.CAPE);
		normalInventory.addItem(Items.BOW);
		swordInventory.addItem(Items.SWORD);
		ringInventory.addItem(Items.BASIC);
		potionInventory.addItem(Items.RED_POTION);
	}
	
	public void toggleInventory() {
		if (player.getRoomToBe() == null) {
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
			selectedSlot = player.getWorld().getFirstActiveRoom().getRoomID();
		}
	}
	
	public void moveSelectedInvSlot(EnumDirection dir) {
		World w = player.getWorld();
		Vec2i startBounds = w.getWorldType().getStartActiveBounds();
		Vec2i endBounds = w.getWorldType().getEndActiveBounds();
		
		switch (selectedThing) {
			case CollectiblesInventory:
				switch (dir) {
					case east:
						if ((double) (selectedSlot + 1) / collectiblesInventory.getSizeX() == (selectedSlot + 1) / collectiblesInventory.getSizeX()) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case north:
						if (selectedSlot < collectiblesInventory.getSizeX()) {
							selectedSlot += collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1);
						} else {
							selectedSlot -= collectiblesInventory.getSizeX();
						}
						break;
					case south:
						if (selectedSlot >= collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1)) {
							selectedSlot -= collectiblesInventory.getSizeX() * (collectiblesInventory.getSizeY() - 1);
						} else {
							selectedSlot += collectiblesInventory.getSizeX();
						}
						break;
					case west:
						if ((double) selectedSlot / collectiblesInventory.getSizeX() == selectedSlot / collectiblesInventory.getSizeX()) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case Map:
				switch (dir) {
					case east:
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
					case north:
						if (selectedSlot < w.getFirstActiveRoom().getRoomID() + startBounds.getX()) {
							selectedSlot += ((endBounds.getY() - startBounds.getY() - 1) * 17);
						} else {
							selectedSlot -= 17;
						}
						break;
					case south:
						if (selectedSlot >= w.getLastActiveRoom().getRoomID() - endBounds.getX()) {
							selectedSlot -= 17 * ((endBounds.getY() - startBounds.getY()) - 1);
						} else {
							selectedSlot += 17;
						}
						break;
					case west:
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
				}
				break;
			case NormalInventory:
				switch (dir) {
					case east:
						if ((double) (selectedSlot + 1) / normalInventory.getSizeX() == (selectedSlot + 1) / normalInventory.getSizeX()) {
							selectedThing = EnumSelectables.SwordInventory;
							selectedSlot /= normalInventory.getSizeX();
						} else {
							selectedSlot++;
						}
						break;
					case north:
						if (selectedSlot < normalInventory.getSizeX()) {
							selectedSlot += normalInventory.getSizeX() * (normalInventory.getSizeY() - 1);
						} else {
							selectedSlot -= normalInventory.getSizeX();
						}
						break;
					case south:
						if (selectedSlot >= normalInventory.getSizeX() * (normalInventory.getSizeY() - 1)) {
							selectedSlot -= normalInventory.getSizeX() * (normalInventory.getSizeY() - 1);
						} else {
							selectedSlot += normalInventory.getSizeX();
						}
						break;
					case west:
						if ((double) selectedSlot / normalInventory.getSizeX() == selectedSlot / normalInventory.getSizeX()) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case PotionInventory:
				switch (dir) {
					case east:
						if (selectedSlot == potionInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case north:
						selectedThing = EnumSelectables.RingInventory;
						break;
					case south:
						selectedThing = EnumSelectables.SpecialInventory;
						break;
					case west:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case RingInventory:
				switch (dir) {
					case east:
						if (selectedSlot == ringInventory.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case north:
						selectedThing = EnumSelectables.SpecialInventory;
						break;
					case south:
						selectedThing = EnumSelectables.PotionInventory;
						break;
					case west:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case SpecialInventory:
				switch (dir) {
					case east:
						if (selectedSlot == specialItems.getSizeX() - 1) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot++;
						}
						break;
					case north:
						selectedThing = EnumSelectables.PotionInventory;
						break;
					case south:
						selectedThing = EnumSelectables.RingInventory;
						break;
					case west:
						if (selectedSlot == 0) {
							selectedThing = EnumSelectables.Map;
							selectedSlot = w.getFirstActiveRoom().getRoomID();
						} else {
							selectedSlot--;
						}
						break;
				}
				break;
			case SwordInventory:
				switch (dir) {
					case east:
						selectedThing = EnumSelectables.Map;
						selectedSlot = w.getFirstActiveRoom().getRoomID();
						break;
					case north:
						if (selectedSlot == 0) {
							selectedSlot = swordInventory.getSizeY() - 1;
						} else {
							selectedSlot--;
						}
						break;
					case south:
						if (selectedSlot == swordInventory.getSizeY() - 1) {
							selectedSlot = 0;
						} else {
							selectedSlot++;
						}
						break;
					case west:
						selectedThing = EnumSelectables.NormalInventory;
						selectedSlot = selectedSlot * normalInventory.getSizeX() + normalInventory.getSizeX() - 1;
						break;
				}
				break;
		}
	}
	
	public Item getSelectedItem() {
		if (!isOpen) {
			return null;
		}
		
		switch (selectedThing) {
			case NormalInventory:
				return normalInventory.getItem(selectedSlot);
			case PotionInventory:
				return potionInventory.getItem(selectedSlot);
			case RingInventory:
				return ringInventory.getItem(selectedSlot);
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
				if (potionInventory.getItem(selectedSlot) != null) {
					potionInventory.getItem(selectedSlot).drink();
				}
				break;
			case RingInventory:
				if (equipedRing == ringInventory.getItem(selectedSlot)) {
					equipedRing = null;
					break;
				}
				
				equipedRing = ringInventory.getItem(selectedSlot);
				break;
			case SwordInventory:
				if (equipedSword == swordInventory.getItem(selectedSlot)) {
					equipedSword = null;
					break;
				}
				
				equipedSword = swordInventory.getItem(selectedSlot);
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
				if (potionInventory.getItem(selectedSlot) != null) {
					potionInventory.getItem(selectedSlot).drink();
				}
				break;
			case RingInventory:
				if (equipedRing == ringInventory.getItem(selectedSlot)) {
					equipedRing = null;
					break;
				}
				
				equipedRing = ringInventory.getItem(selectedSlot);
				break;
			case SwordInventory:
				if (equipedSword == swordInventory.getItem(selectedSlot)) {
					equipedSword = null;
					break;
				}
				
				equipedSword = swordInventory.getItem(selectedSlot);
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
	
	public ItemUseable getEquipedSword() {
		return equipedSword;
	}
	
	public Ring getEquipedRing() {
		return equipedRing;
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public int getCurrentScreen() {
		return currentInvScreen;
	}
	
	public Inventory<Item> getNormalInventory() {
		return normalInventory;
	}
	
	public Inventory<Sword> getSwordInventory() {
		return swordInventory;
	}
	
	public Inventory<Potion> getPotionInventory() {
		return potionInventory;
	}
	
	public CollectibleInventory getCollectiblesInventory() {
		return collectiblesInventory;
	}
	
	public InventoryRing getRingInventory() {
		return ringInventory;
	}
	
	public EnumSelectables getSelectedThing() {
		return selectedThing;
	}
	
	public enum EnumSelectables {
		Map,
		NormalInventory,
		SwordInventory,
		RingInventory,
		PotionInventory,
		SpecialInventory,
		CollectiblesInventory;
	}
}
