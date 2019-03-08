package main.java.lotf.items.util;

public abstract class Item {
	
	protected String name;
	protected Ammo ammo;
	protected int meta, maxMeta;
	protected boolean useAmmo;
	protected InventoryType invType;
	
	public Item(String name, Ammo ammo, int meta, int maxMeta, InventoryType invType) {
		this.name = name;
		this.ammo = ammo;
		this.useAmmo = true;
		this.meta = meta;
		this.maxMeta = maxMeta;
		this.invType = invType;
	}
	
	public Item(String name, int meta, int maxMeta, InventoryType invType) {
		this.name = name;
		this.meta = meta;
		this.maxMeta = maxMeta;
		this.invType = invType;
	}
	
	public Item(String name, InventoryType invType) {
		this.name = name;
		this.invType = invType;
	}
	
	public void use() {
		System.err.println(name + ":" + meta + " was used (Item class)");
		onUse();
	}
	
	protected abstract void onUse();
	
	public InventoryType getInventoryType() {
		return invType;
	}
	
	public int getMaxMeta() {
		return maxMeta;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public boolean getUseAmmo() {
		return useAmmo;
	}
	
	public Ammo getAmmo() {
		return ammo;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			if (((Item) obj).name.equals(name) && ((Item) obj).meta == meta) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + name + ", " + meta + ")";
	}
	
	public enum InventoryType {
		normal,
		sword,
		ringID,
		ringUnID,
		hidden;
	}
}
