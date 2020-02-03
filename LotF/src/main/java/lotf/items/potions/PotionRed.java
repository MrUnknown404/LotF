package main.java.lotf.items.potions;

public class PotionRed extends Potion {
	
	public PotionRed() {
		super("red");
	}
	
	@Override
	protected void onDrink() {
		System.out.println("Red use");
	}
}
