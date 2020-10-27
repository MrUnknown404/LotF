package main.java.lotf.util.enums;

import main.java.ulibs.utils.math.Vec2i;

public enum EnumWorldType {
	debugworld    (new Vec2i(6), new Vec2i(11), new Vec2i(8)),
	overworld     (new Vec2i(0), new Vec2i(17), new Vec2i(8)),
	underworld    (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_one   (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_two   (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_three (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_four  (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_five  (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_six   (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_seven (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_eight (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_nine  (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_ten   (new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_eleven(new Vec2i(),  new Vec2i(),   new Vec2i()),
	dungeon_twelve(new Vec2i(),  new Vec2i(),   new Vec2i());
	
	private final Vec2i startActiveBounds, endActiveBounds, startingPos;
	
	private EnumWorldType(Vec2i startActiveBounds, Vec2i endActiveBounds, Vec2i startingPos) {
		this.startActiveBounds = startActiveBounds;
		this.endActiveBounds = endActiveBounds;
		this.startingPos = startingPos;
	}
	
	public Vec2i getStartActiveBounds() {
		return startActiveBounds;
	}
	
	public Vec2i getEndActiveBounds() {
		return endActiveBounds;
	}
	
	public Vec2i getStartingPos() {
		return startingPos;
	}
}
