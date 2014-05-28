package Person;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class Limb {
public float thick;
float size;
public Color color;
public Point2D start = new Point2D.Float();
public Point2D end = new Point2D.Float();
public Point2D startDelta = new Point2D.Float();
public Point2D endDelta = new Point2D.Float();
public Point2D ref = new Point2D.Float();
public Line2D limbLine = new Line2D.Float();
public Ellipse2D limbEllipse = new Ellipse2D.Float();
private boolean pointAtPoint;
private Point2D pointAt = new Point2D.Float();
private float length;
private Tool gun = new Tool(size, end, (float) Math.toDegrees(GetAngle(end)),"Sword");
public boolean tool;
public String wepType;

public Limb(Point2D reference,float dsX,float dsY,float thi,Color col,float siz){
thick=thi;
color=col;
ref.setLocation(reference);
size=siz;
startDelta.setLocation(dsX,dsY);
tool=false;
}

public void setLimb(Point2D reference,float dsX,float dsY,float thi,Color col,float siz){
thick=thi;
color=col;
ref.setLocation(reference);
size=siz;
startDelta.setLocation(dsX,dsY);
}
public void relativeEnd(float deX,float deY){
endDelta.setLocation(deX,deY);
}
public void relativeEnd(Point2D delta){
endDelta.setLocation(delta);
}
public float getSX(){
return (float) start.getX();
}
public float getSY(){
return (float) start.getY();
}

public void pointAtLoc(Point2D at, float length){
float a = GetAngle(at);
float newX = (float) Math.cos(a);
float newY = (float) Math.sin(a);
newX*=length;
newY*=length;
endDelta.setLocation(newX,newY);
}

public float GetAngle(Point2D at){
return (float)(Math.atan2(-(start.getY() - at.getY()), -(start.getX() - at.getX())));
}

public void drawLine(Graphics2D g){
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g.setColor(color);
start.setLocation(ref.getX()+(startDelta.getX()*size),ref.getY()+(startDelta.getY()*size));
if(pointAtPoint){pointAtLoc(pointAt,length);}
end.setLocation(start.getX()+(endDelta.getX()*size),start.getY()+(endDelta.getY()*size));
g.setStroke(new BasicStroke(size*thick,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
limbLine.setLine(start,end);
g.draw(limbLine);

}
public void drawTool(Graphics2D g){
gun.set(size, end,(float) Math.toDegrees(GetAngle(end)),wepType);
if(tool){gun.draw(g);}
}

public void dontPoint(){
pointAtPoint=false;
}
public void pointLoc(Point2D at,float Length){
pointLoc((float)at.getX(),(float)at.getY(),Length);
}
public void pointLoc(float x,float y,float Length){
pointAtPoint=true;
pointAt.setLocation(x,y);
length=Length;
}
}
