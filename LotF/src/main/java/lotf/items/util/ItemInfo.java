package main.java.lotf.items.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.items.rings.Ring;
import main.java.lotf.items.rings.Ring.RingType;
import main.java.lotf.items.rings.RingArcher1;
import main.java.lotf.items.rings.RingBasic;
import main.java.lotf.util.DoubleValue;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public enum ItemInfo {
	item_bow(new LangKey(LangType.item, "bow", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("maxCooldown", 20), new DoubleValue<String, Object>("damage", 1))),
	item_cape(new LangKey(LangType.item, "cape", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("noCooldown", true))),
	
	sword_starterSword(new LangKey(LangType.item, "starterSword", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("maxCooldown", 10), new DoubleValue<String, Object>("damage", 1))),
	
	ring_basic(RingBasic.class, new LangKey(LangType.ring, "basic", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("ringType", RingType.passive))),
	ring_archer1(RingArcher1.class, new LangKey(LangType.ring, "archer1", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("ringType", RingType.passive))),
	
	collectible_blueFly(new LangKey(LangType.collectible, "blueFly", LangKeyType.name, LangKeyType.desc)),
	collectible_redFly(new LangKey(LangType.collectible, "redFly", LangKeyType.name, LangKeyType.desc)),
	collectible_greenFly(new LangKey(LangType.collectible, "greenFly", LangKeyType.name, LangKeyType.desc)),
	collectible_spikyBeetle(new LangKey(LangType.collectible, "spikyBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_lavaBeetle(new LangKey(LangType.collectible, "lavaBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_butterfly(new LangKey(LangType.collectible, "butterfly", LangKeyType.name, LangKeyType.desc));
	
	private final Class<? extends Ring> clazz;
	private final LangKey langKey;
	private final String name, description;
	private Map<String, Object> data = new HashMap<String, Object>();
	
	private ItemInfo(Class<? extends Ring> clazz, LangKey langKey, List<DoubleValue<String, Object>> data) {
		this.clazz = clazz;
		this.langKey = langKey;
		
		if (data == null) {
			this.data = null;
		} else {
			for (DoubleValue<String, Object> dv : data) {
				this.data.put(dv.getLeft(), dv.getRight());
			}
		}
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
	}
	
	private ItemInfo(LangKey langKey, List<DoubleValue<String, Object>> data) {
		this(null, langKey, data);
	}
	
	private ItemInfo(LangKey langKey) {
		this(null, langKey, null);
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
	
	public Map<String, Object> getData() {
		return data;
	}
}
