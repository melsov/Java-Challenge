package drawbot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import static java.lang.Math.*;

public class DrawBot extends JPanel implements ActionListener, KeyListener {

	Machine machine = new Machine();
	Pointt curPoint = new Pointt();
	DrawPointProvider drawPointProvider = new DrawPointProvider();
	public static  int CanvasWidth = 1200;
	public static  int CanvasHeight = 800;
	
	private Image canvas; /*off-screen image*/
	private Graphics cg; /*the graphics of the off-screen image*/
	
	private Color[] penColors = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK};
	private int penColorIndex = 0;
	
	private MotorSprite motorSpriteL;
	private MotorSprite motorSpriteR;
	
	private LineSprite lineSpriteL;
	private LineSprite lineSpriteR;
	
	private Pointt _targetPoint = new Pointt();
	
	
	public DrawBot()
	{
	}
	
	public void addNotify() {
		super.addNotify();
		this.addKeyListener(this);
		this.setFocusable(true);
		setupCanvas();
		motorSpriteL = new MotorSprite(machine.getMotor(false), false);
		motorSpriteR = new MotorSprite(machine.getMotor(true), true);
		lineSpriteL = new LineSprite(machine.getMotor(false), false);
		lineSpriteR = new LineSprite(machine.getMotor(true),true);
		Timer t = new Timer(20, this);
		t.start();
	}
	
	public void paint(Graphics g)
    {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(canvas, 0, 0, null);
		
		Pointt leftPos = machine.motorLocationL;
		Pointt rightPos = machine.motorLocationR; 
		Motor left = machine.getMotor(false);
		Motor right = machine.getMotor(true);
		double leftAng = machine.testAngleForSide(false);
		double rightAng = machine.testAngleForSide(true);
		double yL = left.stringLength() * sin(leftAng);
		double xL = left.stringLength() * cos(leftAng);
		double yR = right.stringLength() * sin(rightAng);
		double xR = machine.machineWidth - right.stringLength() * cos(rightAng);
		
		lineSpriteL.drawLine(g2, leftPos, new Pointt(xL,yL) );
		lineSpriteR.drawLine(g2, rightPos, new Pointt(xR,yR) );
		
		motorSpriteL.drawMotor(g2, leftPos);
		motorSpriteR.drawMotor(g2, rightPos.minus(new Pointt(right.gearDiameter,0)));
		drawPoint(g2, _targetPoint, Color.MAGENTA);
		drawPoint(g2, machine.departurePoint, Color.CYAN);
		drawPoint(g2, machine.cheatPoint, Color.PINK);
		drawPoint(g2, machine.cheatPointForPrev, Color.GREEN);
//		g2.draw(machine.departureToTargetLine().arrow());
    }
	private void drawPoint(Graphics2D g, Pointt p, Color c) {
		int w = 22, h = 8;
		Ellipse2D e = new Ellipse2D.Double(p.x - w/2.0, p.y - h/2.0, w,h);
		g.setPaint(c);
		g.fill(e);
		Ellipse2D el = new Ellipse2D.Double(p.x - h/2.0, p.y - w/2.0, h,w);
		g.draw(el);
	}

	public static void main(String[] args) {
		JFrame fr = new JFrame();
		
		//what are the dimensions of this screen?
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		//STATIC VARS
		CanvasWidth = (int) dim.getWidth();
		CanvasHeight = (int) dim.getHeight();
		
		fr.setSize(CanvasWidth, CanvasHeight );
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setUndecorated(true);

		//get a 'device' object--whatever that is...
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(fr);
		
		DrawBot db = new DrawBot(); 
		db.setSize(CanvasWidth, CanvasHeight);
		fr.add(db);
		fr.setVisible(true);
		fr.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (pause) return;
		updateMachine();
		drawOnCanvas();
		repaint();
//		if (advanceOne) {
//			pause = true;
//		}
	}
	
	private void updateMachine() {
		if (machine.readyForNextPoint()) {
			_targetPoint = nextPoint();
			machine.moveToPoint(_targetPoint);
			penColorIndex = (penColorIndex + 1) % penColors.length;
		}
		curPoint = machine.penLocation();
	}
	
	private Pointt nextPoint() {
		return drawPointProvider.nextPoint();
	}
	
	private void drawOnCanvas() {
		Graphics2D cgg = (Graphics2D) cg;
		if (machine.oneMotorArrivedFirst()) {
			cgg.setPaint(Color.WHITE);
		} else if (machine.sameDistanceToTarget()) {
			cgg.setPaint(Color.LIGHT_GRAY);
		} else {
			cgg.setPaint(penColors[penColorIndex]);
		}
		Ellipse2D.Double curPointEll = new Ellipse2D.Double(curPoint.x, curPoint.y, 5, 5);
		cgg.draw(curPointEll);
		
		cgg.draw(machine.prevToCurrentLine().arrow());
	}

	private void setupCanvas()
	{
		if (canvas == null)
		{
			canvas = createImage(CanvasWidth,CanvasHeight ) ;
			cg = canvas.getGraphics();
		}
		cg.setColor(Color.BLACK);
		cg.fillRect(0, 0, CanvasWidth, CanvasHeight );
	}

	@Override
	public void keyPressed(KeyEvent e) {
		B.bug("got a key");
		machine.doKeyPressed(e);
//		int code = e.getKeyCode();
//		if (code == KeyEvent.VK_P) {
//			pause=true;
//		} else if (code == KeyEvent.VK_N){
//			advanceOne=true;
//			pause=false;
//		} else if (code == KeyEvent.VK_R){
//			advanceOne=false;
//			pause=false;
//		}
		
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
