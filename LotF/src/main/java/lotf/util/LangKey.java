package main.java.lotf.util;

import java.util.ArrayList;
import java.util.List;

public class LangKey {

	private final LangType langType;
	private final String key;
	private final List<LangKeyType> keyTypes = new ArrayList<LangKeyType>();
	
	public LangKey(LangType langType, String key, LangKeyType... keyTypes) {
		this.langType = langType;
		this.key = key;
		
		for (LangKeyType k : keyTypes) {
			this.keyTypes.add(k);
		}
	}
	
	public LangType getLangType() {
		return langType;
	}
	
	public String getKey() {
		return key;
	}
	
	public List<LangKeyType> getKeyTypes() {
		return keyTypes;
	}
	
	public enum LangType {
		item,
		ring,
		collectible,
		npc,
		gui,
		command;
	}
	
	public enum LangKeyType {
		name,
		desc,
		dialogue;
	}
}
