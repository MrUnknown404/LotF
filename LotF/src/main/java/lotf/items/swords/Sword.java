package main.java.lotf.items.swords;

import main.java.lotf.items.util.ItemUseable;
import main.java.lotf.util.LangKey.LangType;

public abstract class Sword extends ItemUseable {

	protected final int damage;
	
	protected Sword(String key, int useStallCooldown, boolean shouldStallPlayer, int maxCooldown, int damage) {
		super(LangType.sword, key, useStallCooldown, shouldStallPlayer, maxCooldown);
		this.damage = damage;
	}
}
