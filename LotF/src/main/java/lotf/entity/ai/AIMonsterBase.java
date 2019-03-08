package main.java.lotf.entity.ai;

import main.java.lotf.entity.EntityMonster;

public abstract class AIMonsterBase extends AIBase {
	
	public EntityMonster e;
	
	public void setEntity(EntityMonster e) {
		this.e = e;
	}
}
