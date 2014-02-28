/**
 * 
 */

/**
 * @author didyouloseyourdog
 * This class uses the square root finder
 *
 */
public class SquareRootNeedingClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args)  
	{
		// To me, the idea behind classes is the same as Homer's campaign slogan 
		// when he ran for sanitation commissioner of Springfield:
		// "Can't someone else do it?"
		// In other words, when you get the idea to make a class
		// its like getting an idea along the lines of: 'wouldn't it be nice
		// if there was some magic robot that could do this job for me?'
		//
		// Lingo: "SquareRootFinder" is a "class"
		// 		  sqRootFinder is an "object"
		//        its also an 'instance' of class "SquareRootFinder"
		//        In the minds of programmers, objects are (kind of)
		//        like various little robots that do what you want.
		//       One good thing about organizing your programs this way is that
		//       it tends to naturally partition the code into sections
		//       and makes things just generally easier to sort out.
		//       Really programming without objects is--in many cases--
		//       kind of unimaginable. It would be almost like going back to the barter system
		//       instead of using money.
		SquareRootFinder sqRootFinder = new SquareRootFinder("Hurray!");
		float the_answer = sqRootFinder.squareRoot(-3f);
		System.out.println("the answer was: " + the_answer);
	}

}
