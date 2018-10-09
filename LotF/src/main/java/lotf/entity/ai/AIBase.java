package main.java.lotf.entity.ai;

import main.java.lotf.entity.EntityMonster;

public abstract class AIBase {

	public EntityMonster e;
	
	public abstract void tick();
	
	public void setEntity(EntityMonster e) {
		this.e = e;
	}
}
