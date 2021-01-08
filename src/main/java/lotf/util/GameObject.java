package main.java.lotf.util;

import main.java.lotf.util.annotation.UseGetter;
import main.java.ulibs.utils.math.MathH;
import main.java.ulibs.utils.math.Vec2f;
import main.java.ulibs.utils.math.Vec2i;

public abstract class GameObject {
	@Deprecated @UseGetter({"getPosX", "getPosY"})
	protected transient Vec2f pos;
	@Deprecated @UseGetter({"getWidth", "getHeight"})
	protected transient Vec2i size;
	
	protected GameObject(Vec2f pos, Vec2i size) {
		this.pos = pos;
		this.size = size;
	}
	
	public void setPos(Vec2f pos) {
		fixNullPos();
		this.pos.set(pos);
	}
	
	public void setPos(float x, float y) {
		fixNullPos();
		pos.set(x, y);
	}
	
	public void setPosX(float x) {
		fixNullPos();
		pos.setX(x);
	}
	
	public void setPosY(float y) {
		fixNullPos();
		pos.setY(y);
	}
	
	public void addPosX(float x) {
		fixNullPos();
		pos.addX(x);
	}
	
	public void addPosY(float y) {
		fixNullPos();
		pos.addY(y);
	}
	
	public float getPosX() {
		fixNullPos();
		return pos.getX();
	}
	
	public float getPosY() {
		fixNullPos();
		return pos.getY();
	}
	
	public int getWidth() {
		if (size == null) {
			size = new Vec2i();
		}
		
		return size.getX();
	}
	
	public int getHeight() {
		if (size == null) {
			size = new Vec2i();
		}
		
		return size.getY();
	}
	
	private void fixNullPos() {
		if (pos == null) {
			pos = new Vec2f();
		}
	}
	
	public HitBox getBounds() {
		return new HitBox(MathH.floor(getPosX()), MathH.floor(getPosY()), getWidth(), getHeight());
	}
}
