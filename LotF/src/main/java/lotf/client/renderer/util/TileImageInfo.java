package main.java.lotf.client.renderer.util;

import java.awt.image.BufferedImage;

import main.java.lotf.tile.TileInfo;

public class TileImageInfo extends ImageInfo {
	protected TileInfo info;
	
	public TileImageInfo(TileInfo info, BufferedImage... imgs) {
		super(imgs);
		this.info = info;
	}
	
	public TileInfo getTileInfo() {
		return info;
	}
	
	@Override
	public String toString() {
		return "(currentImage:" + currentImage + ", currentFrameCounter:" + currentFrameCounter + ")";
	}
}
