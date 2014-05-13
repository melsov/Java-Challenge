package SingleThings;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.Timer;

public class Testy extends Applet{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;

TextUtil save = new TextUtil("HarrysGame.txt");

public void init()
{
	save.erase();
	save.writeLine(3, "<-- There might be an indent there");
	bugfile();
	save.writeLine(3, "Was this replaced?");
	save.writeLine(4, "Line After at 4");
	save.writeLine(3, "I replaced line 3");
	save.writeLine(8, "There should be three empty lines above me");
	bugfile();
//	save.writeLine(2, "Testing the replace feature");
//	save.writeLine(3, "If you see this, the writeline does not replace lines correctly");
//	save.writeLine(3, "(Hopefully) replaced line");
//	save.writeLine(4, "If there is an error message next line, that means its empty line detection is working.");
//	save.writeLine(8, "There should be three empty lines above me");
//	System.out.println(save.getLine(1));
//	System.out.println(save.getLine(2));
//	System.out.println(save.getLine(3));
//	System.out.println(save.getLine(4));
//	System.out.println(save.getLine(5));
//	System.out.println(save.getLine(6));
//	System.out.println(save.getLine(7));
//	System.out.println(save.getLine(8));
}

private void bugfile() {
	save.debugPrintFile();
	bug("file length now: " + save.getLength());
	bug();
}

private void bug() { bug("##"); }

private void bug(String s) {
	System.out.println(s);
}

	

}
