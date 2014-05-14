package water;

import java.awt.geom.Line2D;

public class Drop 
{
	public double Xvel;
	public double Yvel;
	public double Xloc;
	public double Yloc;
	public double Delete;
	
	public void moveInXDir() {
		Xloc += Xvel;
	}
	
	public void youCollidedWithSomething() {
		Xvel=0;
	}
	
	public void collideWithLine(Line2D.Double line) {
		
	}
	
	private static Pointt GetNormal(Line2D.Double line) {
		double slope = LineSlope(line);
		
	}
	
	private static double LineSlope(Line2D.Double line) {
		return (line.getY1() - line.getY2())/(line.getX1() - line.getX2());
	}
}

