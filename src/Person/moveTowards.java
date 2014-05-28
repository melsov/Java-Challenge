package Person;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;


public class moveTowards {
public static void main(String[] args){

}
public static Point2D myWay(Point2D start,Point2D find,float length){
float dX = (float) (find.getX() - start.getX());
float dY = (float) (find.getY() - start.getY());
float distance = (float) Math.sqrt(Math.pow(0, 0)+Math.pow(dY, 2));
float xRel=dX/distance;
float yRel=dY/distance;
return new Point2D.Float((float)start.getX()+xRel,(float)start.getY()+yRel);
}
public static Point2D yourWay(Point2D start,Point2D find,float length){
	float dirX = (float) (find.getX() - start.getX());
	float dirY = (float) (find.getY() - start.getY());
	float dirLen = (float) Math.sqrt(dirX * dirX + dirY * dirY); // The length of dir
	dirX = dirX / dirLen;
	dirY = dirY / dirLen;
	float lineX = dirX* length;
	float lineY = dirY* length;
	return new Point2D.Float((float)start.getX()+lineX,(float)start.getY()+lineY);
}

}
