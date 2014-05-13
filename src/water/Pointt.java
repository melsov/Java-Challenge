package water;

import java.awt.geom.Point2D;

public class Pointt 
{
	public float x, y;
	
	public Pointt() {
	}
	
	public Pointt(float xx, float yy) {
		x=xx; y=yy;
	}
	
	public Pointt(double xx, double yy) {
		this((float) xx, (float) yy);
	}
	
	public Pointt(Point2D.Float p) {
		this(p.x, p.y);
	}
	
	public boolean greaterThan(Pointt other) {
		return this.x > other.x && this.y > other.y;
	}
	
	public Pointt plus(Pointt other) {
		return new Pointt(other.x + x, other.y + y);
	}
}
