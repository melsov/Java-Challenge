import java.io.Console;
import static java.lang.System.out;

public class Fibo {

	/**
	 * @param args
	 * You must run this program from the terminal. (See Haggler.java for how to do this)
	 * The Fibonacci numbers are a series of numbers.
	 * By definition the first two numbers in the sequence are: 0, 1
	 * Any next number is the sum of previous two numbers. So the third number is 0 + 1 or 1
	 * Fourth: 1 + 1 or 2
	 * Part of the series: 0 1 1 2 3 5 8 13 21 34 55 89
	 * Write psuedo-code for a program that prints out any positive number of fibonacci numbers.
	 * Put the psuedo-code in a comment somewhere in this file.
	 * Then write a program that prints out any positive number of fibonacci numbers.
	 */
	public static void main(String[] args) 
	{
		Console console = System.console();

		int howmany = Integer.parseInt(console.readLine("How many fibonacci numbers would you like?:"));

		Fibo.fibo(howmany);
		
	}
	
	public static void fibo(int count) 
	{
		// YOUR CODE HERE
		
	}

}
