package main.java.lotfbuilder.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.ui.UIHandler;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2i;

public class MouseHandler extends MouseAdapter {
	public static final Vec2i HUD_MOUSE_POS = new Vec2i(), WORLD_MOUSE_POS = new Vec2i();
	
	private int mouseState;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = e.getWheelRotation();
		
		if (!MainBuilder.main.builder.isInvOpen()) { //TODO setup zoom in/out
			if (scroll == 1) {
				MainBuilder.main.builder.increaseSlot();
			} else {
				MainBuilder.main.builder.decreaseSlot();
			}
		} else {
			//TODO setup builder inventory scrolling once i have enough tiles
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseState = e.getButton();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseState = e.getButton();
		updateMousePos(e);
		checkWithUI(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePos(e);
		checkPlace();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
	}
	
	private void checkPlace() {
		if (!MainBuilder.main.builder.isInvOpen()) {
			if (mouseState == 1) {
				MainBuilder.main.builder.placeMouseTileAt(WORLD_MOUSE_POS);
			} else if (mouseState == 3) {
				MainBuilder.main.builder.placeTileAt(null, WORLD_MOUSE_POS);
			}
		}
	}
	
	private void checkWithUI(MouseEvent e) {
		if (!UIHandler.checkClick(HUD_MOUSE_POS, mouseState == 1 ? ClickType.left : mouseState == 2 ? ClickType.middle : mouseState == 3 ? ClickType.right : null,
				e.isAltDown() ? ClickModifier.alt : e.isShiftDown() ? ClickModifier.shift : e.isControlDown() ? ClickModifier.ctrl : ClickModifier.none)) {
			checkPlace();
		}
	}
	
	private void updateMousePos(MouseEvent e) {
		HUD_MOUSE_POS.set(MathH.floor(e.getX() / MainBuilder.main.scale) - (MainBuilder.main.getExtraWidth() / 2),
				MathH.floor(e.getY() / MainBuilder.main.scale) - (MainBuilder.main.getExtraHeight() / 2));
		
		if (MainBuilder.main.camera != null) {
			WORLD_MOUSE_POS.set(HUD_MOUSE_POS);
			WORLD_MOUSE_POS.add(MathH.floor(MainBuilder.main.camera.getPosX()), MathH.floor(MainBuilder.main.camera.getPosY()));
		}
	}
}
