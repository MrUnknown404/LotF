package main.java.lotf.items.util;

import main.java.lotf.init.InitCollectibles;
import main.java.lotf.util.Console;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public class Collectible {
	
	private final LangKey langKey;
	private final String name, description;
	private int amount;
	private boolean has;
	
	public Collectible(String key) {
		langKey = new LangKey(LangType.collectible, key, LangKeyType.name, LangKeyType.desc);
		Console.print(Console.WarningType.RegisterDebug, langKey.getKey() + " was registered!");
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
		
		InitCollectibles.COLLECTIBLES.add(this);
	}
	
	public Collectible setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public Collectible setHas(boolean has) {
		this.has = has;
		return this;
	}
	
	public String getKey() {
		return langKey.getKey();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean has() {
		return has;
	}
}
