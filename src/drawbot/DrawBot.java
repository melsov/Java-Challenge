package drawbot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import static java.lang.Math.*;

public class DrawBot extends JPanel implements ActionListener {

	Machine machine = new Machine();
	Pointt curPoint = new Pointt();
	DrawPointProvider drawPointProvider = new DrawPointProvider();
	public static final int CanvasWidth = 1200;
	public static final int CanvasHeight = 800;
	
	private Image canvas; /*off-screen image*/
	private Graphics cg; /*the graphics of the off-screen image*/
	
	private Color[] penColors = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK};
	private int penColorIndex = 0;
	
	private MotorSprite motorSpriteL;
	private MotorSprite motorSpriteR;
	
	private LineSprite lineSpriteL;
	private LineSprite lineSpriteR;
	
	public DrawBot()
	{
	}
	
	public void addNotify() {
		super.addNotify();
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
		
		Pointt leftPos = new Pointt();
		Pointt rightPos = new Pointt(machine.machineWidth, 0);
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
    }

	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.setSize(CanvasWidth, CanvasHeight + 40);
		DrawBot db = new DrawBot();
		db.setSize(CanvasWidth, CanvasHeight);
		fr.add(db);
		fr.setVisible(true);
		fr.setResizable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateMachine();
		drawOnCanvas();
		repaint();
	}
	
	private void updateMachine() {
		if (machine.readyForNextPoint()) {
			machine.moveToPoint(nextPoint());
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
		cgg.fill(curPointEll);
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

}
