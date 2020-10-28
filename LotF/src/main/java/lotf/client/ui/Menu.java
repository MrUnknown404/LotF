package main.java.lotf.client.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import main.java.lotf.Main;
import main.java.lotf.client.MenuMouseHandler;
import main.java.lotf.client.gui.GuiButton;
import main.java.lotf.client.ui.button.Button.ClickModifier;
import main.java.lotf.client.ui.button.Button.ClickType;
import main.java.lotf.init.Menus;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.ITickable;
import main.java.ulibs.utils.math.Vec2i;

public abstract class Menu implements ITickable {
	protected final List<GuiButton> guiButtons;
	protected final BufferedImage background;
	
	public Menu(String menuName) {
		this.guiButtons = registerButtons();
		this.background = GetResource.getTexture(ResourceType.gui, menuName + "_background");
		
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
		g.drawImage(background, 0, 0, Main.HUD_WIDTH, Main.HUD_HEIGHT, null);
		
		for (GuiButton b : guiButtons) {
			g.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), b.getW(), b.getH(), null);
		}
	}
	
	public abstract List<GuiButton> registerButtons();
	
	public List<GuiButton> getButtons() {
		return guiButtons;
	}
}
