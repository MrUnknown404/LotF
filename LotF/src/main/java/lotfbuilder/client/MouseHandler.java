package main.java.lotfbuilder.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.MainBuilder;
import main.java.lotfbuilder.client.ui.UIHandler;
import main.java.lotfbuilder.client.ui.UIHandler.ClickModifier;
import main.java.lotfbuilder.client.ui.UIHandler.ClickType;

public class MouseHandler extends MouseAdapter {
	public static final Vec2i MOUSE_POS = new Vec2i();
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int scroll = e.getWheelRotation();
		
		if (!MainBuilder.main.builder.isInvOpen()) {
			if (scroll == 1) {
				MainBuilder.main.builder.increaseSlot();
			} else {
				MainBuilder.main.builder.decreaseSlot();
			}
		} else {
			//TODO setup builder inventory scrolling
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int button = e.getButton(); //1 = left, 3 = right
		updateMousePos(e);
		
		UIHandler.checkClick(MOUSE_POS, button == 1 ? ClickType.left : button == 2 ? ClickType.middle : button == 3 ? ClickType.right : null,
				e.isAltDown() ? ClickModifier.alt : e.isShiftDown() ? ClickModifier.shift : e.isControlDown() ? ClickModifier.ctrl : ClickModifier.none);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePos(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
	}
	
	private void updateMousePos(MouseEvent e) {
		MOUSE_POS.set(MathH.floor(e.getX() / MainBuilder.main.scale) - (MainBuilder.main.getExtraWidth() / 2),
				MathH.floor(e.getY() / MainBuilder.main.scale) - (MainBuilder.main.getExtraHeight() / 2));
	}
}
