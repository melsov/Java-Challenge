package Person;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;



public class Tool{
private float size;
private Point2D loc = new Point2D.Float();
private float angle;
public String type;
public boolean show;
boolean backwards;

public Tool(float size_,Point2D loc_,float angle_,String type_){
set(size_,loc_,angle_,type_);	
}

public void set(float size_,Point2D loc_,float angle_,String type_){
size=size_;
loc.setLocation(loc_);
angle=angle_;
type=type_;
show = true;
}

public void draw(Graphics2D g){
if(Math.abs(angle)>90){
backwards=true; 
}else{ 
backwards=false;
}
if(show){
if(type=="Pistol") drawPistol(g);
if(type == "Sword") drawSword(g);
if(type == "Baton") drawBaton(g);
if(type == "Grenade") drawGrenade(g);
}
}

private Point2D rel(Point2D center,float angle_,float DX,float DY){
DX*=size;
DY*=size;
if(backwards){
DX*=-1;
DY*=-1;
}
float newX=(float) (center.getX()+(Math.cos(Math.toRadians(angle_)*-1)*DX));
float newY=(float) (center.getY()+(Math.sin(Math.toRadians(angle_)*1)*DX));
newX+=(Math.cos(Math.toRadians(angle_+90))*(DY*1));
newY+=(Math.sin(Math.toRadians(angle_+90))*DY);
return new Point2D.Float(newX,newY);
}

private void drawSword(Graphics2D g){
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g.setColor(new Color(153,153,153,255));
g.setStroke(new BasicStroke(3*size,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
g.draw(new Line2D.Float(rel(loc,angle,0,-4),rel(loc,angle,0,-20)));
g.setColor(new Color(93,93,93,255));
g.setStroke(new BasicStroke((float) (0.5*size),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
g.draw(new Line2D.Float(rel(loc,angle,0,-2),rel(loc,angle,0,-19)));
g.setColor(new Color(143,143,143,255));
g.setStroke(new BasicStroke(2*size));
g.draw(new Line2D.Float(rel(loc,angle,-3,(float) -2.5),rel(loc,angle,3,(float) -2.5)));
g.draw(new Line2D.Float(rel(loc,angle,0,0),rel(loc,angle,0,(float) -2.5)));
}

private void drawGrenade(Graphics2D g){
g.setColor(new Color(0,109,12,255));
g.setStroke(new BasicStroke(1*size));
g.draw(new Line2D.Float(rel(loc,angle,0,0),rel(loc,angle,0,(float) -2.25)));
g.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angle), loc.getX() ,loc.getY()));
g.setColor(new Color(0,135,13,255));
g.fill(new Ellipse2D.Float((float) ((float)loc.getX()-(1.25*size)),(float)loc.getY()-(2*size),(float) (2.5*size),4*size));
g.setTransform(AffineTransform.getRotateInstance(Math.toRadians(0)));
}

private void drawBaton(Graphics2D g){
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g.setColor(Color.black);
g.setStroke(new BasicStroke(1*size));
g.draw(new Line2D.Float(rel(loc,angle,0,2),rel(loc,angle,0,-16)));
g.draw(new Line2D.Float(rel(loc,angle,0,-2),rel(loc,angle,3,-2)));
}

private void drawPistol(Graphics2D g){
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g.setColor(Color.gray);
g.setStroke(new BasicStroke(size*2));
g.draw(new Line2D.Float(rel(loc,angle,0,1),rel(loc,angle,0,-1)));
g.draw(new Line2D.Float(rel(loc,angle,0,-1),rel(loc,angle,5,-1)));
}
}