package main.java.lotfbuilder.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.gui.DebugHud;

public final class KeyInput extends KeyAdapter {

	private static final int SPEED = 10;
	private boolean[] keys = new boolean[4];
	
	public KeyInput() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_BACK_QUOTE) {
			DebugHud.toggleShowDebug();
		}
		
		if (MainBuilder.getDoesRoomExist()) {
			if (key == KeyEvent.VK_W) {
				MainBuilder.getCamera().moveDir.setY(SPEED);
				keys[0] = true;
			} else if (key == KeyEvent.VK_S) {
				MainBuilder.getCamera().moveDir.setY(-SPEED);
				keys[1] = true;
			}
			
			if (key == KeyEvent.VK_A) {
				MainBuilder.getCamera().moveDir.setX(SPEED);
				keys[2] = true;
			} else if (key == KeyEvent.VK_D) {
				MainBuilder.getCamera().moveDir.setX(-SPEED);
				keys[3] = true;
			}
			
			if (key == KeyEvent.VK_F2) {
				MainBuilder.getRoomBuilder().saveRoom();
			} else if (key == KeyEvent.VK_F3) {
				MainBuilder.getRoomBuilder().resetRoom();
			}
			
			if (key == KeyEvent.VK_DOWN) {
				MainBuilder.getRoomBuilder().isOpen = !MainBuilder.getRoomBuilder().isOpen;
			}
		} else {
			if (key == KeyEvent.VK_F1) {
				if (MainBuilder.getRoomBuilder().creationState == 0) {
					MainBuilder.getRoomBuilder().creationState = 1;
				} else if (MainBuilder.getRoomBuilder().creationState == 1) {
					MainBuilder.getRoomBuilder().setup(Room.RoomSize.small);
				}
			} else if (key == KeyEvent.VK_F2) {
				if (MainBuilder.getRoomBuilder().creationState == 0) {
					MainBuilder.getRoomBuilder().creationState = 1;
				} else if (MainBuilder.getRoomBuilder().creationState == 1) {
					MainBuilder.getRoomBuilder().setup(Room.RoomSize.medium);
				}
			} else if (key == KeyEvent.VK_F3) {
				if (MainBuilder.getRoomBuilder().creationState == 0) {
					MainBuilder.getRoomBuilder().creationState = 1;
				} else if (MainBuilder.getRoomBuilder().creationState == 1) {
					MainBuilder.getRoomBuilder().setup(Room.RoomSize.big);
				}
			} else if (key == KeyEvent.VK_F4) {
				if (MainBuilder.getRoomBuilder().creationState == 0) {
					MainBuilder.getRoomBuilder().creationState = 1;
				} else if (MainBuilder.getRoomBuilder().creationState == 1) {
					MainBuilder.getRoomBuilder().setup(Room.RoomSize.veryBig);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (MainBuilder.getDoesRoomExist()) {
			if (key == KeyEvent.VK_W) {
				keys[0] = false;
			} else if (key == KeyEvent.VK_S) {
				keys[1] = false;
			}
			
			if (key == KeyEvent.VK_A) {
				keys[2] = false;
			} else if (key == KeyEvent.VK_D) {
				keys[3] = false;
			}
			
			if (!keys[0] && !keys[1]) {
				MainBuilder.getCamera().moveDir.setY(0);
			}
			
			if (!keys[2] && !keys[3]) {
				MainBuilder.getCamera().moveDir.setX(0);
			}
		}
	}
}
