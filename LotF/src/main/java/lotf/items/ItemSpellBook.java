package main.java.lotf.items;

import main.java.lotf.items.spellPages.util.SpellPageList;
import main.java.lotf.items.util.Item;

public class ItemSpellBook extends Item {

	private SpellPageList pageList = new SpellPageList();
	public boolean side;
	
	public ItemSpellBook() {
		super("spellBook", 0);
	}
	
	@Override
	protected void onUse() {
		pageList.getSelectedPage().use();
	}
	
	public SpellPageList getSpellPageList() {
		return pageList;
	}
}
