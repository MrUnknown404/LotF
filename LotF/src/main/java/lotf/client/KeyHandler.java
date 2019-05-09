package main.java.lotf.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.MathHelper;

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
		
		handleConsole(key, e.getKeyChar());
		if (Main.getMain().getWorldHandler().getPlayer() != null) {
			if (Main.getMain().shouldPlayerHaveControl()) {
				handlePlayer(key, true);
			}
		} else {
			
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (Main.getMain().getWorldHandler().getPlayer() != null) {
			if (Main.getMain().shouldPlayerHaveControl()) {
				handlePlayer(key, false);
			}
		} else {
			
		}
	}
	
	private void handlePlayer(int key, boolean dualKeyBoolean) {
		if (Main.getMain().shouldPlayerHaveControl()) {
			for (Entry<KeyType, Boolean> dualKey : dualKeys.entrySet()) {
				if (dualKey.getKey() == KeyType.player_walk_up && checkKey(dualKey.getKey(), key)) {
					dualKey.setValue(dualKeyBoolean);
				} else if (dualKey.getKey() == KeyType.player_walk_down && checkKey(dualKey.getKey(), key)) {
					dualKey.setValue(dualKeyBoolean);
				} else if (dualKey.getKey() == KeyType.player_walk_left && checkKey(dualKey.getKey(), key)) {
					dualKey.setValue(dualKeyBoolean);
				} else if (dualKey.getKey() == KeyType.player_walk_right && checkKey(dualKey.getKey(), key)) {
					dualKey.setValue(dualKeyBoolean);
				}
				
				if (!dualKeys.get(KeyType.player_walk_up) && !dualKeys.get(KeyType.player_walk_down)) {
					Main.getMain().getWorldHandler().getPlayer().setMoveY(0);
				}
				
				if (!dualKeys.get(KeyType.player_walk_left) && !dualKeys.get(KeyType.player_walk_right)) {
					Main.getMain().getWorldHandler().getPlayer().setMoveX(0);
				}
			}
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
					console.setCurLine(MathHelper.clamp(console.getCurLine() + 1, 0, console.getWrittenLines().size() - 1));
					console.setInput(console.getWrittenLines().get(console.getCurLine()));
				}
			} else if (checkKey(KeyType.console_down, key)) {
				if (console.getWrittenLines().size() > 0) {
					console.setCurLine(MathHelper.clamp(console.getCurLine() - 1, 0, console.getWrittenLines().size() - 1));
					console.setInput(console.getWrittenLines().get(console.getCurLine()));
				}
			} else if (checkKey(KeyType.console_finish, key)) {
				console.finishCommand();
				Main.getMain().revertGamestate();
			} else if (checkKey(KeyType.console_cancel, key)) {
				console.clearInput();
				console.setConsoleOpen(false);
				console.setCurLine(0);
				Main.getMain().revertGamestate();
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
				Main.getMain().setGamestate(Main.Gamestate.softPause);
			} else if (checkKey(KeyType.console_open_slash, key)) {
				console.setConsoleOpen(true);
				console.addKey(chr);
				Main.getMain().setGamestate(Main.Gamestate.softPause);
			}
		}
	}
	
	private boolean checkKey(KeyType type, int keyCode) {
		for (KeyType key : KeyType.values()) {
			if (key == type) {
				if (key.key1 == keyCode || key.key2 == keyCode) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public enum KeyType {
		console_open      (false, KeyEvent.VK_BACK_QUOTE),
		console_open_slash(false, KeyEvent.VK_SLASH),
		console_up        (false, KeyEvent.VK_UP),
		console_down      (false, KeyEvent.VK_DOWN),
		console_finish    (false, KeyEvent.VK_ENTER),
		console_cancel    (false, KeyEvent.VK_ESCAPE),
		console_delete    (false, KeyEvent.VK_BACK_SPACE),
		
		player_walk_up    (true, KeyEvent.VK_W),
		player_walk_down  (true, KeyEvent.VK_S),
		player_walk_left  (true, KeyEvent.VK_A),
		player_walk_right (true, KeyEvent.VK_D);
		
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
