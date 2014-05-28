package HMethods;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Float;

public class test extends Applet{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
Point2D place = new Point2D.Float();
public void init(){
	setSize(1280,675);

}
public void paint(Graphics g){
Graphics2D g2 = (Graphics2D) g;
	place.setLocation(640, 350);
	g2.setColor(Color.red);
	g2.draw(new Line2D.Float(place,HMath.relativeMoveZeroUp(place, (float) -90, 0, 100)));
	g2.setColor(Color.green);
	g2.draw(new Line2D.Float(place,HMath.relativeMoveZeroUp(place, (float) -90, 100, 100)));
	g2.setColor(Color.blue);
	g2.draw(new Line2D.Float(HMath.relativeMoveZeroUp(place, (float) -90, 100, 100),HMath.relativeMoveZeroUp(place, (float) -90, 0, 100)));
	g2.draw( new Ellipse2D.Float((float)place.getX()-2,(float)place.getY()-2,4,4));
}
}
