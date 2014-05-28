package Person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class PersonV3{
public Point2D loc = new Point2D.Float();
public Point2D vel = new Point2D.Float();
int size;
int health;
int maxHealth;

public WeaponType currentWeapon;


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

public PersonV3(Point2D location,int MaxHealth,int sizePercent){
	resetToDefault(location,new Point2D.Float(0,0),MaxHealth,sizePercent);
}
public PersonV3(Point2D location,Point2D velocity,int MaxHealth,int sizePercent){
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
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	leftArm.drawTool(g);
	rightArm.drawTool(g);
	rightLeg.drawTool(g);
	leftLeg.drawTool(g);
	torso.drawTool(g);
	shoulder.drawTool(g);
	head.drawTool(g);
	leftSclera.drawTool(g);
	rightSclera.drawTool(g);
	leftIris.drawTool(g);
	rightIris.drawTool(g);
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
leftArm.relativeEnd(-5,8);
rightArm.relativeEnd(5,8);
leftArm.tool=true;
}

//public float distanceTo(Line2D[] obs, float cx,float cy){
//Point2D fakeLoc = new Point2D.Float();
//fakeLoc.setLocation(loc);
//boolean found = false;
//while(found = false){
//for(int i=0;i<obs.length;i++){
//if(obs[i].intersects(volume(fakeLoc)))found = true;
//}
//if(new Line2D.Float(0,0,0,-700).intersects(volume(fakeLoc)))found=true;
//if(new Line2D.Float(0,-700,1280,-700).intersects(volume(fakeLoc)))found=true;
//if(new Line2D.Float(1280,0,1280,-700).intersects(volume(fakeLoc)))found=true;
//if(new Line2D.Float(1280,0,1280,0).intersects(volume(fakeLoc)))found=true;
//if(!found){
//fakeLoc.setLocation(fakeLoc.getX()+cx,fakeLoc.getY()+cy);
//}
//}
//return (float) Math.sqrt((Math.pow(fakeLoc.getX()-loc.getX(),2) + Math.pow(fakeLoc.getY()-loc.getY(),2)));
//}

public Rectangle2D volume(Point2D loc_){
Limb[] limbs = {rightLeg,leftLeg,torso,shoulder,head,leftSclera,rightSclera,leftIris,rightIris,leftArm,rightArm};
float high = (float) limbs[0].end.getY();
float low = (float) limbs[0].end.getY();
float left = (float) limbs[0].end.getX();
float right = (float) limbs[0].end.getX();
for(int i=0;i<limbs.length;i++){
	if(limbs[i].end.getY()<high)high=(float) limbs[i].end.getY();
	if(limbs[i].start.getY()<high)high=(float) limbs[i].start.getY();
	if(limbs[i].end.getY()>low)low=(float) limbs[i].end.getY();
	if(limbs[i].start.getY()>low)low=(float) limbs[i].start.getY();
	if(limbs[i].end.getX()<left)left=(float) limbs[i].end.getX();
	if(limbs[i].start.getX()<left)left=(float) limbs[i].start.getX();
	if(limbs[i].end.getX()>right)right=(float) limbs[i].end.getX();
	if(limbs[i].start.getX()>right)right=(float) limbs[i].start.getX();
}
return new Rectangle2D.Float((float)loc_.getX(),(float)loc_.getY(),low-high,right-left);
}


}