package water;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class Water2 extends Applet implements ActionListener,KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Drop> drops = new ArrayList<Drop>();
	public static Timer animate;
	Line2D line = new Line2D.Double();
	public double MX;
	public double MY;
	Point2D p1 = new Point2D.Double();
	Point2D p2 = new Point2D.Double();
	int frames = 0;
	double superAngle;
	int added=0;
	int deleted=0;
	double waterHeight = 675;
	boolean space;
	
	
	public void paint(Graphics g2)
	{
		Graphics2D g = (Graphics2D) g2;
		g.clearRect(0, 0, 1280, 675);
		superAngle=getAngle(640,674,MX,MY);
		newDrop(640,674);
		fillWater(g);
		drawWater(g);
		debug(true);
	}

	public void init(){
		animate = new Timer(25,this);
		setSize(1280,675);
		animate.start();
		addKeyListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		Point point = MouseInfo.getPointerInfo().getLocation();
		MX = point.getX();
		MY = point.getY();
		repaint();	
	}
	
	public void newDrop(double Sx,double Sy)
	{

		Drop the_drop = new Drop();
		the_drop.Xloc = Sx;
		the_drop.Yloc = Sy;
		the_drop.Xvel = 50*Math.sin(superAngle);
		the_drop.Yvel = 50*Math.cos(superAngle);
		drops.add(the_drop);
		added++;
	}
	
	public static double getAngle(double CurrentX, double CurrentY, double FindX, double FindY)
	{
		double oneEightyInRadians = Math.PI;
		double ninetyInRadians = Math.PI * .5;
		double direction;
		if(CurrentY-FindY==0)
		{
			if(CurrentX-FindX > 0){
				direction = -ninetyInRadians;
			}else{
				direction = ninetyInRadians;
			}
		}else{
			direction = Math.atan((CurrentX-FindX)/(CurrentY-FindY));
		}
			
		if(CurrentY-FindY < 0){
			direction+=oneEightyInRadians;
		}
		direction+=oneEightyInRadians;
//		return modular(direction,360);
		return modular(direction,oneEightyInRadians * 2);
	}
	
	public static double modular(double number, double mod){
		while(number>mod){
			number-=mod;	
		}
		while(number<0){
			number = mod - number;	
		}
		return number;
	}

	public void drawWater(Graphics2D g){
		g.setPaint(Color.blue);
		g.setStroke(new BasicStroke(4));
		int l = drops.size();
		for(int i=0;i<l;i++)
		{
			Drop d = drops.get(i);
			p1.setLocation(d.Xloc, d.Yloc);
			p2.setLocation(d.Xloc + d.Xvel,  d.Yloc + d.Yvel);
			line.setLine(p1,p2);
			g.draw(line);
//		d.Xloc.set(d,d.Xloc + d.Xvel);
			d.Xloc+=d.Xvel;
			d.Yloc+=d.Yvel;
//			d.Yvel-=2;
			d.Yvel+=2;
			if(d.Yloc>waterHeight){
			if(0<d.Yvel){
				drops.remove(i);
				i--;
				deleted++;
				waterHeight-=0.25;
			}}
		l=drops.size();
		}
	}



	public void debug(boolean debug){
		frames++;
		System.out.print(frames + " = frame number | ");
		System.out.print("list size: "+ drops.size()+ " | ");
		System.out.print("new drops = " + added + " | deleted drops = " + deleted + " | " );
		System.out.print("water level: " + (675-waterHeight) + "(" + waterHeight + ") | " );
		System.out.println("end of frame.");
		added=0;
		deleted=0;
		
	}

	public void fillWater(Graphics2D g){
		g.setPaint(Color.cyan);
		g.setStroke(new BasicStroke(1));
		for(double i = 675; i>waterHeight-1;i-=0.25){
			line.setLine(0,i,1280,i);
			g.draw(line);	
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		if(pressed == KeyEvent.VK_SPACE){
			waterHeight+=10;
			if(waterHeight>675){
			waterHeight=675;	
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
