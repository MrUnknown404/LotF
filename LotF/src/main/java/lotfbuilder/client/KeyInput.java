package main.java.lotfbuilder.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.lotf.util.EnumDungeonType;
import main.java.lotf.world.Room;
import main.java.lotf.world.World;
import main.java.lotf.world.World.WorldType;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.gui.DebugHud;

public class KeyInput extends KeyAdapter {

	private static final int SPEED = 10;
	private boolean[] keys = new boolean[4];
	
	private World.WorldType type;
	private EnumDungeonType dungeonType;
	
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
					type = WorldType.overworld;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.one;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 3) {
					MainBuilder.getRoomBuilder().setup(type, dungeonType, Room.RoomSize.small, 0);
				}
			} else if (key == KeyEvent.VK_F2) {
				if (MainBuilder.getRoomBuilder().creationState == 1) {
					type = WorldType.underworld;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.two;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 3) {
					MainBuilder.getRoomBuilder().setup(type, dungeonType, Room.RoomSize.medium, 0);
				} else {
					MainBuilder.getRoomBuilder().loadRoom();
				}
			} else if (key == KeyEvent.VK_F3) {
				if (MainBuilder.getRoomBuilder().creationState == 1) {
					type = WorldType.inside;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.three;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 3) {
					MainBuilder.getRoomBuilder().setup(type, dungeonType, Room.RoomSize.big, 0);
				}
			} else if (key == KeyEvent.VK_F4) {
				if (MainBuilder.getRoomBuilder().creationState == 1) {
					type = WorldType.dungeon;
					MainBuilder.getRoomBuilder().creationState = 2;
				} else if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.four;
					MainBuilder.getRoomBuilder().creationState = 3;
				} else if (MainBuilder.getRoomBuilder().creationState == 3) {
					MainBuilder.getRoomBuilder().setup(type, dungeonType, Room.RoomSize.veryBig, 0);
				}
			} else if (key == KeyEvent.VK_F5) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.five;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F6) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.six;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F7) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.seven;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F8) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.eight;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F9) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.nine;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F10) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.ten;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F11) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.eleven;
					MainBuilder.getRoomBuilder().creationState = 3;
				}
			} else if (key == KeyEvent.VK_F12) {
				if (MainBuilder.getRoomBuilder().creationState == 2) {
					dungeonType = EnumDungeonType.twelve;
					MainBuilder.getRoomBuilder().creationState = 3;
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
