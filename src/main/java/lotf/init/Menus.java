package main.java.lotf.init;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.client.ui.Menu;
import main.java.lotf.client.ui.MenuMain;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Console.WarningType;

public class Menus {
	private static final List<Menu> ALL = new ArrayList<Menu>();
	
	public static final Menu MAIN = new MenuMain();
	
	public static List<Menu> getAll() {
		return ALL;
	}
	
	public static void add(Menu m) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + Menus.class.getSimpleName() + "!");
		}
		
		ALL.add(m);
		Console.print(Console.WarningType.RegisterDebug, "'" + m.getClass().getSimpleName() + "' was registered!");
	}
	
	/** Forces an early load */
	public static void registerAll() {
	}
}
