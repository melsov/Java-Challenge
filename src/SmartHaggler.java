import java.io.Console;
import static java.lang.System.out;

public class SmartHaggler 
{

	/**
	 * @param args
	 * Haggler sells washing machines but
	 * only if the user is willing to meet his terms.
	 * He has a price floor below which he won't sell the
	 * machine.
	 * Version 2: Smart haggler:
	 * Ask the user how much they are willing to pay
	 * If his or her offer is less than half of the floor price
	 * ask him or her if they are crazy.
	 * If they offer more than the floor the first time around
	 * reject their offer and increase the floor price to 125% of their offer
	 */
	
// HOW TO:
//	Ask the user something:
	
//	String input = console.readLine("ask something: ");
//	out.println("you said: " + input);
	
//	(Note: for this to work, one has to make a 'console' variable at some point. 
//	This has been done already in this case; see below.) 
	
//	Turn a String into a float:
	
//	someString = "2.0";
//	float offerAmount = Float.parseFloat(someString);
	
//	Actually run this file:
	
//	You need to use the terminal. (Either the one in eclipse or the terminal program (in Mac))
//	use cd path/to/bin 
//	to "change directory" to get to a folder called 'bin' inside this project. If you type: "ls" you should see
//	a file called "SmartHaggler.class" and/or "Haggler.class"
//	TO RUN TYPE:
//	java Haggler
//	You are running the Haggler.class file but you are required to leave off the '.class' 
//	You should see the text that starts: "YOU SHOULD SEE THIS LINE..."
	
	private float minPrice = 1254.05f;
	
	private static Console console = System.console();  //use this to ask the user for input
	
	public static void main(String[] args) 
	{
		out.println("YOU SHOULD SEE THIS LINE IN THE TERMINAL WHEN YOU RUN. DELETE AFTER VERIFYING THAT THIS WORKS");
		SmartHaggler sh = new SmartHaggler();
		sh.haggle();
	}

	public void haggle()
	{
		// YOUR CODE HERE
	}

	
}
