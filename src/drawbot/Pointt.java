package drawbot;

import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class Pointt
{
	public float x, y;
	
	/*
	 * CONSTRUCTORS
	 */
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
	public Pointt(Point2D p) {
		this( p.getX(), p.getY());
	}
	public Pointt copy() {
		return new Pointt(x,y);
	}
	
	/*
	 * MATH
	 */
	@Override
	public boolean equals(Object otherr) {
		if (!otherr.getClass().equals(this.getClass())) {
			return false;
		}
		Pointt other = (Pointt) otherr;
		return Math.abs(x - other.x) < 0.2 && Math.abs(y - other.y) < 0.2;
	}
	public boolean equalY(Pointt other) {
		return equalY(other, .01);
	}
	public boolean equalY(Pointt other, double epsilon) {
		return FloatMath.Equal(y, other.y, epsilon);
	}
	public boolean greaterThan(Pointt other) {
		return this.x > other.x && this.y > other.y;
	}
	public Pointt plus(Pointt other) {
		return new Pointt(other.x + x, other.y + y);
	}
	public Pointt minus(Pointt other) {
		return new Pointt(x - other.x, y - other.y);
	}
	public Pointt multiply(Pointt other) {
		return new Pointt(other.x * x, other.y * y);
	}
	public Pointt multiply(float num) {
		return new Pointt(num * x, num * y);
	}
	public Pointt dividedBy(Pointt other) {
		return new Pointt(x/other.x, y/other.y);
	}
	public Pointt dividedBy(float num) {
		if (num == 0) {
			return new Pointt(Float.MAX_VALUE, Float.MAX_VALUE);
		}
		return new Pointt(x/num, y/num);
	}
	public Pointt dividedBy(double num) {
		if (num == 0) {
			return new Pointt(Float.MAX_VALUE, Float.MAX_VALUE);
		}
		return new Pointt(x/num, y/num);
	}
	public Pointt clampToWholeNumbersWhenClose() {
		return clampToWholeNumbersWhenClose(.01f);
	}
	public Pointt clampToWholeNumbersWhenClose(float epsilon) {
		float xr = round(x);
		float yr = round(y);
		Pointt res = this.copy();
		if (abs(xr - x) < epsilon) res.x = xr; 
		if (abs(yr - y) < epsilon) res.y = yr;
		return res;
	}
	public Pointt clobberDecimals() {
		Pointt res = this.copy();
		res.x = (int) (res.x + .5);
		res.y = (int) (res.y + .5);
		return res;
	}
	public Pointt multiply(double num) {
		return new Pointt(num * x, num * y);
	}
	public double distance() {
		return Math.sqrt(x*x + y*y);
	}
	public double distanceFrom(Pointt other) {
		return this.minus(other).distance();
	}
	public double dot(Pointt other) {
		return this.x* other.x + this.y*other.y;
	}
	public Pointt unitPointt() {
		return this.dividedBy(this.distance());
	}
	public static Pointt Max(Pointt a, Pointt b) {
		return new Pointt(a.x > b.x ? a.x : b.x , a.y > b.y ? a.y : b.y);
	}
	public static Pointt Min(Pointt a, Pointt b) {
		return new Pointt(a.x < b.x ? a.x : b.x , a.y < b.y ? a.y : b.y);
	}
	
	/*
	 * Handy for swing
	 */
	public Point2D.Double point2D() {
		return new Point2D.Double(x, y);
	}
}
