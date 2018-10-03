package main.java.lotfbuilder.client;

import main.java.lotf.util.math.Vec2i;

public final class Camera {

	public Vec2i pos = new Vec2i(), moveDir = new Vec2i();
	
	public void tick() {
		if (!moveDir.equals(Vec2i.NULL_VECTOR)) {
			pos.add(moveDir);
		}
	}
}
