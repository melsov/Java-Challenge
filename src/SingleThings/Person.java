package SingleThings;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public class Person {

public Point2D loc = new Point2D.Float();
public Point2D vel = new Point2D.Float();
public float HealthMax;
public float Health;
public float Size;
public boolean showHealthBar;
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Point2D LeftArmEnd = new Point2D.Float(0,0);
public Point2D RightArmEnd = new Point2D.Float(0,0);
public Point2D LeftLegEnd = new Point2D.Float(0,0);
public Point2D RightLegEnd = new Point2D.Float(0,0);
public Point2D TorsoEnd = new Point2D.Float(0,0);
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Point2D LeftArmStart = new Point2D.Float(0,0);
public Point2D RightArmStart = new Point2D.Float(0,0);
public Point2D LeftLegStart = new Point2D.Float(0,0);
public Point2D RightLegStart = new Point2D.Float(0,0);
public Point2D TorsoStart = new Point2D.Float(0,0);
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Line2D LeftArm = new Line2D.Float();
public Line2D RightArm = new Line2D.Float();
public Line2D LeftLeg = new Line2D.Float();
public Line2D RightLeg = new Line2D.Float();
public Line2D Torso = new Line2D.Float();
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Point2D PointAtLeft = new Point2D.Float();
public Point2D PointAtRight = new Point2D.Float();
public boolean RightPoint = false;
public boolean LeftPoint = false;
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Point2D ShoulderLeft = new Point2D.Float();
public Point2D ShoulderRight = new Point2D.Float();
public Line2D Shoulder = new Line2D.Float();
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public boolean dead;
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Person(Point2D Location, int maxHealth,int sizePercent) {
dead = false;
loc.setLocation(Location);
vel.setLocation(0,0);
HealthMax = maxHealth;
Health = maxHealth;
Size = sizePercent;
		}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void draw(Graphics g2){
Graphics2D g = (Graphics2D) g2;
doDraw(g);
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void draw(Graphics2D g){
doDraw(g);
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
private void doDraw(Graphics2D g){
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	g.setColor(Color.black);
	updateFacts();
	g.fill(new Ellipse2D.Float((float)loc.getX()+size(-4),(float) loc.getY()+size(-30), size(8),size(8)));
	g.setStroke(new BasicStroke(size(2),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
	g.draw(Torso);
	g.draw(LeftLeg);
	g.draw(RightLeg);
	g.setStroke(new BasicStroke((size((float) 1.5)),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
	g.draw(Shoulder);
	g.draw(LeftArm);
	g.draw(RightArm);
	
	if(showHealthBar){
	g.setStroke(new BasicStroke(3));
	if(Health>0){
	g.setColor(Color.green);
	g.draw(new Line2D.Float(cPoints(loc,size(-15),size(-36)), cPoints(loc,(size(-15)+(size(30)*(Health/HealthMax))),size(-36))));
	}else{
	dead=true;
	}
	if(Health<100){
	g.setColor(Color.red);
	g.draw(new Line2D.Float(cPoints(loc,(size(-15)+(size(30)*(Health/HealthMax))),size(-36)),cPoints(loc,size(15),size(-36)) ));
	}
	}	
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void setHealth(int NewHealth){
	if(NewHealth>HealthMax){
	Health=HealthMax;
	}else if (NewHealth>0){
	Health=NewHealth;
	}else{
dead=true;
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void changeHealth(int addHealth){
	if(addHealth+Health<HealthMax){
	Health=HealthMax;
	}else if(Health+addHealth>0){
	Health+=addHealth;
	}else{
dead=true;
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public static Point2D cPoints(Point2D point, float Xchanger,float Ychanger){
float x = (float) point.getX();
float y = (float) point.getY();
x +=Xchanger;
y +=Ychanger;
return new Point2D.Float(x,y);
}
private float size(float num){
return ((Size/100)*num);
}

//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
private void updateFacts(){

LeftArmStart.setLocation(cPoints(loc,size(-2),size(-20)));
if(LeftPoint){
LeftArmEnd.setLocation(rotateStuff(LeftArmStart,PointAtLeft,size(10)));
}else{
LeftArmEnd.setLocation(cPoints(loc,size(-5),size(-11)));	
}
LeftArm.setLine(LeftArmStart,LeftArmEnd);


RightArmStart.setLocation(cPoints(loc,size(2),size(-20)));
if(RightPoint){
RightArmEnd.setLocation(rotateStuff(RightArmStart,PointAtRight,size(10)));
}else{
RightArmEnd.setLocation(cPoints(loc,size(5),size(-11)));	
}
RightArm.setLine(RightArmStart,RightArmEnd);

LeftLegStart.setLocation(cPoints(loc,size(0),size(-12)));
LeftLegEnd.setLocation(cPoints(loc,size(-4),size(0)));
LeftLeg.setLine(LeftLegStart, LeftLegEnd);


RightLegStart.setLocation(cPoints(loc,size(0),size(-12)));
RightLegEnd.setLocation(cPoints(loc,size(4),size(0)));
RightLeg.setLine(RightLegStart, RightLegEnd);

TorsoStart.setLocation(cPoints(loc,size(0),size(-12)));
TorsoEnd.setLocation(cPoints(loc,size(0),size(-22)));
Torso.setLine(TorsoStart, TorsoEnd);

ShoulderLeft.setLocation(cPoints(loc,size(-2),size(-20)));
ShoulderRight.setLocation(cPoints(loc,size(2),size(-20)));
Shoulder.setLine(ShoulderLeft,ShoulderRight);

}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Rectangle2D findLoc(){
	return new Rectangle2D.Float((float)loc.getX()+size(-8),0,(float)loc.getX()+size(8),(float)loc.getY()+size(-38));
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public float angle(Point2D center,Point2D at){
return (float)(Math.atan2(-(center.getY() - at.getY()), -(center.getX() - at.getX())));
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public Point2D rotateStuff(Point2D center,Point2D at, float length){
float a = angle(center,at);
Point2D returny = new Point2D.Float();
float newX = (float) Math.cos(a);
float newY = (float) Math.sin(a);
newX*=length;
newY*=length;
returny.setLocation(center.getX()+newX,center.getY()+newY);
return returny;
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=

}
