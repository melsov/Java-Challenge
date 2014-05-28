package drawbot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Motor // implements ActionListener
{
	public double gearDiameter=240d;
	private double originalStringLength = 0;
//	private int currentTotalSteps = 0;
	private double currentTotalSteps = 0;
//	private int targetSteps;
	private double targetSteps;
	public static final int STEPS_PER_REV=200;
	public double stepsPerSecond = 10d;
	public static int MAX_STEPS_PER_SECOND=500;
	public static int MIN_STEPS_PER_SECOND=10;
//	MotorRotator mr;
	
	public static int MotorSecondinMillis = 20; // speed up time
//	private Timer timer;
	private double velocitySteps = 1d;
	
	public Motor(double startStringLength) {
		originalStringLength = startStringLength;
//		timer = new Timer(MotorSecondinMillis, this);
//		timer.start();
	}
	/*
	 * Motor operation methods
	 */
	public void updateRotationTargetBySteps(double steps) {
		targetSteps = currentTotalSteps + steps;
	}
	public void setVelocitySteps(double vel) {
		velocitySteps = Math.abs(vel);
	}
	public double velocity() { return velocitySteps; }
//	public void startRotating(double stepsPerSecond) {
//		startRotating(stepsPerSecond, 10d);
//	}
//	public void startRotating(double stepsPerSecond, double stepAcceleration) {
//
//	}
	public double direction() {
		if (targetSteps == currentTotalSteps) return 0d;
		if (targetSteps > currentTotalSteps) return 1d;
		return -1d;
	}
	public void spinOneFrame() {
		moveMotor();
	}
	
	private void moveMotor() {
//		currentTotalSteps += Math.min((int) Math.round(velocitySteps), absDistanceToTarget()) * direction();
		currentTotalSteps += Math.min(velocitySteps, absDistanceToTarget()) * direction();
	}
	
	/*
	 * Motor info methods
	 */
	public double stringLength() {
		return Math.abs(originalStringLength + deltaStringLength());
	}
	public double stringLengthWithDeltaSteps(int deltaSteps) {
		return Math.abs(originalStringLength + stringLengthForSteps(deltaSteps + currentTotalSteps));
	}
	public double distanceToTarget() {
		return targetSteps - currentTotalSteps;
	}
	public double absDistanceToTarget() {
		return Math.abs(distanceToTarget());
	}
	public double totalRotationRadians() {
		return (currentTotalSteps /(double) STEPS_PER_REV)*Math.PI*2.0;
	}
	public double currentTotalSteps() { return currentTotalSteps; }
	
	private double deltaStringLength() {
		return stringLengthForSteps(currentTotalSteps);
	}
	public double stringLengthForSteps(double steps) {
		return gearDiameter * Math.PI * (steps/(double)STEPS_PER_REV );
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		moveMotor();
//	}

	
}
