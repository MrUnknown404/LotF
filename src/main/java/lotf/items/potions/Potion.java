package main.java.lotf.items.potions;

import main.java.lotf.Main;
import main.java.lotf.init.Items;
import main.java.lotf.inventory.Inventory;
import main.java.lotf.items.util.Item;
import main.java.lotf.util.LangKey.LangType;

public abstract class Potion extends Item {
	
	public Potion(String key) {
		super(LangType.potion, key);
	}
	
	public void drink() {
		if (this != Items.EMPTY_POTION) {
			Inventory<Potion> pi = Main.getMain().getWorldHandler().getPlayer().getInventory().getPotionInventory();
			for (int i = 0; i < pi.getItemSize(); i++) {
				Potion p = pi.getItem(i);
				if (p == this) {
					pi.setItem(i, Items.EMPTY_POTION);
					break;
				}
			}
		}
		
		onDrink();
	}
	
	protected abstract void onDrink();
}
