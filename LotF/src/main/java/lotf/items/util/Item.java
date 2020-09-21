package main.java.lotf.items.util;

import main.java.lotf.init.Items;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public abstract class Item {
	
	protected final LangKey langKey;
	private final String name, description;
	
	public Item(LangType keyType, String key) {
		langKey = new LangKey(keyType, key, LangKeyType.name, LangKeyType.desc);
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
		
		Items.add(this);
	}
	
	public String getName() {
		return  name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getKey() {
		return langKey.getKey();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			Item item = (Item) obj;
			if (item.getName() == getName()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
