package SingleThings;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.IOException;

import javax.swing.Timer;



public class Testy extends Applet implements ActionListener{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
TextUtil test = new TextUtil("Stuff.txt",100);
Keys mouse = new Keys();
Timer yolo = new Timer(10,this);
Person player = new Person(new Point2D.Float(250,250),100,500);

public void init(){
setSize(500,500);
yolo.start();
addMouseMotionListener(mouse);

}
public void paint(Graphics g){
player.showHealthBar=true;
player.LeftPoint=false;
player.RightPoint=false;
player.PointAtLeft=mouse.mouseLoc;
player.PointAtRight=mouse.mouseLoc;
player.draw(g);
}
@Override
public void actionPerformed(ActionEvent arg0) {
repaint();
	
}
	

}
