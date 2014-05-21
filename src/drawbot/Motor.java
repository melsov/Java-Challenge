package drawbot;

public class Motor 
{
	public double gearDiameter=20d;
	private double originalStringLength = 0;
	private int currentTotalSteps = 0;
	private int targetSteps;
	public static final int STEPS_PER_REV=200;
	public double stepsPerSecond = 10d;
	public static int MAX_STEPS_PER_SECOND=500;
	
	public Motor(double startStringLength) {
		originalStringLength = startStringLength;
	}
	
	public void updateRotationTargetBySteps(int steps) {
		targetSteps = currentTotalSteps + steps;
	}
	public void startRotating(double stepsPerSecond) {
		MotorRotator mr = new MotorRotator(stepsPerSecond); //WANT
		Thread t = new Thread(mr);
		t.start();
	}
	
	public double stringLength() {
		return Math.abs( originalStringLength + deltaStringLength());
	}
	public int distanceToTarget() {
		return targetSteps - currentTotalSteps;
	}
	public double totalRotationRadians() {
		return (currentTotalSteps /(double) STEPS_PER_REV)*Math.PI*2.0;
	}
	private double deltaStringLength() {
		return gearDiameter * Math.PI * (currentTotalSteps/(double)STEPS_PER_REV);
	}
	private class MotorRotator implements Runnable
	{
		public long stepTimeMillis = 5;
		
		public MotorRotator(double stepsPerSecond) {
			stepTimeMillis = Math.max( (long) (1000.0/stepsPerSecond), 1);
			
		}
		
		@Override
		public void run() {
			while(currentTotalSteps != targetSteps) {
				currentTotalSteps += currentTotalSteps < targetSteps ? 1 : -1;
				try {
					Thread.sleep(stepTimeMillis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
