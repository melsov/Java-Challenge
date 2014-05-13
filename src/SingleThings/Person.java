package SingleThings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;

public class Person {
Point2D loc = new Point2D.Float();
Point2D vel = new Point2D.Float();
float HealthMax;
float Health;
float Size;


public Person(Point2D Location,Point2D Velocity, int maxHealth,int health,int sizePercent) 
{
	loc.setLocation(Location);
	vel.setLocation(Velocity);
	HealthMax = health;
	Health = health;
	Size = sizePercent/100;
}
public void draw(Graphics g2, int legs)
{
	Graphics2D g = (Graphics2D) g2;
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	g.setColor(Color.black);
	//head
	g.fill(new Ellipse2D.Float((float)loc.getX()-4,(float) loc.getY()-(18+legs), 8,8));
	//torso
	g.setStroke(new BasicStroke(2));
	g.draw(new Line2D.Float(cPoints(loc,0,-(legs)),cPoints(loc, 0,-(10+legs))));
	//legs
	g.draw(new Line2D.Float(cPoints(loc,0,-legs),cPoints(loc,-4,0)));
	g.draw(new Line2D.Float(cPoints(loc,0,-legs),cPoints(loc,4,0)));
	//arms
	g.setStroke(new BasicStroke((float) 1.5));
	g.draw(new Line2D.Float(cPoints(loc,-1,-(legs+8)),cPoints(loc,-8,-(legs+6))));
	g.draw(new Line2D.Float(cPoints(loc,1,-(legs+8)),cPoints(loc,8,-(legs+6))));
}

public static Point2D cPoints(Point2D point, float Xchanger,float Ychanger)
{
	float x = (float) point.getX();
	float y = (float) point.getY();
	x +=Xchanger;
	y +=Ychanger;
	return new Point2D.Float(x,y);
}



}
