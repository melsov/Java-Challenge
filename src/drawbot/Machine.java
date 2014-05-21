package drawbot;

public class Machine 
{
	public float machineWidth = DrawBot.CanvasWidth;
	public float machineHeight = 0;
	public Pointt startPoint = new Pointt(machineWidth/2.0, 0);
	private Motor leftMotor;
	private Motor rightMotor;
	
	public Machine() {
		double ldist = distanceFromMotorToPoint(startPoint, false);
		double rdist = distanceFromMotorToPoint(startPoint, true);

		leftMotor = new Motor(ldist);
		rightMotor = new Motor(rdist);
	}

	public void moveToPoint(Pointt p){
		setStepsForMotor(p, true);
		setStepsForMotor(p, false);
		startMotors();
	}
	public Pointt penLocation() {
		return penLoc();
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
		int steps = stepsForDistance(difLength, motor);

		motor.updateRotationTargetBySteps(steps);
	}
	/*
	 * sync steps per second so that motors arrive at the same time.
	 */
	private void startMotors() {
		int stepsToTargetL = Math.abs( leftMotor.distanceToTarget());
		int stepsToTargetR = Math.abs( rightMotor.distanceToTarget());
		if (stepsToTargetR==stepsToTargetL || stepsToTargetR == 0 || stepsToTargetL == 0) {
			leftMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
			rightMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
			return;
		}
		
		Motor moreStepsMotor = stepsToTargetR > stepsToTargetL ? rightMotor : leftMotor;
		Motor fewerStepsMotor = stepsToTargetR > stepsToTargetL ?  leftMotor : rightMotor;
		
		int greaterSteps = stepsToTargetR > stepsToTargetL ? stepsToTargetR : stepsToTargetL;
		int fewerSteps = stepsToTargetR > stepsToTargetL ?  stepsToTargetL : stepsToTargetR ;
		
		int fewerStepsStepSpeedPerSecond = (int)(Motor.MAX_STEPS_PER_SECOND * (fewerSteps/(double)greaterSteps));

		moreStepsMotor.startRotating(Motor.MAX_STEPS_PER_SECOND);
		fewerStepsMotor.startRotating(fewerStepsStepSpeedPerSecond);
	}
	private int stepsForDistance(double dist, Motor motor) {
		return (int)(Motor.STEPS_PER_REV * dist/(Math.PI * motor.gearDiameter));
	}
	public double testStringDistanceFromCurrentPointToPoint(Pointt p, boolean wantRightMotor) {
		return stringDistanceFromCurrentPointToPoint(p, wantRightMotor); // for testing
	}
	private double stringDistanceFromCurrentPointToPoint(Pointt p, boolean wantRightMotor) {
		double newDist = distanceFromMotorToPoint(p, wantRightMotor);
		return newDist - (wantRightMotor ? rightMotor.stringLength() : leftMotor.stringLength());
	}
	public double testDistanceFromMotorToPoint(Pointt p, boolean wantRightMotor) {
		return distanceFromMotorToPoint(p, wantRightMotor); // for testing
	}
	private double distanceFromMotorToPoint(Pointt p, boolean wantRightMotor) {
		return p.minus(machineLocation(wantRightMotor)).distance();
	}
	private Pointt machineLocation(boolean wantRight) {
		Pointt machineLoc = new Pointt(0,0);
		if (wantRight) machineLoc.x = machineWidth;
		return machineLoc;
	}
	private Pointt penLoc() {
		double lAng = angleForSide(false);
		double yy = leftMotor.stringLength() * Math.sin(lAng);
		double xx = leftMotor.stringLength() * Math.cos(lAng);
		return new Pointt(xx,yy);
	}
	public double testAngleForSide(boolean wantRightSide) { //public for testing
		return angleForSide(wantRightSide);
	}
	/*
	 * apply the law of cosines...
	 */
	private double angleForSide(boolean wantRightSide) { 
		double opSide = rightMotor.stringLength();
		double nearSide = leftMotor.stringLength();
		if (wantRightSide) {
			opSide = leftMotor.stringLength();
			nearSide = rightMotor.stringLength();
		}
		return Math.acos((machineWidth*machineWidth + nearSide*nearSide - opSide*opSide)/(2*machineWidth*nearSide));
	}
	
	public Motor getMotor(boolean right) //for testing
	{
		if (right) return rightMotor;
		return leftMotor;
	}
}
