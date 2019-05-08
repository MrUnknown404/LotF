package main.java.lotf.entity.ai;

import main.java.lotf.entity.util.Entity;

public abstract class AIType {
	protected final Entity parent;
	
	public AIType(Entity parent) {
		this.parent = parent;
	}
	
	public abstract void attemptAction();
	protected abstract void action();
}
