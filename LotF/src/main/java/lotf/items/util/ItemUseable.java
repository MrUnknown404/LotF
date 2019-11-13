package main.java.lotf.items.util;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.ITickable;

public abstract class ItemUseable extends ItemBase implements ITickable {

	private final int maxCooldown, useStallCooldown;
	private int cooldown;
	private final boolean shouldStallPlayer;
	private boolean canUse;
	
	public ItemUseable(ItemInfo info, int useStallCooldown, boolean shouldStallPlayer, int maxCooldown) {
		super(info);
		this.shouldStallPlayer = shouldStallPlayer;
		this.useStallCooldown = useStallCooldown;
		this.maxCooldown = maxCooldown;
	}
	
	@Override
	public void tick() {
		if (maxCooldown != 0 && !canUse) {
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
			
			if (shouldStallPlayer) {
				user.setUsingItem(useStallCooldown, true);
			}
		}
	}
	
	protected abstract void onUse(EntityPlayer user);
}
