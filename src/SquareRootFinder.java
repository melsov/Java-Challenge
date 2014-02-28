/**
 * 
 */

/**
 * @author didyouloseyourdog
 * A fairly silly class for demonstrating
 * the idea behind classes.
 *
 */
public class SquareRootFinder 
{
	private String customMessage;
	
	//A constructor method for square root finder
	public SquareRootFinder(String _customMessage)
	{
		this.customMessage = _customMessage;
	}
	
	//Alternate constructor. In this case, this constructor helps lazy people
	//who don't care what the custom message is.
	// this means somebody can either type:
	// SquareRootFinder srf = SquareRootFinder("Found it!!!!");
	// or
	// SquareRootFinder srf = SquareRootFinder();
	public SquareRootFinder()
	{
		customMessage = "Got your square root: ";
	}
	
	public float squareRoot(float posNumber) 
	{
		if (posNumber < 0) {
			return -1f;
		}
		
		int i = 0;
		while(i*i < posNumber)
		{
			i++;
		}
		
		float iMinus = (float)(i - 1);
		System.out.println(this.customMessage);
		return (float)(iMinus + (posNumber - iMinus * iMinus)/(i*i - iMinus * iMinus));
	}
	
}
