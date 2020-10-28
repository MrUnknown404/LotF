package main.java.lotf.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.lotf.Main;
import main.java.lotf.client.ui.button.Button.ClickModifier;
import main.java.lotf.client.ui.button.Button.ClickType;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2i;

public class MenuMouseHandler extends MouseAdapter {
	public static final Vec2i HUD_MOUSE_POS = new Vec2i();
	
	private int mouseState;
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseState = e.getButton();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseState = e.getButton();
		updateMousePos(e);
		checkUI(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePos(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
	}
	
	private void checkUI(MouseEvent e) {
		if (Main.getMain().getCurrentMenu() == null) {
			return;
		}
		
		Main.getMain().getCurrentMenu().checkClick(HUD_MOUSE_POS,
				mouseState == 1 ? ClickType.left : mouseState == 2 ? ClickType.middle : mouseState == 3 ? ClickType.right : null,
				e.isAltDown() ? ClickModifier.alt : e.isShiftDown() ? ClickModifier.shift : e.isControlDown() ? ClickModifier.ctrl : ClickModifier.none);
	}
	
	private void updateMousePos(MouseEvent e) {
		HUD_MOUSE_POS.set(MathH.floor(e.getX() / Main.getMain().scale) - (Main.getMain().getExtraWidth() / 2),
				MathH.floor(e.getY() / Main.getMain().scale) - (Main.getMain().getExtraHeight() / 2));
	}
}
