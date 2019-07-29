package main.java.lotf.items.util;

public class CollectibleInfo {

	private final ItemInfo info;
	private int amount;
	private boolean has;
	
	public CollectibleInfo(ItemInfo info, boolean has, int amount) {
		this.info = info;
		this.has = has;
		this.amount = amount;
	}
	
	public CollectibleInfo setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public CollectibleInfo setHas(boolean has) {
		this.has = has;
		return this;
	}
	
	public ItemInfo getInfo() {
		return info;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean has() {
		return has;
	}
}
