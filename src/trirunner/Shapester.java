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
	public enum Mode {
		SNAZZY, DULL, OUTRAGEOUS
	}
    
    protected double x = 0;
    protected double y = 0;
    public double width = 50d;
    public double height = 50d;
    
    
    public double getx() {
    	return x;
    }
    
    public double gety(){
    	return y;
    }
    
    public void setx(double newX){
    	x = newX;
    }
    
    public void setY(double newY){
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
    	return new Point2D.Double[] {new Point2D.Double(x,y), new Point2D.Double(x + width, y),new Point2D.Double(x + width/2d, y + height),new Point2D.Double(x,y) };
    }

}