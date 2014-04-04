package trirunner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.Timer;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class Diamondster extends Shapester 
{

	private Color color;
	//Constructor V
	public Diamondster(double xpos, double ypos, Color someColor)
	{
		x = xpos;
		y = ypos;
		color = someColor;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Point.Double[] getPoints()
    	{
		return new Point2D.Double[] {
		new Point2D.Double(x,y),
		new Point2D.Double(x+(width/2),y-(height)),
		new Point2D.Double(x,y-height*2),    
		new Point2D.Double(x-(width/2),y-(height)),
		new Point2D.Double(x,y),
		};
		
	}
}
