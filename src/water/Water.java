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

public class Water extends Applet implements ActionListener,KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Double> Xvel = new ArrayList<Double>();
	List<Double> Yvel = new ArrayList<Double>();
	List<Double> Xloc= new ArrayList<Double>();
	List<Double> Yloc= new ArrayList<Double>();
	List<Double> Delete = new ArrayList<Double>();
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
	
	private Keys keys = new Keys();
	private Keys otherKeys = new Keys();
	
	private Timer otherTimer;
	
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
		setSize(1280,675);
		
		animate = new Timer(25,this);
		otherTimer = new Timer(100,this);
		animate.start();
		otherTimer.start();
//		addKeyListener(this);
		addKeyListener(keys);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
//		if (e.getSource() == otherTimer) {
//			System.out.println("got an event from the other timer");
//			// ... do other timer stuff
//			
//			// now we should probably leave the actionPerformed method
//			// unless there's stuff that we want to do when either
//			// timer fires.
//			return; 
//		} else {
//			System.out.println("got an event from the animate timer");
//		}
		
		if (Keys.Apress) {
			System.out.println("a is down");
		}
		
		Point point = MouseInfo.getPointerInfo().getLocation();
		MX = point.getX();
		MY = point.getY();
		repaint();	
	}


	public void newDrop(double Sx,double Sy)
	{

		
		Xloc.add(Sx);
		Yloc.add(Sy);
		Xvel.add(50*Math.sin(superAngle));
		Yvel.add(50*Math.cos(superAngle));
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
		int l = Xvel.size();
		for(int i=0;i<l;i++)
		{
			p1.setLocation(Xloc.get(i), Yloc.get(i));
			p2.setLocation(Xloc.get(i)+ Xvel.get(i),Yloc.get(i)+ Yvel.get(i));
			line.setLine(p1,p2);
			g.draw(line);
			Xloc.set(i,Xloc.get(i) + Xvel.get(i));
			Yloc.set(i,Yloc.get(i) + Yvel.get(i));
			Yvel.set(i,Yvel.get(i)+ 2);
			if(Yloc.get(i)>waterHeight){
				if(0<Yvel.get(i)){
				Yloc.remove(i);
				Xloc.remove(i);
				Yvel.remove(i);
				Xvel.remove(i);
				i--;
				deleted++;
				waterHeight-=0.25;
			}}
		l=Xvel.size();
		}
	}

	public void debug(boolean debug){
		frames++;
//		System.out.print(frames + " = frame number | ");
//		System.out.print("list size: "+ Xloc.size()+ " | ");
//		System.out.print("new drops = " + added + " | deleted drops = " + deleted + " | " );
//		System.out.print("water level: " + (675-waterHeight) + "(" + waterHeight + ") | " );
//		System.out.println("end of frame.");
		added=0;
		deleted=0;
		
	}

	public void fillWater(Graphics2D g){
		g.setPaint(Color.cyan);
		g.setStroke(new BasicStroke(1));
		
		if (keys.Apress) return;
		
		for(double i = 675; i>waterHeight-1;i-=0.25){
			line.setLine(0,i,1280,i);
			g.draw(line);	
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Water class got a key");
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
