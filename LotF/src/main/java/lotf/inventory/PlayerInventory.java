package main.java.lotf.inventory;

import java.util.Random;

import main.java.lotf.Main;
import main.java.lotf.init.InitItems;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.EnumDungeonType;

public class PlayerInventory extends Inventory {
	
	public boolean isInventoryOpen = false, isSelectingPage = false;
	
	private Inventory swords = new Inventory(1, 5);
	private Inventory rings = new Inventory(1, 5);
	private Inventory hiddenItems = new Inventory(10, 10);
	
	private int selectedSword = -1, selectedRing = -1, selectedSlot, selectedInv;
	private Item selectedLeft = InitItems.EMPTY, selectedRight = InitItems.EMPTY;
	
	public PlayerInventory() {
		super(5, 5);
		
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("compass", new Random().nextInt(EnumDungeonType.values().length)));
		hiddenItems.addItem(InitItems.get("map", new Random().nextInt(EnumDungeonType.values().length)));
		
		hiddenItems.addItem(InitItems.get("dungeonItem", 0));
		
		swords.addItem(InitItems.get("sword", 0));
		
		addItem(InitItems.get("bottle", 0));
		addItem(InitItems.get("bottle", 1));
		addItem(InitItems.get("spellBook", 0));
	}
	
	public void setSelected(boolean left) {
		if (!isSelectingPage) {
			if (selectedInv == 0) {
				if (left) {
					if (items.get(selectedSlot).equals(InitItems.EMPTY)) {
						if (!selectedLeft.equals(InitItems.EMPTY)) {
							items.set(selectedSlot, selectedLeft);
							selectedLeft = InitItems.EMPTY;
						}
					} else {
						Item item = items.get(selectedSlot);
						
						if (item instanceof ItemSpellBook) {
							((ItemSpellBook) item).side = true;
							isSelectingPage = true;
						} else {
							items.set(selectedSlot, selectedLeft);
							selectedLeft = item;
						}
					}
				} else {
					if (items.get(selectedSlot).equals(InitItems.EMPTY)) {
						if (!selectedRight.equals(InitItems.EMPTY)) {
							items.set(selectedSlot, selectedRight);
							selectedRight = InitItems.EMPTY;
						}
					} else {
						Item item = items.get(selectedSlot);
						
						if (item instanceof ItemSpellBook) {
							((ItemSpellBook) item).side = false;
							isSelectingPage = true;
						} else {
							items.set(selectedSlot, selectedRight);
							selectedRight = item;
						}
					}
				}
			} else if (selectedInv == 1) {
				selectedSword = selectedSlot;
			} else if (selectedInv == 2) {
				selectedRing = selectedSlot;
			}
		} else {
			Item item = items.get(selectedSlot);
			
			if (((ItemSpellBook) item).side) {
				items.set(selectedSlot, selectedLeft);
				selectedLeft = item;
			} else {
				items.set(selectedSlot, selectedRight);
				selectedRight = item;
			}
			
			isSelectingPage = false;
		}
	}
	
	public void addUpSlot() {
		if (selectedInv == 0) {
			if (selectedSlot - 5 < 0) {
				selectedSlot += 20;
			} else {
				selectedSlot -= 5;
			}
		} else if (selectedInv == 1) {
			if (selectedSlot == 0) {
				selectedSlot = rings.items.size() - 1;
			} else {
				selectedSlot -= 1;
			}
		} else if (selectedInv == 2) {
			if (selectedSlot == 0) {
				selectedSlot = rings.items.size() - 1;
			} else {
				selectedSlot -= 1;
			}
		}
	}
	
	public void addDownSlot() {
		if (selectedInv == 0) {
			if (selectedSlot + 5 > items.size() - 1) {
				selectedSlot -= 20;
			} else {
				selectedSlot += 5;
			}
		} else if (selectedInv == 1) {
			if (selectedSlot == swords.items.size() - 1) {
				selectedSlot = 0;
			} else {
				selectedSlot += 1;
			}
		} else if (selectedInv == 2) {
			if (selectedSlot == rings.items.size() - 1) {
				selectedSlot = 0;
			} else {
				selectedSlot += 1;
			}
		}
	}
	
	public void addLeftSlot() {
		if (selectedInv == 0) {
			if ((double) selectedSlot / 5 == (int) selectedSlot / 5) { 
				selectedInv = 2;
				selectedSlot /= 5;
			} else {
				selectedSlot--;
			}
		} else if (selectedInv == 1) {
			selectedInv = 0;
			selectedSlot *= 5;
			selectedSlot += 4;
		} else if (selectedInv == 2) {
			selectedInv = 1;
		}
	}
	
	public void addRightSlot() {
		if (selectedInv == 0) {
			if ((double) (selectedSlot + 1) / 5 == (int) (selectedSlot + 1) / 5) {
				selectedInv = 1;
				selectedSlot /= 5;
			} else {
				selectedSlot++;
			}
		} else if (selectedInv == 1) {
			selectedInv = 2;
		} else if (selectedInv == 2) {
			selectedInv = 0;
			selectedSlot *= 5;
		}
	}
	
	public boolean hasDungeonItem(EnumDungeonType type) {
		if (hiddenItems.findItem("dungeonItem", type.fId) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasMap() {
		if (hiddenItems.findItem("map", Main.getRoomHandler().getPlayer().getDungeon().fId) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasMap(EnumDungeonType type) {
		if (hiddenItems.findItem("map", type.fId) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasCompass() {
		if (hiddenItems.findItem("compass", Main.getRoomHandler().getPlayer().getDungeon().fId) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasCompass(EnumDungeonType type) {
		if (hiddenItems.findItem("compass", type.fId) != null) {
			return true;
		}
		return false;
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public int getSelectedInv() {
		return selectedInv;
	}
	
	public Item getSelectedSword() {
		if (selectedSword == -1) {
			return InitItems.EMPTY;
		} else {
			return swords.items.get(selectedSword);
		}
	}
	
	public Item getSelectedRing() {
		if (selectedRing == -1) {
			return InitItems.EMPTY;
		} else {
			return rings.items.get(selectedRing);
		}
	}
	
	public Item getSelectedLeft() {
		return selectedLeft;
	}
	
	public Item getSelectedRight() {
		return selectedRight;
	}
	
	public Inventory getSwordInv() {
		return swords;
	}
	
	public Inventory getRingInv() {
		return rings;
	}
	
	public Inventory getHiddenInv() {
		return hiddenItems;
	}
}
