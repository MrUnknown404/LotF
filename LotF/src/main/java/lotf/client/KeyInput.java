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
		EntityPlayer player = Main.getWorldHandler().getPlayer();
		
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
			} else if (key == KeyEvent.VK_ENTER) {
				console.finishCommand();
				Main.gamestate = Main.Gamestate.run;
			} else if (key == KeyEvent.VK_ESCAPE) {
				console.clearInput();
				console.isConsoleOpen = false;
				console.curLine = 0;
				Main.gamestate = Main.Gamestate.run;
			} else if (key != KeyEvent.VK_BACK_SPACE) {
				if (e.getKeyChar() != '\uFFFF' && e.getKeyCode() != KeyEvent.VK_DELETE) {
					console.addKey(e.getKeyChar());
				}
			} else {
				if (!console.input.isEmpty()) {
					console.removeKey();
				}
			}
		} else if (!player.getMovingToRoom()) {
			//if (Main.getIsDebug()) {
			if (key == KeyEvent.VK_BACK_QUOTE) {
				console.isConsoleOpen = true;
				Main.gamestate = Main.Gamestate.softPause;
			} else if (key == KeyEvent.VK_SLASH) {
				console.isConsoleOpen = true;
				console.addKey(e.getKeyChar());
				Main.gamestate = Main.Gamestate.softPause;
			}
			//}
			
			if (player != null) {
				if (!player.getInventory().isSelectingPage) {
					if (key == KeyEvent.VK_DOWN) {
						player.getInventory().isInventoryOpen = !player.getInventory().isInventoryOpen;
						
						if (player.getInventory().isInventoryOpen) {
							Main.gamestate = Main.Gamestate.hardPause;
						} else {
							Main.gamestate = Main.Gamestate.run;
						}
					}
				}
				
				if (!player.getInventory().isInventoryOpen) {
					if (key == KeyEvent.VK_F2) {
						Main.save();
					} else if (key == KeyEvent.VK_F3) {
						Main.load();
					}
					
					if (key == KeyEvent.VK_UP) {
						player.getInventory().getSelectedSword().use();
					} else if (key == KeyEvent.VK_LEFT) {
						player.getInventory().getSelectedLeft().use();
					} else if (key == KeyEvent.VK_RIGHT) {
						player.getInventory().getSelectedRight().use();
					}
					
					if (key == KeyEvent.VK_W) {
						keyDown[0] = true;
					} else if (key == KeyEvent.VK_S) {
						keyDown[1] = true;
					}
					
					if (key == KeyEvent.VK_A) {
						keyDown[2] = true;
					} else if (key == KeyEvent.VK_D) {
						keyDown[3] = true;
					}
				} else {
					if (!player.getInventory().isSelectingPage) {
						if (key == KeyEvent.VK_UP) {
							player.getInventory().changeSelectedScreen();
						}
					}
					
					if (player.getInventory().getSelectedScreen() == 0) {
						if (key == KeyEvent.VK_LEFT) {
							player.getInventory().setSelected(true);
						} else if (key == KeyEvent.VK_RIGHT) {
							player.getInventory().setSelected(false);
						}
						
						if (!player.getInventory().isSelectingPage) {
							if (key == KeyEvent.VK_W) {
								player.getInventory().addUpSlot();
							} else if (key == KeyEvent.VK_S) {
								player.getInventory().addDownSlot();
							} else if (key == KeyEvent.VK_A) {
								player.getInventory().addLeftSlot();
							} else if (key == KeyEvent.VK_D) {
								player.getInventory().addRightSlot();
							}
						} else {
							if (key == KeyEvent.VK_A) {
								((ItemSpellBook) player.getInventory().findItem("spellBook", 0)).getSpellPageList().leftSelectedPage();
							} else if (key == KeyEvent.VK_D) {
								((ItemSpellBook) player.getInventory().findItem("spellBook", 0)).getSpellPageList().rightSelectedPage();
							}
						}
					} else {
						int doMapSelectionLater;
					}
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		EntityPlayer player = Main.getWorldHandler().getPlayer();
		
		if (key == KeyEvent.VK_W) {
			keyDown[0] = false;
		} else if (key == KeyEvent.VK_S) {
			keyDown[1] = false;
		} else if (key == KeyEvent.VK_A) {
			keyDown[2] = false;
		} else if (key == KeyEvent.VK_D) {
			keyDown[3] = false;
		}
		
		if (player != null) {
			if (!keyDown[0] && !keyDown[1]) {
				player.setMoveDirectionY(0);
			}
			if (!keyDown[2] && !keyDown[3]) {
				player.setMoveDirectionX(0);
			}
		}
	}
	
	public void tick() {
		EntityPlayer player = Main.getWorldHandler().getPlayer();
		if (keyDown[0] && keyDown[1]) {
			player.setMoveDirectionY(0);
		} else if (keyDown[0]) {
			player.setMoveDirectionY(-EntityPlayer.MOVE_SPEED);
		} else if (keyDown[1]) {
			player.setMoveDirectionY(EntityPlayer.MOVE_SPEED);
		}
		
		if (keyDown[2] && keyDown[3]) {
			player.setMoveDirectionX(0);
		} else if (keyDown[2]) {
			player.setMoveDirectionX(-EntityPlayer.MOVE_SPEED);
		} else if (keyDown[3]) {
			player.setMoveDirectionX(EntityPlayer.MOVE_SPEED);
		}
	}
}
