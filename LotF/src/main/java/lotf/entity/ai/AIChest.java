package main.java.lotf.entity.ai;

import main.java.lotf.items.util.Item;

public class AIChest extends AINPCBase {

	private Item item;
	
	public void open() {
		e.setMeta(1);
	}
	
	@Override public void tick() {}
	@Override protected void move() {}
	
	public Item getItem() {
		return item;
	}
}
