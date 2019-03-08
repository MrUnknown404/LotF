package main.java.lotf.entity.ai;

import main.java.lotf.entity.EntityNPC;

public abstract class AINPCBase extends AIBase {
	
	public EntityNPC e;
	
	public void setEntity(EntityNPC e) {
		this.e = e;
	}
}
