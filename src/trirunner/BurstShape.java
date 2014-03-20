package trirunner;

import java.awt.Color;
import java.awt.geom.Point2D;

public class BurstShape extends Shapester 
{
	Color color;
	public BurstShape(Color _color, double start_angle, double _x, double _y)
	{
		color = _color;
		angle = start_angle;
		x = _x;
		y = _y;
	}
	
	@Override
	public Color getColor()
	{
		return color;
	}
	
	@Override
	public void moveALittle()
	{
		angle += .3d;
	}
	
	@Override
    public Point2D.Double[] getPoints()
    {
    	Point2D.Double[] points = new Point2D.Double[9];
    	
    	// we need 10 angles for a star.
    	// so add 360 / 10 to the angle each time.
    	
    	double angleplus = 360d / (12d * 6d);
    	double start_angle = angle;
    	
    	for(int i = 0; i < points.length; ++i)
    	{
    		// alternate the length of the radius by odds and even 'i'.
    		// so that every other x,y is further out
    		// making the 5 points in the star
    		
    		// i % 2 == 0
    		// means "is the remainder of this number divided by two equal to zero?"
    		// the % is called the 'modulo' symbol 
    		// and i % 2 is often called 'i mod two'
    		// and means "divide i by 2 and give me the remainder"
    		
    		// recall '==' is a question meaning 'are they equal?'
    		double radius = height;
    		if (i == 0 || i == points.length - 1)
    			radius = 0;
    		else if (i % 2 == 0) 
    			radius = height/1.2d;
    		
    		points[i] = getPointFor(x, y, radius, start_angle);

    		// += is a fancy short hand for
    		// start_angle = start_angle + angleplus;
    		start_angle += angleplus; 
    	}
    	
    	return points; 
    }	
}
