package Person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.*;

public class PersonV2{
public Point2D loc = new Point2D.Float();
public Point2D vel = new Point2D.Float();
int size;
int health;
int maxHealth;

Limb rightArm;
Limb leftArm;
Limb rightLeg;
Limb leftLeg;
Limb torso;
Limb shoulder;
Limb head;
Limb leftSclera;
Limb rightSclera;
Limb leftIris;
Limb rightIris;

public PersonV2(Point2D location,int MaxHealth,int sizePercent){
	resetToDefault(location,new Point2D.Float(0,0),MaxHealth,sizePercent);
}
public PersonV2(Point2D location,Point2D velocity,int MaxHealth,int sizePercent){
resetToDefault(location,velocity,MaxHealth,sizePercent);
}
public void resetToDefault(Point2D location,Point2D velocity,int MaxHealth,int sizePercent){
	loc.setLocation(location);
	vel.setLocation(velocity);
	size=sizePercent/100;
	maxHealth = MaxHealth;
	health = maxHealth;
	
	//example = new Limb(base,X distance to base,Y distance to base, thickness, color, size)
shoulder = new Limb(loc,-3,-24,(float) 1.5,Color.black,size);
leftArm = new Limb(loc,-3,-24,(float) 1.5,Color.black,size);
rightArm = new Limb(loc,3,-24,(float) 1.5,Color.black,size);
rightLeg = new Limb(loc,0,-11,(float) 2,Color.black,size);
leftLeg = new Limb(loc,0,-11,(float) 2,Color.black,size);
torso = new Limb(loc,0,-27,(float) 2,Color.black,size);
head = new Limb(loc,0,-31,10,Color.black,size);
leftSclera = new Limb(loc,-2,-34,2,Color.white,size);
rightSclera = new Limb(loc,2,-34,2,Color.white,size);
leftIris= new Limb(loc,(float) -1.5,-34,(float) 0.5,Color.blue,size);
rightIris = new Limb(loc,(float) 2.5,-34,(float) 0.5,Color.blue,size);
}
public void draw(Graphics2D g){
	updateFacts();
	leftArm.drawLine(g);
	rightArm.drawLine(g);
	rightLeg.drawLine(g);
	leftLeg.drawLine(g);
	torso.drawLine(g);
	shoulder.drawLine(g);
	head.drawLine(g);
	leftSclera.drawLine(g);
	rightSclera.drawLine(g);
	leftIris.drawLine(g);
	rightIris.drawLine(g);
}

private void updateFacts(){
rightLeg.relativeEnd(4,11);
leftLeg.relativeEnd(-4,11);
torso.relativeEnd(0,16);
shoulder.relativeEnd(6, 0);
head.relativeEnd(0, -2);
leftSclera.relativeEnd(0,1);
rightSclera.relativeEnd(0, 1);
leftIris.relativeEnd(0,0);
rightIris.relativeEnd(0,0);
//leftArm.relativeEnd(-5,8);
leftArm.pointLoc(MouseInfo.getPointerInfo().getLocation(), 10);
rightArm.relativeEnd(5,8);
}
//private Point2D cp(Point2D start,float xc,float yc){
//return new Point2D.Float((float)start.getX()+size*xc,(float)start.getY()+size*yc);
//}

public float angle(Point2D center,Point2D at){
return (float)(Math.atan2(-(center.getY() - at.getY()), -(center.getX() - at.getX())));
}


public Point2D rotateStuff(Point2D center,Point2D at, float length){
float a = (float)Math.toRadians(Math.toDegrees(angle(center,at))-90);
Point2D returny = new Point2D.Float();
float newX = (float) Math.cos(a);
float newY = (float) Math.sin(a);
newX*=length;
newY*=length;
returny.setLocation(center.getX()+newX,center.getY()+newY);
return returny;
}
}