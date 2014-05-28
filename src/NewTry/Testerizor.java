package NewTry;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import SingleThings.TextUtil;

public class Testerizor extends Applet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextUtil jane = new TextUtil("/Library/Application Support/Harry's Game/HarryzGame.txt",8);
//	TextUtil jane = new TextUtil("HarrysGame.txt",5);
	@SuppressWarnings("unused")
	public void init()
	{
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
