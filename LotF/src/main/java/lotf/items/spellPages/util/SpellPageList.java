package main.java.lotf.items.spellPages.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.items.spellPages.SpellPageBuild;
import main.java.lotf.items.spellPages.SpellPageFire;
import main.java.lotf.items.spellPages.SpellPageIce;
import main.java.lotf.items.spellPages.SpellPageMagnet;
import main.java.lotf.items.spellPages.SpellPageWarp;

public class SpellPageList {

	private List<SpellPage> pages = new ArrayList<SpellPage>(5);
	private List<Boolean> has = new ArrayList<Boolean>(5);
	
	private int selectedPage = 0;
	
	public SpellPageList() {
		pages.add(new SpellPageFire());
		pages.add(new SpellPageWarp());
		pages.add(new SpellPageBuild());
		pages.add(new SpellPageIce());
		pages.add(new SpellPageMagnet());
		
		for (int i = 0; i < 5; i++) {
			has.add(false);
		}
		has.set(0, true);
	}
	
	public void addSpell(int spell) {
		has.set(spell, true);
	}
	
	public void rightSelectedPage() {
		boolean tb = false;
		for (int i = selectedPage + 1; i < 5; i++) {
			if (has.get(i)) {
				selectedPage = i;
				tb = true;
				break;
			}
		}
		
		if (!tb) {
			selectedPage = 0;
		}
	}
	
	public void leftSelectedPage() {
		if (selectedPage == 0) {
			for (int i = 4; i >= 0; i--) {
				if (has.get(i)) {
					selectedPage = i;
					break;
				}
			}
			return;
		}
		
		for (int i = selectedPage - 1; i >= 0; i--) {
			if (has.get(i)) {
				selectedPage = i;
				break;
			}
		}
	}
	
	public int getAmountOfPages() {
		int p = 0;
		for (int i = 0; i < has.size(); i++) {
			if (has.get(i)) {
				p++;
			}
		}
		return p;
	}
	
	public int getSelectedPageInt() {
		return selectedPage;
	}
	
	public SpellPage getSelectedPage() {
		return pages.get(selectedPage);
	}
	
	public List<Boolean> getHas() {
		return has;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		b.append("(");
		for (int i = 0; i < 5; i++) {
			if (i != 4) {
				b.append(pages.get(i).getMeta() + ":" + has.get(i) + ", ");
			} else {
				b.append(pages.get(i).getMeta() + ":" + has.get(i));
			}
		}
		b.append(")");
		
		return b.toString();
	}
}
