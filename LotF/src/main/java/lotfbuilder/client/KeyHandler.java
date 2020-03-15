package main.java.lotfbuilder.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.lotf.util.ITickable;
import main.java.lotfbuilder.MainBuilder;

public class KeyHandler extends KeyAdapter implements ITickable {
	
	public boolean[] dualKeys = new boolean[4];
	
	public KeyHandler() {
		for (int i = 0; i < 4; i++) {
			dualKeys[0] = false;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_F11) {
			Window.toggleFullscreen();
		}
		
		if (key == KeyEvent.VK_W) {
			dualKeys[0] = true;
		} else if (key == KeyEvent.VK_A) {
			dualKeys[1] = true;
		} else if (key == KeyEvent.VK_S) {
			dualKeys[2] = true;
		} else if (key == KeyEvent.VK_D) {
			dualKeys[3] = true;
		} else if (key == KeyEvent.VK_MINUS) {
			MainBuilder.main.builder.decreaseLayer();
		} else if (key == KeyEvent.VK_EQUALS) {
			MainBuilder.main.builder.increaseLayer();
		} else if (key == KeyEvent.VK_F1) {
			MainBuilder.main.builder.saveRoom();
		}
		
		if (key == KeyEvent.VK_E) {
			MainBuilder.main.builder.toggleInv();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			dualKeys[0] = false;
		} else if (key == KeyEvent.VK_A) {
			dualKeys[1] = false;
		} else if (key == KeyEvent.VK_S) {
			dualKeys[2] = false;
		} else if (key == KeyEvent.VK_D) {
			dualKeys[3] = false;
		}
	}
	
	@Override
	public void tick() {
		if ((dualKeys[0] && dualKeys[2]) || (!dualKeys[0] && !dualKeys[2])) {
			MainBuilder.main.camera.setMoveY(0);
		} else if (dualKeys[0]) {
			MainBuilder.main.camera.setMoveY(-1);
		} else if (dualKeys[2]) {
			MainBuilder.main.camera.setMoveY(1);
		}
		
		if ((dualKeys[1] && dualKeys[3]) || (!dualKeys[1] && !dualKeys[3])) {
			MainBuilder.main.camera.setMoveX(0);
		} else if (dualKeys[1]) {
			MainBuilder.main.camera.setMoveX(-1);
		} else if (dualKeys[3]) {
			MainBuilder.main.camera.setMoveX(1);
		}
	}
}
