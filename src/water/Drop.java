package water;

public class Drop 
{
	public double Xvel;
	public double Yvel;
	public double Xloc;
	public double Yloc;
	public double Delete;
	
	public void moveInXDir() {
		Xloc += Xvel;
	}
	
	public void youCollidedWithSomething() {
		Xvel=0;
	}
}

