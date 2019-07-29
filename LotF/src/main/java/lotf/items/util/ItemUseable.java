package main.java.lotf.items.util;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.ITickable;

public abstract class ItemUseable extends ItemBase implements ITickable {

	private final int maxCooldown;
	private int cooldown;
	private boolean canUse;
	
	public ItemUseable(ItemInfo info) {
		super(info);
		
		if (!getData().containsKey("noCooldown")) {
			this.maxCooldown = (int) getData().get("maxCooldown");
		} else {
			this.maxCooldown = 0;
		}
	}
	
	@Override
	public void tick() {
		if (!getData().containsKey("noCooldown") && !canUse) {
			if (cooldown == 0) {
				cooldown = maxCooldown;
				canUse = true;
			} else {
				cooldown--;
			}
		}
	}
	
	public void use(EntityPlayer user) {
		if (canUse) {
			canUse = false;
			cooldown = maxCooldown;
			onUse(user);
		}
	}
	
	protected abstract void onUse(EntityPlayer user);
}
