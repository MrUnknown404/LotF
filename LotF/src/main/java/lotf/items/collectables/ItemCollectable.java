package main.java.lotf.items.collectables;

import main.java.lotf.items.util.ItemBase;
import main.java.lotf.items.util.ItemInfo;

public class ItemCollectable extends ItemBase {

	public static final int MAX = 999;
	protected int amount;
	
	public ItemCollectable(ItemInfo info) {
		super(info);
	}
	
	public int add(int amount) {
		if (this.amount + amount > MAX) {
			int extra = this.amount + amount - MAX;
			this.amount = MAX;
			return extra;
		} else {
			this.amount += amount;
			return 0;
		}
	}
}
