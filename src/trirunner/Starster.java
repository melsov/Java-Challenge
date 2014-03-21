package trirunner;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Starster extends Shapester 
{
	Color color; //NOT IN USE BUT MAKE A SIMILAR MEMBER VARIABLE FOR DIAMONDSTER
	
	//Starster's constructor method
	//unlike shapester. it has parameters
	public Starster(double _x, double _y)
	{
		x = _x;
		y = _y;
	}
	
	//You're allowed to have multiple constructor methods
	//This is Starsters 'default' constructor
	public Starster()
	{
		x = 0;// actually unnecessary since these default to zero anyway.
		y = 0;
	}
    
	// Starster is "overriding" the getColor()
	// method it inherited from Shapester
	// the '@Override' thing is optional but nice
	// because the compiler will check to make sure
	// that you are actually overriding a method 
	// that you inherited (i.e. not misspelling or otherwise
	// out-to-lunch in some way.)
	
	@Override
	public Color getColor()
	{
		// color constructor takes (RED_AMOUNT, GREEN_AMOUNT, BLUE_AMOUNT, TRANSPARENCY_AMOUNT) 
		// transparency often called 'alpha'
//		return new Color(0, 255, 0, 255);  // green
		return color;
	}
	
// OVERRIDE MOVEALITTLE
// SO THAT STARSTER SPINS
// WHILE IT MOVES
	
	@Override
	public void moveALittle()
	{
		super.moveALittle(); // super refers Shapester. Starster's 'super' class
		angle += 2;
	}
	
	@Override
    public Point2D.Double[] getPoints()
    {
    	Point2D.Double[] points = new Point2D.Double[11];
    	
    	// we need 10 angles for a star.
    	// so add 360 / 10 to the angle each time.
    	
    	double angleplus = 36d;
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
    		if (i % 2 == 0) 
    			radius = height/1.8d;
    		
    		points[i] = getPointFor(x, y, radius, start_angle);

    		// += is a fancy short hand for
    		// start_angle = start_angle + angleplus;
    		start_angle += angleplus; 
    	}
    	
    	return points; 
    }
	
	

	
}
