package TheGameOfHarry;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D.Float;
import java.util.Random;

import javax.swing.Timer;

//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
@SuppressWarnings("serial")
public class Control extends Applet implements ActionListener {
	
Point2D playerLoc = new Point2D.Float(540,300);
float velAdd=(float) 0.25;
public Timer animate = new Timer(10,this);
public Keys keybinds = new Keys();
public Person player = new Person(new Point2D.Float(640,350),100,300);
public Color[] mapColor = new Color[20];
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void init(){
	setMap();
	setSize(1280,700);
	animate.start();
	addKeyListener(keybinds);
	addMouseMotionListener(keybinds);
	addMouseListener(keybinds);

}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void paint(Graphics g2){
	Graphics2D g=(Graphics2D) g2;
	player.PointAtRight=keybinds.mouseLoc;
	player.RightPoint=true;
	player.draw(g);
	drawMap(g);
	debugScreen(g);
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void actionPerformed(ActionEvent e) {
	if(keybinds.Wpress){player.vel.setLocation(player.vel.getX(),player.vel.getY()-velAdd);}
	if(keybinds.Apress){player.vel.setLocation(player.vel.getX()-velAdd,player.vel.getY());}
	if(keybinds.Spress){player.vel.setLocation(player.vel.getX(),player.vel.getY()+velAdd);}
	if(keybinds.Dpress){player.vel.setLocation(player.vel.getX()+velAdd,player.vel.getY());}
	player.vel.setLocation(player.vel.getX()*0.9,player.vel.getY()*0.9);
	playerLoc.setLocation(playerLoc.getX()+player.vel.getX(),playerLoc.getY()+player.vel.getY());
	//player.loc.setLocation(Math.round((float)playerLoc.getX()*10)/10,Math.round((float)playerLoc.getY()*10)/10);
	player.loc.setLocation(playerLoc);
	repaint();
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void drawMap(Graphics2D g){
	for(int i=0;i<10;i++){
		g.setColor(mapColor[i]);
		if(i==0){g.fill(new Rectangle2D.Float(-1,500,129,100));}
		else{g.fill(new Rectangle2D.Float(i*128,500,128,100));}
	}
	for(int i=0;i<10;i++){
		g.setColor(mapColor[i+10]);
		if(i==0){g.fill(new Rectangle2D.Float(-1,600,129,100));}
		else{g.fill(new Rectangle2D.Float(i*128,600,128,100));}
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void debugScreen(Graphics2D g){
	g.drawString((player.vel.getX()+" "+player.vel.getY()),5,15);
	g.drawString(String.valueOf(Math.toDegrees(player.angle(player.RightArmStart,player.PointAtRight))),5,30);
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public int random(int lowest, int highest){
	Random rand = new Random();
	int ret=rand.nextInt(highest-lowest+1);
	ret+=lowest;
	return ret;
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
public void setMap(){
	for(int i=0;i<20;i++){
		mapColor[i]=new Color(random(68,80),random(49,61),random(0,10));
	}
}
}
