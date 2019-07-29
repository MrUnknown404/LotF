package main.java.lotf.items.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.lotf.items.rings.Ring.RingType;
import main.java.lotf.util.DoubleValue;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.LangKey;
import main.java.lotf.util.LangKey.LangKeyType;
import main.java.lotf.util.LangKey.LangType;

public enum ItemInfo {
	item_bow(new LangKey(LangType.item, "bow", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("maxCooldown", 60), new DoubleValue<String, Object>("damage", 1))),
	item_cape(new LangKey(LangType.item, "cape", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("noCooldown", true))),
	
	sword_starterSword(new LangKey(LangType.item, "starterSword", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("maxCooldown", 10), new DoubleValue<String, Object>("damage", 1))),
	
	ring_basic(new LangKey(LangType.ring, "basic", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("ringType", RingType.passive))),
	ring_archer1(new LangKey(LangType.ring, "archer1", LangKeyType.name, LangKeyType.desc),
			Arrays.asList(new DoubleValue<String, Object>("ringType", RingType.passive))),
	
	collectible_blueFly(new LangKey(LangType.collectible, "blueFly", LangKeyType.name, LangKeyType.desc)),
	collectible_redFly(new LangKey(LangType.collectible, "redFly", LangKeyType.name, LangKeyType.desc)),
	collectible_greenFly(new LangKey(LangType.collectible, "greenFly", LangKeyType.name, LangKeyType.desc)),
	collectible_spikyBeetle(new LangKey(LangType.collectible, "spikyBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_lavaBeetle(new LangKey(LangType.collectible, "lavaBeetle", LangKeyType.name, LangKeyType.desc)),
	collectible_butterfly(new LangKey(LangType.collectible, "butterfly", LangKeyType.name, LangKeyType.desc));
	
	protected final LangKey langKey;
	protected final String name, description;
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	private ItemInfo(LangKey langKey, List<DoubleValue<String, Object>> data) {
		this.langKey = langKey;
		for (DoubleValue<String, Object> dv : data) {
			this.data.put(dv.getLeft(), dv.getRight());
		}
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
	}
	
	private ItemInfo(LangKey langKey) {
		this.langKey = langKey;
		this.data = null;
		
		name = GetResource.getStringFromLangKey(langKey, LangKeyType.name);
		description = GetResource.getStringFromLangKey(langKey, LangKeyType.desc);
	}
	
	public static ItemInfo find(String name) {
		for (ItemInfo info : values()) {
			if (info.toString().equalsIgnoreCase(name)) {
				return info;
			}
		}
		
		return null;
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
