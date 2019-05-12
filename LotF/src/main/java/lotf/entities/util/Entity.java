package main.java.lotf.entities.util;

import main.java.lotf.util.GameObject;
import main.java.lotf.util.IResetable;
import main.java.lotf.util.ITickable;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;

public abstract class Entity extends GameObject implements ITickable, IResetable {
	
	public Entity(Vec2f pos, Vec2i size) {
		super(pos, size);
	}
}
