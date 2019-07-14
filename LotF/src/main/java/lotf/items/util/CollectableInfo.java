package main.java.lotf.items.util;

public class CollectableInfo {

	private final ItemInfo info;
	private int amount;
	private boolean has;
	
	public CollectableInfo(ItemInfo info, boolean has, int amount) {
		this.info = info;
		this.has = has;
		this.amount = amount;
	}
	
	public CollectableInfo setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public CollectableInfo setHas(boolean has) {
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
