package main.java.lotf.util.enums;

import main.java.lotf.util.math.Vec2i;

public enum EnumWorldType {
	overworld     (new Vec2i(17, 17)),
	underworld    (new Vec2i(13, 13)),
	dungeon_one   (new Vec2i()),
	dungeon_two   (new Vec2i()),
	dungeon_three (new Vec2i()),
	dungeon_four  (new Vec2i()),
	dungeon_five  (new Vec2i()),
	dungeon_six   (new Vec2i()),
	dungeon_seven (new Vec2i()),
	dungeon_eight (new Vec2i()),
	dungeon_nine  (new Vec2i()),
	dungeon_ten   (new Vec2i()),
	dungeon_eleven(new Vec2i()),
	dungeon_twelve(new Vec2i());
	
	private final Vec2i size;
	
	private EnumWorldType(Vec2i size) {
		this.size = size;
	}
	
	public Vec2i getSize() {
		return size;
	}
}
