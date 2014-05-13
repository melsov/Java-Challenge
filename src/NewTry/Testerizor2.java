package NewTry;

public class Testerizor2 
{
	public static void main(String[] args) 
	{
		String filepath = "/Library/Application Support/Harry's Game/HarrysGame.txt";
		if (args.length > 0) {
			filepath=args[0];
		}
		TextUtil jane = new TextUtil(filepath,8);

		System.out.println(jane.lines[0]);
		System.out.println(jane.lines[1]);
		System.out.println(jane.lines[2]);
		System.out.println(jane.lines[3]);
		System.out.println(jane.lines[4]);
		jane.update();
		System.out.println(jane.lines[0]);
		System.out.println(jane.lines[1]);
		System.out.println(jane.lines[2]);
		System.out.println(jane.lines[3]);
		System.out.println(jane.lines[4]);
	}
}
