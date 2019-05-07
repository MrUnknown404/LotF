package main.java.lotf.client.renderer.util;

import java.awt.image.BufferedImage;

public class ImageInfo {
	public BufferedImage[] imgs;
	public int currentImage, currentFrameCounter;
	
	public ImageInfo(BufferedImage... imgs) {
		this.imgs = imgs;
	}
	
	public void setImages(BufferedImage... imgs) {
		this.imgs = imgs;
	}
	
	public BufferedImage getCurrentImage() {
		return imgs[currentImage];
	}
}
