package main.java.lotf.items.util;

import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.ITickable;

public abstract class ItemUseable extends ItemBase implements ITickable {

	private final int maxCooldown, useStallCooldown;
	private int cooldown;
	private boolean canUse, shouldStallPlayer;
	
	public ItemUseable(ItemInfo info, int useStallCooldown, boolean shouldStallPlayer) {
		super(info);
		this.shouldStallPlayer = shouldStallPlayer;
		this.useStallCooldown = useStallCooldown;
		
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
			
			if (shouldStallPlayer) {
				user.setUsingItem(useStallCooldown, true);
			}
		}
	}
	
	protected abstract void onUse(EntityPlayer user);
}
