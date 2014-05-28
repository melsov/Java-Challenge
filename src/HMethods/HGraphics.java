package HMethods;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class HGraphics{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public static void Circle(Graphics g2,Point2D center, float radius,int penSize,boolean fill){
Graphics2D g = (Graphics2D) g2;
g.setRenderingHints(new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g.setStroke(new BasicStroke(penSize));
if(fill){
g.fill(new Ellipse2D.Double(center.getX()-radius,center.getY()-radius,radius*2,radius*2));	 
 }else{
g.draw(new Ellipse2D.Double(center.getX()-radius,center.getY()-radius,radius*2,radius*2));			 
	 }
}
}
