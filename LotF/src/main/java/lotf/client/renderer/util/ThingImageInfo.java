package main.java.lotf.client.renderer.util;

import java.awt.image.BufferedImage;

import main.java.lotf.util.ThingInfo;

public class ThingImageInfo<T extends ThingInfo> extends ImageInfo {
	protected T info;
	
	public ThingImageInfo(T info, BufferedImage... imgs) {
		super(imgs);
		this.info = info;
	}
	
	public T getInfo() {
		return info;
	}
	
	@Override
	public String toString() {
		return "(currentImage:" + currentImage + ", currentFrameCounter:" + currentFrameCounter + ")";
	}
}
