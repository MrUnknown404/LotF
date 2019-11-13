package main.java.lotf.items.swords;

import main.java.lotf.items.util.ItemInfo;
import main.java.lotf.items.util.ItemUseable;

public abstract class ItemSwordBase extends ItemUseable {

	protected final int damage;
	
	protected ItemSwordBase(ItemInfo info, int useStallCooldown, boolean shouldStallPlayer, int maxCooldown, int damage) {
		super(info, useStallCooldown, shouldStallPlayer, maxCooldown);
		this.damage = damage;
	}
}
