package main.java.lotf.items.util;

import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public enum ItemInfo {
	item_bow(new LangKey(LangType.item, "bow", LangKeyType.name, LangKeyType.desc)),
	item_cape(new LangKey(LangType.item, "cape", LangKeyType.name, LangKeyType.desc)),
	
	sword_starterSword(new LangKey(LangType.item, "starterSword", LangKeyType.name, LangKeyType.desc)),
	
	ring_basic(RingBasic.class, new LangKey(LangType.ring, "basic", LangKeyType.name, LangKeyType.desc)),
	ring_archer1(RingArcher1.class, new LangKey(LangType.ring, "archer1", LangKeyType.name, LangKeyType.desc)),
	
	collectible_blueFly(new LangKey(LangType.collectible, "blueFly", LangKeyType.name, LangKeyType.desc)),
	collectible_redFly(new LangKey(LangType.collectible, "redFly", LangKeyType.name, LangKeyType.desc)),
	collectible_greenFly(new LangKey(LangType.collectible, "greenFly", LangKeyType.name, LangKeyType.desc)),
	collectible_spikyBeetle(new LangKey(LangType.collectible, "spikyBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_lavaBeetle(new LangKey(LangType.collectible, "lavaBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_butterfly(new LangKey(LangType.collectible, "butterfly", LangKeyType.name, LangKeyType.desc));
	
	private final Class<? extends Ring> clazz;
	private final LangKey langKey;
	private final String name, description;
	
	private ItemInfo(Class<? extends Ring> clazz, LangKey langKey) {
		this.clazz = clazz;
		this.langKey = langKey;
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
	}
	
	private ItemInfo(LangKey langKey) {
		this(null, langKey);
	}
	
	public static ItemInfo find(String name) {
		for (ItemInfo info : values()) {
			if (info.toString().equalsIgnoreCase(name)) {
				return info;
			}
		}
		
		return null;
	}
	
	public Class<? extends Ring> getRingClazz() {
		return clazz;
	}
	
	public LangKey getLangKey() {
		return langKey;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
