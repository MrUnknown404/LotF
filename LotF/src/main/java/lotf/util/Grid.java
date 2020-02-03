package main.java.lotf.util;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.util.Console.WarningType;

public class Grid<T> {
	private final List<T> grid = new ArrayList<T>();
	private final int width, height;
	
	public Grid(T t, int width, int height) {
		this.width = width;
		this.height = height;
		
		for (int i = 0; i < width * height ; i++) {
			grid.add(t);
		}
	}
	
	public Grid(int width, int height) {
		this(null, width, height);
	}
	
	public List<T> get() {
		return grid;
	}
	
	public T get(int x, int y) {
		return checkValid(x, y) ? grid.get(x + (y * width)) : null;
	}
	
	public Grid<T> add(T t, int x, int y) {
		if (checkValid(x, y)) {
			grid.set(y * width + x, t);
		}
		
		return this;
	}
	
	private boolean checkValid(int x, int y) {
		if (x < 0) {
			Console.print(WarningType.Error, "'X' Cannot be lower than 0!");
			return false;
		} else if (x >= width) {
			Console.print(WarningType.Error, "'X' Cannot be above " + width + "!");
			return false;
		} else if (y < 0) {
			Console.print(WarningType.Error, "'Y' Cannot be lower than 0!");
			return false;
		} else if (y >= height) {
			Console.print(WarningType.Error, "'Y' Cannot be above " + height + "!");
			return false;
		}
		
		return true;
	}
	
	public List<String> toStringList() {
		List<String> strs = new ArrayList<String>(grid.size());
		String str = "";
		for (int i = 0; i < grid.size(); i++) {
			str += grid.get(i);
			if (i % width == width - 1) {
				strs.add(str);
				str = "";
			} else {
				str += ", ";
			}
		}
		
		return strs;
	}
	
	public int size() {
		return grid.size();
	}
	
	public boolean isEmpty() {
		return grid.size() == 0;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return grid.toString();
	}
}