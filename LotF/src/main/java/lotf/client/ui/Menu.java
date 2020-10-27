package main.java.lotf.client.ui;

import java.awt.Graphics2D;
import java.util.List;

import main.java.lotf.client.MenuMouseHandler;
import main.java.lotf.client.gui.GuiButton;
import main.java.lotf.client.ui.button.Button.ClickModifier;
import main.java.lotf.client.ui.button.Button.ClickType;
import main.java.lotf.init.Menus;
import main.java.lotf.util.ITickable;
import main.java.ulibs.utils.math.Vec2i;

public abstract class Menu implements ITickable {
	protected final List<GuiButton> guiButtons;
	
	public Menu() {
		guiButtons = registerButtons();
		Menus.add(this);
	}
	
	/** @return true of click was successful, otherwise false */
	public boolean checkClick(Vec2i click, ClickType clickType, ClickModifier clickMod) {
		for (GuiButton b : guiButtons) {
			if (b.getButton().getBounds().intersectsPoint(click.getX(), click.getY())) {
				return b.getButton().onClick(clickType, clickMod);
			}
		}
		
		return false;
	}
	
	protected boolean checkHover(GuiButton b) {
		Vec2i mousePos = MenuMouseHandler.HUD_MOUSE_POS;
		
		if (b.getButton().getBounds().intersectsPoint(mousePos.getX(), mousePos.getY())) {
			return true;
		}
		return false;
	}
	
	@Override
	public void tick() {
		for (GuiButton b : guiButtons) {
			if (checkHover(b)) {
				b.setHovering(true);
			} else {
				b.setHovering(false);
			}
		}
	}
	
	public void render(Graphics2D g) {
		for (GuiButton b : guiButtons) {
			g.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), b.getW(), b.getH(), null);
		}
	}
	
	public abstract List<GuiButton> registerButtons();
	
	public List<GuiButton> getButtons() {
		return guiButtons;
	}
}
