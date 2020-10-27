package main.java.lotf.client.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.lotf.client.ui.button.Button;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.GetResource.ResourceType;
import main.java.lotf.util.ImageInfo;

public class GuiButton {
	protected final ImageInfo image;
	protected final Button button;
	protected boolean isHovering;
	
	public GuiButton(String normalTexture, Button button) {
		this.button = button;
		
		List<BufferedImage> imgs = new ArrayList<BufferedImage>();
		imgs.add(GetResource.getTexture(ResourceType.gui, normalTexture));
		imgs.add(GetResource.getTexture(ResourceType.gui, normalTexture + "_hovering"));
		
		image = new ImageInfo(imgs.toArray(new BufferedImage[0]));
	}
	
	public BufferedImage getImage() {
		if (isHovering) {
			image.currentImage = 1;
		} else {
			image.currentImage = 0;
		}
		
		return image.getCurrentImage();
	}
	
	public Button getButton() {
		return button;
	}
	
	public float getX() {
		return button.getPosX();
	}
	
	public float getY() {
		return button.getPosY();
	}
	
	public int getW() {
		return button.getWidth();
	}
	
	public int getH() {
		return button.getHeight();
	}
	
	public boolean isHovering() {
		return isHovering;
	}
	
	public void setHovering(boolean isHovering) {
		this.isHovering = isHovering;
	}
}
