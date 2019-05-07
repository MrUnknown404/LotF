package main.java.lotf.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.util.Console;
import main.java.lotf.util.KeyInfo;
import main.java.lotf.util.math.MathHelper;

public class KeyHandler extends KeyAdapter {

	private List<KeyInfo> keys;
	
	public KeyHandler registerKeys() {
		for (KeyType type : KeyType.values()) { //TODO load from config
			type.registerKey(new KeyInfo(type, type.defaultKey, -1));
		}
		
		return this;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		handleConsole(key, e.getKeyChar());
		if (Main.getMain().shouldPlayerHaveControl()) {
			if (Main.getMain().getWorld().getPlayer() != null) {
				handlePlayer(key);
			} else {
				
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	private void handlePlayer(int key) {
		EntityPlayer player = Main.getMain().getWorld().getPlayer();
		
		if (Main.getMain().shouldPlayerHaveControl()) {
			if (checkKey(KeyType.player_walk_up, key)) {
				
			} else if (checkKey(KeyType.player_walk_down, key)) {
				
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
		for (KeyInfo info : keys) {
			if (info.getKeyType() == type) {
				if (info.getKey1() == keyCode || info.getKey2() == keyCode) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public enum KeyType {
		console_open      (KeyEvent.VK_BACK_QUOTE),
		console_open_slash(KeyEvent.VK_SLASH),
		console_up        (KeyEvent.VK_UP),
		console_down      (KeyEvent.VK_DOWN),
		console_finish    (KeyEvent.VK_ENTER),
		console_cancel    (KeyEvent.VK_ESCAPE),
		console_delete    (KeyEvent.VK_BACK_SPACE),
		
		player_walk_up   (KeyEvent.VK_W),
		player_walk_down (KeyEvent.VK_S),
		player_walk_left (KeyEvent.VK_A),
		player_walk_right(KeyEvent.VK_D);
		
		private int defaultKey;
		private KeyInfo info;
		
		private KeyType(int defaultKey) {
			this.defaultKey = defaultKey;
		}
		
		void registerKey(KeyInfo info) {
			Console.print(Console.WarningType.RegisterDebug, info.getKeyType().toString() + " has been registered to -> (" + KeyEvent.getKeyText(
					info.getKey1()) + ":" + KeyEvent.getKeyText(info.getKey2()) + " | " + info.getKey1() + ":" + info.getKey2() + ")");
			this.info = info;
		}
		
		public KeyInfo getKeyInfo() {
			return info;
		}
	}
}
