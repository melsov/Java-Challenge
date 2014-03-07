package trirunner;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Zigster extends Shapester 
{
    
	public Color getColor()
	{
		return Color.green;
	}
	
    public Point2D.Double[] getPoints()
    {
    	height = 60;
    	Point2D.Double[] points = new Point2D.Double[6];
    	
    	double angle = 72d;
    	points[0] = new Point2D.Double(x,y);
    	
    	double newx = x;
    	double newy = y;
    	
    	for(int i = 1; i < points.length; ++i)
    	{
    		newx +=  height * Math.cos(Math.toRadians(angle));
    		newy +=  height * Math.sin(Math.toRadians(angle));
    		
    		points[i] = new Point2D.Double(newx,newy);
    		angle = angle * -1;
    	}
    	
    	return points; 
    }
}
