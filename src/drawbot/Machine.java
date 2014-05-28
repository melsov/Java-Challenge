package drawbot;

import java.awt.event.ActionEvent;
import static java.lang.Math.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class Machine implements ActionListener
{
	public float machineWidth = DrawBot.CanvasWidth;
	public float machineHeight = 0;
	public Pointt startPoint = new Pointt(machineWidth/2.0, 0);
	private Motor leftMotor;
	private Motor rightMotor;
	
	public Pointt motorLocationL = new Pointt(0,0);
	public Pointt motorLocationR = new Pointt(DrawBot.CanvasWidth,0);
	
	public Pointt targetPoint = new Pointt();
	public Pointt departurePoint = new Pointt();
	private Pointt currentPoint = new Pointt();
	private Pointt prevPoint = new Pointt();
	
	public Pointt cheatPoint = new Pointt();
	public Pointt cheatPointForPrev = new Pointt();

	private Timer timer = new Timer(Motor.MotorSecondinMillis, this);
	
	private int total_slices;
	private int slice;
	
	private boolean pause = true;
	private boolean advanceOne;
	
	public Machine() {
		departurePoint = currentPoint = targetPoint = startPoint;
		
		leftMotor = new Motor(distanceFromMotorToPoint(startPoint, false));
		rightMotor = new Motor(distanceFromMotorToPoint(startPoint, true));
		
		timer.start();
	}

	public void moveToPoint(Pointt p){
		setStepsForMotor(p, true);
		setStepsForMotor(p, false);
//		startMotors(p);
		currentPoint = prevPoint = departurePoint = targetPoint;
		targetPoint = p;
		slice = 0;
		updateTotalSlices();
	}
	private void updateTotalSlices() {
		double dist = targetPoint.minus(departurePoint).distance();
		total_slices = (int) (.5d + dist/defaultSpeedPixelsPerTick());
	}
	public Pointt penLocation() {
		return penLoc(false);
	}
	public boolean readyForNextPoint() {
		return leftMotor.distanceToTarget() == 0 && rightMotor.distanceToTarget() == 0;
	}
	public boolean oneMotorArrivedFirst() {
		return (leftMotor.distanceToTarget() == 0) != (rightMotor.distanceToTarget() == 0);
	}
	public boolean sameDistanceToTarget() {
		return leftMotor.distanceToTarget() == rightMotor.distanceToTarget();
	}
	private void setStepsForMotor(Pointt p, boolean wantRightMotor) {
		Motor motor = leftMotor;
		if (wantRightMotor) motor = rightMotor;
		
		double difLength = stringDistanceFromCurrentPointToPoint(p, wantRightMotor);
		double steps = stepsForDistance(difLength, motor);

		motor.updateRotationTargetBySteps(steps);
	}
	private void updateMotors() {
		setMotorVelocities();
		leftMotor.spinOneFrame();
		rightMotor.spinOneFrame();
	}
	private double defaultSpeedPixelsPerTick() {
		return 10.00d;
	}
	private void setMotorVelocities() 
	{
		prevPoint = nextPointWithScale((slice)/(double)total_slices); 
		slice++;
		Pointt nextPoint = nextPointWithScale(slice/(double)total_slices);

		cheatPoint = nextPoint; //testing
		cheatPointForPrev = prevPoint; //testing
		currentPoint = nextPoint;
		double l_string_len = motorPositionWithPoint(prevPoint, false);
		double r_string_len = motorPositionWithPoint(prevPoint, true); 

		double l_next_len = motorPositionWithPoint(nextPoint, false);
		double r_next_len = motorPositionWithPoint(nextPoint, true);

		double r_vel = r_next_len - r_string_len ;
		double l_vel = l_next_len - l_string_len ;
		
		r_vel = abs(r_vel);
		l_vel = abs(l_vel);

		r_vel = Motor.STEPS_PER_REV * (r_vel/(Math.PI * rightMotor.gearDiameter));
		l_vel = Motor.STEPS_PER_REV * (l_vel/(Math.PI * leftMotor.gearDiameter));
		
//		if (r_vel == 0 && l_vel == 0) {
//			if (leftMotor.distanceToTarget() != 0) l_vel = 1;
//			if (rightMotor.distanceToTarget() != 0)  r_vel = 1;
//		} //bail out...
		
//		int lvelint = (int)(l_vel + .5);
//		int rvelint = (int)(r_vel + .5);
		
//		leftMotor.setVelocitySteps(lvelint);
//		rightMotor.setVelocitySteps(rvelint);
		leftMotor.setVelocitySteps(l_vel);
		rightMotor.setVelocitySteps(r_vel);
	}
	
	private Pointt departureToTarget() {
		return targetPoint.minus(departurePoint);
//		return targetPoint.minus(currentPoint);
	}
	private Pointt targetDirectionUnitVector() {
		Pointt res = departureToTarget();
		return res.unitPointt();
	}
	private Pointt nextPointWithScale(double scale) {
		if (Double.isNaN(scale))
			scale = 0;
		Pointt r = departureToTarget().multiply(scale);
		return departurePoint.plus(r);
	}
	private double motorPositionWithPoint(Pointt p, boolean wantRightMotor) {
		double xx = wantRightMotor ? motorLocationR.x - p.x : p.x;
		return  Math.sqrt(xx * xx + p.y * p.y);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pause) return;
		
		updateMotors();
		
		if (advanceOne) {
			pause = true;
		}
	}
	
	public Line departureToTargetLine() {
		return new Line(departurePoint, targetPoint);
	}
	public Line prevToCurrentLine() {
		return new Line(prevPoint, currentPoint);
	}
	private double stepsForDistance(double dist, Motor motor) {
		return (Motor.STEPS_PER_REV * dist/(Math.PI * motor.gearDiameter));
	}
	public double testStringDistanceFromCurrentPointToPoint(Pointt p, boolean wantRightMotor) {
		return stringDistanceFromCurrentPointToPoint(p, wantRightMotor); // for testing
	}
	private double stringDistanceFromCurrentPointToPoint(Pointt p, boolean wantRightMotor) {
		return distanceFromMotorToPoint(p, wantRightMotor) - distanceFromMotorToPoint(penLoc(false), wantRightMotor); 
	}
	public double testDistanceFromMotorToPoint(Pointt p, boolean wantRightMotor) {
		return distanceFromMotorToPoint(p, wantRightMotor); // for testing
	}
	private double distanceFromMotorToPoint(Pointt p, boolean wantRightMotor) {
		return p.distanceFrom(machineLocation(wantRightMotor));
	}
	private Pointt machineLocation(boolean wantRight) {
		if (wantRight) return motorLocationR;
		return motorLocationL;
	}
	private Pointt penLoc(boolean baseOffRightMotorLength) {
		double lAng = angleForSide(baseOffRightMotorLength);
		double len = baseOffRightMotorLength ? rightMotor.stringLength() : leftMotor.stringLength();
		double yy = len * Math.sin(lAng);
		double xx = len * Math.cos(lAng);
		if (baseOffRightMotorLength) xx = motorLocationR.x - xx;
		return new Pointt(xx,yy);
	}
	public double testAngleForSide(boolean wantRightSide) { //public for testing
		return angleForSide(wantRightSide);
	}
	/*
	 * apply the law of cosines...
	 */
	private double angleForSide(boolean wantRightSide) { 
		double opSide =  rightMotor.stringLength();
		double nearSide = leftMotor.stringLength();
		if (wantRightSide) {
			double temp = opSide;
			opSide = nearSide;
			nearSide = temp; 
		}
		return Math.acos((machineWidth*machineWidth + nearSide*nearSide - opSide*opSide)/(2*machineWidth*nearSide));
	}
	
	public Motor getMotor(boolean right) //for testing
	{
		if (right) return rightMotor;
		return leftMotor;
	}
	
	public void doKeyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_P) {
			pause=true;
		} else if (code == KeyEvent.VK_N){
			advanceOne=true;
			pause=false;
		} else if (code == KeyEvent.VK_R){
			advanceOne=false;
			pause=false;
		}
	}

}

/*
 * sync steps per second so that motors arrive at the same time.
 */
//private void startMotors(Pointt p) {
////	int stepsToTargetL = Math.abs( leftMotor.distanceToTarget());
////	int stepsToTargetR = Math.abs( rightMotor.distanceToTarget());
//	double stepsToTargetL = stringDistanceFromCurrentPointToPoint(p, false); 
//	double stepsToTargetR = stringDistanceFromCurrentPointToPoint(p, true);
//	stepsToTargetL = Math.abs(stepsToTargetL);
//	stepsToTargetR = Math.abs(stepsToTargetR);
//	if (stepsToTargetR==stepsToTargetL || stepsToTargetR == 0 || stepsToTargetL == 0) {
//		leftMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
//		rightMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
//		return;
//	}
//	
//	Motor moreStepsMotor = stepsToTargetR > stepsToTargetL ? rightMotor : leftMotor;
//	Motor fewerStepsMotor = stepsToTargetR > stepsToTargetL ?  leftMotor : rightMotor;
//	
//	double greaterSteps = stepsToTargetR > stepsToTargetL ? stepsToTargetR : stepsToTargetL;
//	double fewerSteps = stepsToTargetR > stepsToTargetL ?  stepsToTargetL : stepsToTargetR ;
//	
//	double fewerStepsStepSpeedPerSecond = Motor.MAX_STEPS_PER_SECOND * (fewerSteps/(double)greaterSteps);
//
//	moreStepsMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
//	fewerStepsMotor.startRotating(fewerStepsStepSpeedPerSecond);
//}
//private double proportionForIntVelocity(double leftVel, double rightVel) {
//double result = 1d;
//double eps = 5;
//double max_vel = 1000.0d;
//while (Math.max(leftVel, rightVel) < max_vel) {
//	double lvelint = (int)(leftVel + .5);
//	double rvelint = (int)(rightVel + .5);
//	eps = Math.abs(leftVel/rightVel - lvelint/rvelint);
//	if (eps < .0001) {
//		break;
//	}
//	result += .01;
//	leftVel *= result;
//	rightVel *= result;
//}
//
//return result;
//}