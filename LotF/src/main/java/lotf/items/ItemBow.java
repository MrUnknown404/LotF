package main.java.lotf.items;

import main.java.lotf.Main;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.entities.projectiles.ProjectileArrow;
import main.java.lotf.items.util.ItemUseable;
import main.java.lotf.util.LangKey.LangType;
import main.java.lotf.util.math.Vec2f;

public class ItemBow extends ItemUseable {

	protected int damage = 3;
	
	public ItemBow() {
		super(LangType.item, "bow", 10, true, 20);
	}
	
	@Override
	public void onUse(EntityPlayer user) {
		Vec2f pos = new Vec2f(user.getPosX() - user.getRoom().getPosX(), user.getPosY() - user.getRoom().getPosY());
		switch (user.getFacing()) {
			case east:
				pos.addX(user.getWidth());
				pos.addY(user.getHeight() / 2 - 1);
				break;
			case north:
				pos.addX(user.getWidth() / 2 - 1);
				pos.addY(-8);
				break;
			case south:
				pos.addX(user.getWidth() / 2 - 1);
				pos.addY(user.getHeight());
				break;
			case west:
				pos.addX(-8);
				pos.addY(user.getHeight() / 2 - 1);
				break;
		}
		
		Main.getMain().getWorldHandler().spawnEntity(new ProjectileArrow(pos, user, user.getFacing(), 5, damage));
	}
}
