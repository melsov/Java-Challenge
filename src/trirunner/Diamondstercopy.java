package trirunner;

/*
 * GO OVER CONCEPTS AND ASSOCIATED LINGO
 * SURROUNDING METHODS (RETURN TYPE NAME(PARAMETERS...))
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.Timer;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class Diamondstercopy extends Shapester 
{

	private Color color;

	public Diamondstercopy(double xpos, double ypos, Color someColor)
	{
		x = xpos;
		y = ypos;
		color = someColor;
	}
	
	public Color getColor(){
		return color;
	}
}
