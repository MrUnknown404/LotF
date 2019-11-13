package main.java.lotf.entities.ai.util;

import main.java.lotf.entities.util.Entity;

public abstract interface AIType<T extends Entity> {
	
	public abstract boolean attemptAction();
	public abstract void action();
	public abstract T getParent();
	public abstract AiType getAIType();
	
	public enum AiType {
		attack,
		movement,
		spot
	}
}
