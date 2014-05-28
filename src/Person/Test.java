package Person;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.Timer;

public class Test extends Applet implements ActionListener{
PersonV3 player = new PersonV3(new Point2D.Float(640,350),100,500);
Timer animate = new Timer(10,this);
Keys keys = new Keys();
public void init(){
setSize(1280,700);
animate.start();
addKeyListener(keys);
addMouseMotionListener(keys);
player.leftArm.wepType="Sword";
}
public void paint(Graphics g2){
	
Graphics2D g = (Graphics2D) g2;
player.leftArm.pointLoc(keys.mouseLoc, 10);
player.draw(g);
g.setColor(Color.black);
g.drawString("The pistol is a bit bugged when pointing towards the left.", 10, 15);
g.drawString("I dont feel like fixing that right now so just ignore it.", 10, 30);
g.drawString("Press 1,2,3,or 4 to get the different weapons.", 10, 45);
g.drawString("I like the look of this lettering.", 10, 60);
}
@Override
public void actionPerformed(ActionEvent arg0) {
if(keys.num1press){
player.leftArm.wepType="Pistol";
player.currentWeapon = WeaponType.PISTOL;
System.out.println(player.currentWeapon);
if (player.currentWeapon.equals(WeaponType.PISTOL))
	System.out.println("equal");
}
if(keys.num2press){
player.leftArm.wepType="Sword";
}
if(keys.num3press){
player.leftArm.wepType="Baton";
}
if(keys.num4press){
player.leftArm.wepType="Grenade";
}
repaint();
	
}



}
