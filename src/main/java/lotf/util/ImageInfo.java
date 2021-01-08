package main.java.lotf.util;

import java.awt.image.BufferedImage;

public class ImageInfo {
	public BufferedImage[] imgs;
	public int currentImage, currentFrameCounter;
	
	public ImageInfo(BufferedImage... imgs) {
		this.imgs = imgs;
	}
	
	public ImageInfo setImages(BufferedImage... imgs) {
		this.imgs = imgs;
		return this;
	}
	
	public BufferedImage getCurrentImage() {
		return imgs[currentImage];
	}
}
