package main.java.lotf.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entities.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.enums.EnumDirection;
import main.java.lotf.util.math.MathH;

public class KeyHandler extends KeyAdapter implements ITickable {

	public Map<KeyType, Boolean> dualKeys = new HashMap<KeyType, Boolean>();
	
	public KeyHandler registerKeys() {
		for (KeyType type : KeyType.values()) {
			type.registerKey(type.defaultKey, -1); //TODO load from config
			
			if (type.hasDualAction) {
				dualKeys.put(type, false);
			}
		}
		
		return this;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (checkKey(KeyType.debug_fullscreen, key)) {
			Window.toggleFullscreen();
		}
		
		handleConsole(key, e.getKeyChar());
		if (Main.getMain().getWorldHandler() != null && Main.getMain().getWorldHandler().getPlayer() != null) {
			if (!Main.getMain().getCommandConsole().isConsoleOpen()) {
				handlePlayer(key, true);
			}
		} else {
			
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (Main.getMain().getWorldHandler() != null && Main.getMain().getWorldHandler().getPlayer() != null) {
			handlePlayer(key, false);
		} else {
			
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
			}
		}
		
		if (checkKey(KeyType.player_inventory_up, key) && isPressed) {
			Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.north);
		} else if (checkKey(KeyType.player_inventory_down, key) && isPressed) {
			Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.south);
		} else if (checkKey(KeyType.player_inventory_left, key) && isPressed) {
			Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.west);
		} else if (checkKey(KeyType.player_inventory_right, key) && isPressed) {
			Main.getMain().getWorldHandler().getPlayer().getInventory().moveSelectedInvSlot(EnumDirection.east);
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
				Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
			} else if (checkKey(KeyType.console_cancel, key)) {
				console.clearInput();
				console.setConsoleOpen(false);
				console.setCurLine(0);
				Main.getMain().setGamestate(getClass(), Main.Gamestate.run);
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
				Main.getMain().setGamestate(getClass(), Main.Gamestate.softPause);
			} else if (checkKey(KeyType.console_open_slash, key)) {
				console.setConsoleOpen(true);
				console.addKey(chr);
				Main.getMain().setGamestate(getClass(), Main.Gamestate.softPause);
			}
		}
	}
	
	private boolean checkKey(KeyType type, int keyCode) {
		return (type.key1 == keyCode || type.key2 == keyCode) ? true : false;
	}
	
	public enum KeyType {
		debug_fullscreen       (false, KeyEvent.VK_F11),
		
		console_open           (false, KeyEvent.VK_BACK_QUOTE),
		console_open_slash     (false, KeyEvent.VK_SLASH),
		console_up             (false, KeyEvent.VK_UP),
		console_down           (false, KeyEvent.VK_DOWN),
		console_finish         (false, KeyEvent.VK_ENTER),
		console_cancel         (false, KeyEvent.VK_ESCAPE),
		console_delete         (false, KeyEvent.VK_BACK_SPACE),
		
		player_walk_up         (true,  KeyEvent.VK_W),
		player_walk_down       (true,  KeyEvent.VK_S),
		player_walk_left       (true,  KeyEvent.VK_A),
		player_walk_right      (true,  KeyEvent.VK_D),
		player_inventory_toggle(true,  KeyEvent.VK_DOWN),
		player_inventory_switch(true,  KeyEvent.VK_UP),
		player_inventory_up    (false, KeyEvent.VK_W),
		player_inventory_down  (false, KeyEvent.VK_S),
		player_inventory_left  (false, KeyEvent.VK_A),
		player_inventory_right (false, KeyEvent.VK_D);
		
		private int defaultKey;
		private boolean hasDualAction;
		private int key1, key2;
		
		private KeyType(boolean hasDualAction, int defaultKey) {
			this.hasDualAction = hasDualAction;
			this.defaultKey = defaultKey;
		}
		
		void registerKey(int key1, int key2) {
			Console.print(Console.WarningType.RegisterDebug, this.toString() + " has been registered to -> (" + KeyEvent.getKeyText(key1) +
					":" + KeyEvent.getKeyText(key2) + " | " + key1 + ":" + key2 + ")");
			this.key1 = key1;
			this.key2 = key2;
		}
	}
}
