package main.java.lotf.entities.ai;

import main.java.lotf.entities.util.Entity;

public abstract class AIType {
	protected final Entity parent;
	
	public AIType(Entity parent) {
		this.parent = parent;
	}
	
	public abstract void attemptAction();
	protected abstract void action();
}
