package main.java.lotf.items.potions;

import main.java.lotf.items.util.Item;
import main.java.lotf.util.LangKey.LangType;

public abstract class Potion extends Item {
	
	public Potion(String key) {
		super(LangType.potion, key);
	}
	
	public void drink() {
		//TODO set this to empty
		onDrink();
	}
	
	protected abstract void onDrink();
}
