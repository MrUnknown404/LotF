package main.java.lotf.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.lotf.Main;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.entity.EntityPlayer;
import main.java.lotf.items.ItemSpellBook;
import main.java.lotf.util.math.MathHelper;

public final class KeyInput extends KeyAdapter {

	public static boolean[] keyDown = new boolean[4];
	
	public KeyInput() {
		for (int i = 0; i < keyDown.length; i++) {
			keyDown[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		DebugConsole console = Main.getCommandConsole();
		
		if (console.isConsoleOpen) {
			if (key == KeyEvent.VK_UP) {
				if (console.writtenLines.size() > 0) {
					console.curLine = MathHelper.clamp(console.curLine += 1, 0, console.writtenLines.size() - 1);
					console.input = console.writtenLines.get(console.curLine);
				}
			} else if (key == KeyEvent.VK_DOWN) {
				if (console.writtenLines.size() > 0) {
					console.curLine = MathHelper.clamp(console.curLine -= 1, 0, console.writtenLines.size() - 1);
					console.input = console.writtenLines.get(console.curLine);
				}
			}
			
			if (key == KeyEvent.VK_ESCAPE) {
				console.clearInput();
				console.isConsoleOpen = false;
				console.curLine = 0;
			} else if (key != KeyEvent.VK_BACK_SPACE) {
				if (e.getKeyChar() != '\uFFFF' && e.getKeyCode() != KeyEvent.VK_DELETE) {
					console.addKey(e.getKeyChar());
				}
			} else {
				if (!console.input.isEmpty()) {
					console.removeKey();
				}
			}
		} else {
			if (Main.getRoomHandler().getPlayer() != null) {
				if (!Main.getRoomHandler().getPlayer().getMovingToRoom() && !Main.getRoomHandler().getPlayer().getInventory().isSelectingPage) {
					if (key == KeyEvent.VK_DOWN) {
						Main.getRoomHandler().getPlayer().getInventory().isInventoryOpen = !Main.getRoomHandler().getPlayer().getInventory().isInventoryOpen;
						
						if (Main.getRoomHandler().getPlayer().getInventory().isInventoryOpen) {
							Main.gamestate = Main.Gamestate.hardPause;
						} else {
							Main.gamestate = Main.Gamestate.run;
						}
					}
				}
				
				if (!Main.getRoomHandler().getPlayer().getInventory().isInventoryOpen) {
					if (key == KeyEvent.VK_UP) {
						Main.getRoomHandler().getPlayer().getInventory().getSelectedSword().use();
					} else if (key == KeyEvent.VK_LEFT) {
						Main.getRoomHandler().getPlayer().getInventory().getSelectedLeft().use();
					} else if (key == KeyEvent.VK_RIGHT) {
						Main.getRoomHandler().getPlayer().getInventory().getSelectedRight().use();
					}
					
					if (key == KeyEvent.VK_W) {
						Main.getRoomHandler().getPlayer().setMoveDirectionY(-EntityPlayer.MOVE_SPEED);
						keyDown[0] = true;
					} else if (key == KeyEvent.VK_S) {
						Main.getRoomHandler().getPlayer().setMoveDirectionY(EntityPlayer.MOVE_SPEED);
						keyDown[1] = true;
					}
					
					if (key == KeyEvent.VK_A) {
						Main.getRoomHandler().getPlayer().setMoveDirectionX(-EntityPlayer.MOVE_SPEED);
						keyDown[2] = true;
					} else if (key == KeyEvent.VK_D) {
						Main.getRoomHandler().getPlayer().setMoveDirectionX(EntityPlayer.MOVE_SPEED);	
						keyDown[3] = true;
					}
				} else {
					if (key == KeyEvent.VK_LEFT) {
						Main.getRoomHandler().getPlayer().getInventory().setSelected(true);
					} else if (key == KeyEvent.VK_RIGHT) {
						Main.getRoomHandler().getPlayer().getInventory().setSelected(false);
					}
					
					if (!Main.getRoomHandler().getPlayer().getInventory().isSelectingPage) {
						if (key == KeyEvent.VK_UP) {
							System.err.println("up while in inv");
						}
						
						if (key == KeyEvent.VK_W) {
							Main.getRoomHandler().getPlayer().getInventory().addUpSlot();
						} else if (key == KeyEvent.VK_S) {
							Main.getRoomHandler().getPlayer().getInventory().addDownSlot();
						} else if (key == KeyEvent.VK_A) {
							Main.getRoomHandler().getPlayer().getInventory().addLeftSlot();
						} else if (key == KeyEvent.VK_D) {
							Main.getRoomHandler().getPlayer().getInventory().addRightSlot();
						}
					} else {
						if (key == KeyEvent.VK_A) {
							((ItemSpellBook) Main.getRoomHandler().getPlayer().getInventory().findItem("spellBook", 0)).getSpellPageList().leftSelectedPage();
						} else if (key == KeyEvent.VK_D) {
							((ItemSpellBook) Main.getRoomHandler().getPlayer().getInventory().findItem("spellBook", 0)).getSpellPageList().rightSelectedPage();
						}
					}
				}
			}
		}
		
		if (key == KeyEvent.VK_BACK_QUOTE && !console.isConsoleOpen) {
			console.isConsoleOpen = true;
		} else if (key == KeyEvent.VK_ENTER && console.isConsoleOpen) {
			console.finishCommand();
		} else if (key == KeyEvent.VK_SLASH && !console.isConsoleOpen) {
			console.isConsoleOpen = true;
			console.addKey(e.getKeyChar());
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
		} else if (key == KeyEvent.VK_S) {
			keyDown[1] = false;
		} else if (key == KeyEvent.VK_A) {
			keyDown[2] = false;
		} else if (key == KeyEvent.VK_D) {
			keyDown[3] = false;
		}
		
		if (Main.getRoomHandler().getPlayer() != null) {
			if (!keyDown[0] && !keyDown[1]) {
				Main.getRoomHandler().getPlayer().setMoveDirectionY(0);
			}
			if (!keyDown[2] && !keyDown[3]) {
				Main.getRoomHandler().getPlayer().setMoveDirectionX(0);
			}
		}
	}
}
