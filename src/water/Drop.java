package water;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;

public class Drop 
{
	public double Xvel;
	public double Yvel;
	public double Xloc;
	public double Yloc;
	public double Delete;
	
	private static double COEFFICIENT_OF_RESTITUTION = 0.0;
	
	public void moveInXDir() {
		Xloc += Xvel;
	}
	
	public void youCollidedWithSomething() {
		Xvel=0;
	}
	
	public void collideWithLine(Line2D.Double line) {
		if (line == null)
			return;
		Pointt normal = getNormal(line);
		Pointt curVel = new Pointt(Xvel, Yvel);
		Pointt collisionPush = normal.multiply(normal.dot(curVel));
		Pointt newVel = curVel.minus(collisionPush.multiply(1.0 + COEFFICIENT_OF_RESTITUTION));
		
		Xvel = newVel.x;
		Yvel = newVel.y;
	}
	
	private Pointt getNormal(Line2D.Double line) {
		line = LeftToRightLine(line);
		boolean wantLeftNormal = Yvel > 0.0; // (drop headed downwards)
		return NormalVectorOfLine(line, wantLeftNormal);
	}
	
	private static Pointt NormalVectorOfLine(Line2D.Double line, boolean wantLeftNormal) {
		line = LeftToRightLine(line);
		Pointt leftP = new Pointt(line.getP1());
		Pointt rightP =  new Pointt( line.getP2());
		Pointt p = rightP.minus(leftP);
		p = p.dividedBy(p.distance()); // convert to unit vector (distance now == 1)
		if (wantLeftNormal) {
			return new Pointt(-p.y, p.x);
		}
		return new Pointt(p.y, -p.x);
	}
	
	private static Line2D.Double LeftToRightLine(Line2D.Double line) {
		// ensure that slope is calculated with xpos axis going to the right
		Point2D leftPoint = line.getX1() < line.getX2() ? line.getP1() : line.getP2();
		Point2D rightPoint = line.getX1() > line.getX2() ? line.getP1() : line.getP2();
		
		return new Line2D.Double(leftPoint, rightPoint);
	}
	
	public Line2D.Double getLine2D() {
		return new Line2D.Double(getLocation(), getExtent());
	}
	
	public Point2D.Double getLocation() {
		return new Point2D.Double(Xloc, Yloc);
	}
	
	public Point2D.Double getExtent() {
		return new Point2D.Double(Xloc + Xvel, Yloc + Yvel);
	}
	
//	private static double Slope(Line2D.Double line) {
//		
//		if (line.getX2() == line.getX1()) {
//			return ((line.getY2() - line.getY1()) < 0.0 ? -1 : 1) * Float.MAX_VALUE;
//		}
//		
//		// ensure that slope is calculated with xpos axis going to the right
//		Point2D leftPoint = line.getX1() < line.getX2() ? line.getP1() : line.getP2();
//		Point2D rightPoint = line.getX1() > line.getX2() ? line.getP1() : line.getP2();
//		
//		return (rightPoint.getY() - leftPoint.getY())/(rightPoint.getX() - leftPoint.getX());
//	}
}

