package main.java.lotf.util;

public class HitBox {
	public final int x, y, w, h;
	
	public HitBox(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean intersects(HitBox box) {
		int tw = this.w, th = this.h, rw = box.w, rh = box.h;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		int tx = this.x, ty = this.y, rx = box.x, ry = box.y;
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}
	
	public boolean intersectsPoint(int x, int y) {
		int tw = this.w, th = this.h, rw = 1, rh = 1;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		int tx = this.x, ty = this.y;
		
		rw += x;
		rh += y;
		tw += tx;
		th += ty;
		
		return ((rw < x || rw > tx) && (rh < y || rh > ty) && (tw < tx || tw > x) && (th < ty || th > y));
	}
	
	public HitBox addX(int x) {
		return new HitBox(this.x + x, y, w, h);
	}
	
	public HitBox addY(int y) {
		return new HitBox(x, this.y + y, w, h);
	}
	
	public HitBox addW(int w) {
		return new HitBox(x, y, this.w + w, h);
	}
	
	public HitBox addH(int h) {
		return new HitBox(x, y, w, this.h + h);
	}
}
