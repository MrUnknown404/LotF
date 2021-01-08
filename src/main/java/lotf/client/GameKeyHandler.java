package main.java.lotf.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.reflect.TypeToken;

import main.java.lotf.Main;
import main.java.lotf.client.GameKeyHandler.KeyType;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.IConfigurable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.ulibs.utils.Console;
import main.java.ulibs.utils.Pair;
import main.java.ulibs.utils.math.MathH;

public class GameKeyHandler extends KeyAdapter implements ITickable, IConfigurable<Map<KeyType, Pair<Integer, Integer>>> {

	public Map<KeyType, Boolean> dualKeys = new HashMap<KeyType, Boolean>();
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (checkKey(KeyType.debug_fullscreen, key)) {
			Window.toggleFullscreen();
		}
		
		if (Main.isDebug) {
			handleConsole(key, e.getKeyChar());
		}
		
		if (Main.getMain().getWorldHandler() != null && Main.getMain().getWorldHandler().getPlayer() != null) {
			if (!Main.getMain().getCommandConsole().isConsoleOpen()) {
				handlePlayer(key, true);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (Main.getMain().getWorldHandler() != null && Main.getMain().getWorldHandler().getPlayer() != null) {
			handlePlayer(key, false);
		}
	}
	
	private void handlePlayer(int key, boolean isPressed) {
		for (Entry<KeyType, Boolean> dualKey : dualKeys.entrySet()) {
			if (dualKey.getKey() == KeyType.player_walk_up && checkKey(dualKey.getKey(), key)) {
				dualKey.setValue(isPressed);
			} else if (dualKey.getKey() == KeyType.player_walk_down && checkKey(dualKey.getKey(), key)) {
				dualKey.setValue(isPressed);
			} else if (dualKey.getKey() == KeyType.player_walk_left && checkKey(dualKey.getKey(), key)) {
				dualKey.setValue(isPressed);
			} else if (dualKey.getKey() == KeyType.player_walk_right && checkKey(dualKey.getKey(), key)) {
				dualKey.setValue(isPressed);
			} else if (dualKey.getKey() == KeyType.player_inventory_toggle && checkKey(dualKey.getKey(), key)) {
				if (isPressed && !dualKey.getValue()) {
					Main.getMain().getWorldHandler().getPlayer().getInventory().toggleInventory();
				}
				dualKey.setValue(isPressed);
			}
			
			if (Main.getMain().getWorldHandler().getPlayer().getInventory().isOpen()) {
				if (dualKey.getKey() == KeyType.player_inventory_switch && checkKey(dualKey.getKey(), key)) {
					if (isPressed && !dualKey.getValue()) {
						Main.getMain().getWorldHandler().getPlayer().getInventory().switchInventoryScreen();
					}
					dualKey.setValue(isPressed);
				}
			} else {
				if (dualKey.getKey() == KeyType.player_use_left && checkKey(dualKey.getKey(), key)) {
					if (isPressed && !dualKey.getValue()) {
						Main.getMain().getWorldHandler().getPlayer().useLeftItem();
					}
					dualKey.setValue(isPressed);
				} else if (dualKey.getKey() == KeyType.player_use_right && checkKey(dualKey.getKey(), key)) {
					if (isPressed && !dualKey.getValue()) {
						Main.getMain().getWorldHandler().getPlayer().useRightItem();
					}
					dualKey.setValue(isPressed);
				} else if (dualKey.getKey() == KeyType.player_use_sword && checkKey(dualKey.getKey(), key)) {
					if (isPressed && !dualKey.getValue()) {
						Main.getMain().getWorldHandler().getPlayer().useSword();
					}
					dualKey.setValue(isPressed);
				}
			}
		}
		
		if (Main.getMain().getWorldHandler().getPlayer().getInventory().isOpen()) {
			if (checkKey(KeyType.player_inventory_up, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.north);
			} else if (checkKey(KeyType.player_inventory_down, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.south);
			} else if (checkKey(KeyType.player_inventory_left, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.west);
			} else if (checkKey(KeyType.player_inventory_right, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.east);
			} else if (checkKey(KeyType.player_inventory_select_left, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().selectLeft();
			} else if (checkKey(KeyType.player_inventory_select_right, key) && isPressed) {
				Main.getMain().getWorldHandler().getPlayer().getInventory().selectRight();
			}
		}
		
		if (!dualKeys.get(KeyType.player_walk_up) && !dualKeys.get(KeyType.player_walk_down)) {
			Main.getMain().getWorldHandler().getPlayer().setMoveY(0);
		}
		
		if (!dualKeys.get(KeyType.player_walk_left) && !dualKeys.get(KeyType.player_walk_right)) {
			Main.getMain().getWorldHandler().getPlayer().setMoveX(0);
		}
	}
	
	@Override
	public void tick() {
		EntityPlayer player = Main.getMain().getWorldHandler().getPlayer();
		
		if (player != null) {
			if (dualKeys.get(KeyType.player_walk_up) && dualKeys.get(KeyType.player_walk_down)) {
				player.setMoveY(0);
			} else if (dualKeys.get(KeyType.player_walk_up)) {
				player.setMoveY(-1);
			} else if (dualKeys.get(KeyType.player_walk_down)) {
				player.setMoveY(1);
			}
			
			if (dualKeys.get(KeyType.player_walk_left) && dualKeys.get(KeyType.player_walk_right)) {
				player.setMoveX(0);
			} else if (dualKeys.get(KeyType.player_walk_left)) {
				player.setMoveX(-1);
			} else if (dualKeys.get(KeyType.player_walk_right)) {
				player.setMoveX(1);
			}
		}
	}
	
	private void handleConsole(int key, char chr) {
		DebugConsole console = Main.getMain().getCommandConsole();
		
		if (console.isConsoleOpen()) {
			if (checkKey(KeyType.console_up, key)) {
				if (console.getWrittenLines().size() > 0) {
					console.setCurLine(MathH.clamp(console.getCurLine() + 1, 0, console.getWrittenLines().size() - 1));
					console.setInput(console.getWrittenLines().get(console.getCurLine()));
				}
			} else if (checkKey(KeyType.console_down, key)) {
				if (console.getWrittenLines().size() > 0) {
					console.setCurLine(MathH.clamp(console.getCurLine() - 1, 0, console.getWrittenLines().size() - 1));
					console.setInput(console.getWrittenLines().get(console.getCurLine()));
				}
			} else if (checkKey(KeyType.console_finish, key)) {
				console.finishCommand();
				Main.getMain().setGamestate(console.getClass(), Main.Gamestate.run);
			} else if (checkKey(KeyType.console_cancel, key)) {
				console.clearInput();
				console.setConsoleOpen(false);
				console.setCurLine(0);
				Main.getMain().setGamestate(console.getClass(), Main.Gamestate.run);
			} else if (checkKey(KeyType.console_delete, key)) {
				if (!console.getInput().isEmpty()) {
					console.removeKey();
				}
			} else {
				if (chr != '\uFFFF' && key != KeyEvent.VK_DELETE) {
					console.addKey(chr);
				}
			}
		} else {
			if (checkKey(KeyType.console_open, key)) {
				console.setConsoleOpen(true);
				Main.getMain().setGamestate(console.getClass(), Main.Gamestate.hardPause);
			} else if (checkKey(KeyType.console_open_slash, key)) {
				console.setConsoleOpen(true);
				console.addKey(chr);
				Main.getMain().setGamestate(console.getClass(), Main.Gamestate.hardPause);
			}
		}
	}
	
	private boolean checkKey(KeyType type, int keyCode) {
		return (type.key1 == keyCode || type.key2 == keyCode) ? true : false;
	}
	
	@Override
	public String getFileName() {
		return "KeyConfig";
	}
	
	@Override
	public Map<KeyType, Pair<Integer, Integer>> save() {
		Map<KeyType, Pair<Integer, Integer>> keys = new HashMap<KeyType, Pair<Integer, Integer>>();
		
		for (KeyType type : KeyType.values()) {
			keys.put(type, new Pair<Integer, Integer>(type.key1, type.key2));
		}
		
		return keys;
	}
	
	@Override
	public Map<KeyType, Pair<Integer, Integer>> getDefaultSave() {
		Map<KeyType, Pair<Integer, Integer>> keys = new HashMap<KeyType, Pair<Integer, Integer>>();
		
		for (KeyType type : KeyType.values()) {
			keys.put(type, new Pair<Integer, Integer>(type.defaultKey, -1));
		}
		
		return keys;
	}
	
	@Override
	public void load(Object obj) {
		@SuppressWarnings("unchecked")
		Map<KeyType, Pair<Integer, Integer>> keys = (Map<KeyType, Pair<Integer, Integer>>) obj;
		
		for (Entry<KeyType, Pair<Integer, Integer>> type : keys.entrySet()) {
			type.getKey().registerKey(type.getValue().getL(), type.getValue().getR());
			
			if (type.getKey().hasDualAction) {
				dualKeys.put(type.getKey(), false);
			}
		}
	}
	
	@Override
	public Type getType() {
		return new TypeToken<Map<KeyType, Pair<Integer, Integer>>>(){}.getType();
	}
	
	public enum KeyType {
		debug_fullscreen             (false, KeyEvent.VK_F11),
		
		console_open                 (false, KeyEvent.VK_BACK_QUOTE),
		console_open_slash           (false, KeyEvent.VK_SLASH),
		console_up                   (false, KeyEvent.VK_UP),
		console_down                 (false, KeyEvent.VK_DOWN),
		console_finish               (false, KeyEvent.VK_ENTER),
		console_cancel               (false, KeyEvent.VK_ESCAPE),
		console_delete               (false, KeyEvent.VK_BACK_SPACE),
		
		player_walk_up               (true,  KeyEvent.VK_W),
		player_walk_down             (true,  KeyEvent.VK_S),
		player_walk_left             (true,  KeyEvent.VK_A),
		player_walk_right            (true,  KeyEvent.VK_D),
		
		player_use_left              (true,  KeyEvent.VK_LEFT),
		player_use_right             (true,  KeyEvent.VK_RIGHT),
		player_use_sword             (true,  KeyEvent.VK_UP),
		
		player_inventory_toggle      (true,  KeyEvent.VK_DOWN),
		player_inventory_switch      (true,  KeyEvent.VK_UP),
		player_inventory_up          (false, KeyEvent.VK_W),
		player_inventory_down        (false, KeyEvent.VK_S),
		player_inventory_left        (false, KeyEvent.VK_A),
		player_inventory_right       (false, KeyEvent.VK_D),
		player_inventory_select_left (false, KeyEvent.VK_LEFT),
		player_inventory_select_right(false, KeyEvent.VK_RIGHT);
		
		private int defaultKey;
		private boolean hasDualAction;
		private int key1, key2;
		
		private KeyType(boolean hasDualAction, int defaultKey) {
			this.hasDualAction = hasDualAction;
			this.defaultKey = defaultKey;
		}
		
		private void registerKey(int key1, int key2) {
			Console.print(Console.WarningType.RegisterDebug, "'" + toString() + "' has been registered to -> (" + KeyEvent.getKeyText(key1) +
					":" + KeyEvent.getKeyText(key2) + " | " + key1 + ":" + key2 + ")");
			this.key1 = key1;
			this.key2 = key2;
		}
		
		public int getDefaultKey() {
			return defaultKey;
		}
		
		public boolean hasDualAction() {
			return hasDualAction;
		}
	}
}
