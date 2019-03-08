package main.java.lotf.inventory;

import main.java.lotf.Main;
import main.java.lotf.init.InitItems;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.EnumDungeonType;

public class PlayerInventory extends Inventory {
	
	public transient boolean isInventoryOpen = false, isSelectingPage = false;
	
	private Inventory swords = new Inventory(1, 5);
	private Inventory rings = new Inventory(1, 5);
	
	private Inventory hiddenItems = new Inventory(32, 32);
	private Inventory hiddenRings = new Inventory(32, 32);
	
	private int selectedSword = -1, selectedRing = -1, selectedSlot, selectedInv, selectedScreen;
	private Item selectedLeft = InitItems.EMPTY, selectedRight = InitItems.EMPTY;
	
	public PlayerInventory() {
		super(5, 5);
		
		//delete this
		addItem(InitItems.get("bombBag", 0));
		addItem(InitItems.get("boomerang", 0));
		addItem(InitItems.get("bottle", 0));
		addItem(InitItems.get("bottle", 1));
		addItem(InitItems.get("bottle", 2));
		addItem(InitItems.get("bottle", 3));
		addItem(InitItems.get("bottle", 4));
		addItem(InitItems.get("bottle", 5));
		addItem(InitItems.get("bow", 0));
		addItem(InitItems.get("cape", 0));
		addItem(InitItems.get("clock", 0));
		addItem(InitItems.get("flippers", 0));
		addItem(InitItems.get("lavaFlippers", 0));
		addItem(InitItems.get("graplingHook", 0));
		addItem(InitItems.get("hammer", 0));
		addItem(InitItems.get("hermesBoots", 0));
		addItem(InitItems.get("rcBombBag", 0));
		addItem(InitItems.get("spellBook", 0));
		swords.addItem(InitItems.get("sword", 0));
		swords.addItem(InitItems.get("sword", 1));
		swords.addItem(InitItems.get("sword", 2));
		swords.addItem(InitItems.get("sword", 3));
		swords.addItem(InitItems.get("sword", 4));
		((ItemSpellBook) findItem("spellBook", 0)).getSpellPageList().addSpell(1);
		((ItemSpellBook) findItem("spellBook", 0)).getSpellPageList().addSpell(2);
		((ItemSpellBook) findItem("spellBook", 0)).getSpellPageList().addSpell(3);
		((ItemSpellBook) findItem("spellBook", 0)).getSpellPageList().addSpell(4);
		//*/
	}
	
	@Override
	public void addItem(Item item) {
		if (item.getInventoryType() == Item.InventoryType.normal) {
			super.addItem(item);
		} else if (item.getInventoryType() == Item.InventoryType.sword) {
			swords.addItem(item);
		} else if (item.getInventoryType() == Item.InventoryType.ringID) {
			rings.addItem(item);
		} else if (item.getInventoryType() == Item.InventoryType.ringUnID) {
			hiddenRings.addItem(item);
		} else if (item.getInventoryType() == Item.InventoryType.hidden) {
			hiddenItems.addItem(item);
		}
	}
	
	@Override
	public Item findItem(String str, int meta) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(str) && items.get(i).getMeta() == meta) {
				return items.get(i);
			}
		}
		
		for (int i = 0; i < swords.getItems().size(); i++) {
			if (swords.getItems().get(i).getName().equals(str) && swords.getItems().get(i).getMeta() == meta) {
				return swords.getItems().get(i);
			}
		}
		
		for (int i = 0; i < rings.getItems().size(); i++) {
			if (rings.getItems().get(i).getName().equals(str) && rings.getItems().get(i).getMeta() == meta) {
				return rings.getItems().get(i);
			}
		}
		
		for (int i = 0; i < hiddenRings.getItems().size(); i++) {
			if (hiddenRings.getItems().get(i).getName().equals(str) && hiddenRings.getItems().get(i).getMeta() == meta) {
				return hiddenRings.getItems().get(i);
			}
		}
		
		for (int i = 0; i < hiddenItems.getItems().size(); i++) {
			if (hiddenItems.getItems().get(i).getName().equals(str) && hiddenItems.getItems().get(i).getMeta() == meta) {
				return hiddenItems.getItems().get(i);
			}
		}
		
		if (getSelectedLeft().getName().equals(str) && getSelectedLeft().getMeta() == meta) {
			return getSelectedLeft();
		} else if (getSelectedRight().getName().equals(str) && getSelectedRight().getMeta() == meta) {
			return getSelectedRight();
		}
		
		return null;
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
	
	public void changeSelectedScreen() {
		if (selectedScreen == 1) {
			selectedScreen = 0;
		} else {
			selectedScreen = 1;
		}
	}
	
	public boolean hasDungeonItem(EnumDungeonType type) {
		if (hiddenItems.findItem("dungeonItem", type.fId) != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasMap() {
		if (hiddenItems.findItem("map", Main.getWorldHandler().getPlayerWorld().getDungeonType().fId) != null) {
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
		if (hiddenItems.findItem("compass", Main.getWorldHandler().getPlayerWorld().getDungeonType().fId) != null) {
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
	
	public int getSelectedScreen() {
		return selectedScreen;
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
	
	public Inventory getHiddenRings() {
		return hiddenRings;
	}
}
