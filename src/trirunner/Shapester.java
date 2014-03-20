package trirunner;


import java.awt.*;
//import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

/* 
 * This is like the FontDemo applet in volume 1, except that it 
 * uses the Java 2D APIs to define and render the graphics and text.
 */

public class Shapester
{
	//This is an 'enum'
	// not in use right now...
	public enum Mode {
		SNAZZY, DULL, OUTRAGEOUS
	}
    
	// protected is a variation on private
	// where as "private" means no one can see this variable except me.
	// "protected" means "no one can see this variable except me and my 'sub classes.' (classes that inherit from me)
    protected double x = 0;
    protected double y = 0;
    public double width = 50d;
    public double height = 50d;
    protected double angle = 0;
    
    // Shapesters constructor method
    // called when someone calls
    // 'new Shapester()'
    public Shapester()
    {
    	
    }
    
    // ACCESSOR METHODS
    // The following four methods are "getters and setters"
    // or "accessor methods." There is nothing tricky about 
    // the way they work--they do exactly what they look like they do.
    // also, in this case, they are totally superfluous:
    // we could have saved a bunch of typing by just
    // saying that x and y were 'public' instead of protected
    // and gotten the same result.
    // However, they represent an important principle, namely
    // that its often good for a class to control how and when 
    // its member variables are accessed and set for...a number of reasons
    // for example, conceivably there might be a reason for
    // Shapester to want to know when its x or y was set
    // and to optionally do something when that happens.
    // for another example, a getter method can serve
    // as a 'fake' member variable:
    // public double rightEdge()
    // could represent the right edge position of the shape
    // As far as other classes were concerned 'rightEdge()' would 
    // be just like a public variable, but Shapester wouldn't actually 
    // have to store a variable called: 'double rightEdgePosition'
    // in memory. It could just return "x + width"
    // In fact, storing 'rightEdgePosition' as a variable
    // would be terrible since then, whenever x was updated
    // rightEdgePositon would also have to be updated to be useful.
    // this would create more work and invite
    // bugs.
    public double getx() {
    	return x;
    }
    public double gety(){
    	return y;
    }
    public void setx(double newX){
    	x = newX;
    }
    public void sety(double newY){
    	y = newY;
    }
    
    public void moveALittle()
    {
    	x += 0.1;
    	y += 0.1;
    }
    
    public Color getColor(){
    	return Color.red;
    }
    
    public Point.Double[] getPoints()
    {
    	return new Point2D.Double[] {
    			new Point2D.Double(x,y), 
    			new Point2D.Double(x + width, y),
    			new Point2D.Double(x + width/2d, y + height),
    			new Point2D.Double(x,y) };
    }
    
	//used by Starster
	protected Point2D.Double getPointFor(double centerX, double centerY, double radius, double angle_in_degrees)
	{
		// YOU DON'T NEED TO KNOW OR USE THIS FOR DIAMONDSTER
		// SKIP THE MATH EXPLANATION.
		
		// Math explanation: "cos" and "sin" (pronounced "cose" and "sine")
		// convert an angle into its horizontal and vertical parts
		
		// For example, if a flag pole is partially bent over, and the sun is directly over head
		
		// length_of_flag_pole * cos(angle_between_flag_pole_and_ground) = the length of the shadow under the flag pole.
		// length_of_flag_pole * sin(angle_between_flag_pole_and_ground) = how high up the tip of the flag pole is.

		// so handy for, among other things, making stars, hexagons, etc.		
		
		Point2D.Double result = new Point2D.Double();
		result.x = x + radius * Math.cos(Math.toRadians(angle_in_degrees));
		result.y = y + radius * Math.sin(Math.toRadians(angle_in_degrees));
		return result;
	}    

}