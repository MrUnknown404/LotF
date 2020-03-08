package main.java.lotf.entities.ai;

import main.java.lotf.entities.util.Entity;

public interface AIType<T extends Entity> {
	public boolean attemptAction();
	public void action();
	public void reset();
}
