package main.java.lotfbuilder.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public final class RoomFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		} else if (f.toString().endsWith(".lotfroom")) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return ".lotfroom";
	}
}
