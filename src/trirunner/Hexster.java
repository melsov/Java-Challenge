package trirunner;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Hexster extends Shapester 
{
	Color color;
	
	public Hexster(double _angle, Color _color)
	{
		angle = _angle;
		color = _color;
	}
	
	@Override
	public Color getColor()
	{
		return color;
	}
	
	@Override
	public void moveALittle()
	{
//		color = new Color((color.getRed() + 1) % 255, color.getGreen(), color.getBlue(), 255);
	}
	
	@Override
    public Point2D.Double[] getPoints()
    {
    	Point2D.Double[] points = new Point2D.Double[7];
    	
    	// we need 10 angles for a star.
    	// so add 360 / 10 to the angle each time.
    	
    	double angleplus = 60d;
    	double start_angle = angle;
    	
    	for(int i = 0; i < points.length; ++i)
    	{
    		points[i] = getPointFor(x, y, height/2.0, start_angle);

    		// += is a fancy short hand for
    		// start_angle = start_angle + angleplus;
    		start_angle += angleplus; 
    	}
    	
    	return points; 
    }
}
