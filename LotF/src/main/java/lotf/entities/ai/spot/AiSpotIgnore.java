package main.java.lotf.entities.ai.spot;

import main.java.lotf.entities.ai.AIType;
import main.java.lotf.entities.util.Entity;

public class AiSpotIgnore<T extends Entity> implements AIType<T> {
	protected T parent;
	
	public AiSpotIgnore(T parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean attemptAction() {
		return true;
	}
	
	@Override
	public void action() {
		
	}
	
	@Override
	public T getParent() {
		return parent;
	}
	
	@Override
	public AiType getAIType() {
		return AiType.spot;
	}
}
