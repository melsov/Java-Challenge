package water;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.w3c.dom.css.Rect;

public class Box {
	Pointt origin = new Pointt();
	Pointt size = new Pointt();
	
	public Box(Pointt _origin, Pointt _size) {
		origin = _origin;
		size = _size;
	}
	
	public boolean isWithin(Pointt p) {
		return p.greaterThan(origin) && this.extent().greaterThan(p); 
	}
	
	public Pointt extent() {
		return origin.plus(size);
	}
	
	public Rectangle2D.Float getRectangle() {
		return new Rectangle2D.Float(origin.x, origin.y, size.x, size.y);
	}
}
