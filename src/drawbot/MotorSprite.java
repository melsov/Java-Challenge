package drawbot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class MotorSprite 
{
	Motor motor;
	boolean isRightSideMotor;
	public MotorSprite(Motor _motor, boolean rightMotor) {
		motor = _motor;
		isRightSideMotor = rightMotor;
	}
	
	public void drawMotor(Graphics2D g, Pointt loc) {
		Ellipse2D.Double gear = new Ellipse2D.Double(loc.x, loc.y, motor.gearDiameter, motor.gearDiameter);
		g.setPaint(Color.BLUE);
		g.fill(gear);
		Pointt center = loc.plus(new Pointt(motor.gearDiameter, motor.gearDiameter).multiply(.5));
		double a = motor.totalRotationRadians();
		if (isRightSideMotor) a *= -1;
		Pointt radarLoc = new Pointt(motor.gearDiameter * .5 * Math.cos(a), motor.gearDiameter * .5 * Math.sin(a));
		radarLoc = radarLoc.plus(center);
		Line2D.Double radar = new Line2D.Double(center.point2D(), radarLoc.point2D());
		g.setPaint(Color.WHITE);
		g.draw(radar);
		
		int pushDownY = 50;
		drawTextAt(g, loc, "distToTarget: " + motor.distanceToTarget(), pushDownY );
		pushDownY +=20;
		drawTextAt(g, loc, "velocity: " + motor.velocity(), pushDownY );
		
	}
	
	private void drawTextAt(Graphics2D g, Pointt loc, String str, int pushDownY) {
		char[] dd = str.toCharArray();
		Pointt textO = loc.plus(new Pointt(0, pushDownY));
		textO.x = textO.x > 50 ? textO.x - 150 : textO.x;
		g.drawChars(dd, 0, dd.length, (int) textO.x,(int) textO.y);
	}
}
