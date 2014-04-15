package silly;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import silly.server.ZeldaUDPServer;

//import silly.SillyPanel.MyKeyAdapter;

/*
 * Notes 
	for a (not very much like) Zelda (original NES) game (map)
 * Plan for the game map.
 * Link moves around a 2D map.
 * The map is made of "tiles"
 * Tiles are actually numbers in a 2D array
 * A 2D array is an array where you can look up values
 * with 2 indices. For example: "something = map[x,y];"
 * 
 * TWO CLASSES: MAP AND PAINTER
 * Split up the jobs involved with making and showing
 * the map using classes:
 * one class holds the array as a member variable
 * and its only job is to tell other classes what kind of tile
 * is at a given x and y on the map. (or what set of tiles 
 * is in a given square area)
 * Another class, Painter, will own an image. It's job will be to paint
 * the map onto the image. It will have a method
 * the takes a square section of the 2D array as a parameter.
 * The square section represents the part of the map that we want to draw.
 * 
 * OBLIVIOUSNESS IS A GOOD THING:
 * It's a good thing that the class that draws the map
 * doesn't know why its drawing stuff, it just knows how to
 * translate a bunch of numbers into pictures that it will tile together.
 * 
 * And it's a good this that the map class doesn't know how to represent itself.
 * It just keeps track of the numbers in its 2D array and dishes them out when asked.
 * It has no idea why it does this. 
 * 
 * MORE CLASSES:
 * A class to actually make the game happen: i.e. the main class. 
 * Call it MainZelda.
 * As part of the as 
 * 
 */


public class SillyFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SillyPanel panel1;
	private SillyPanel panel2;
	private ZeldaUDPServer zeldaServer;
	
	private boolean sameScreenMode = false;

	public SillyFrame()
	{
		sameScreenMode = getWantsSameScreenMode();
		boolean runServerAlso = sameScreenMode;
		if (!runServerAlso) {
			runServerAlso = getWantsClientAndServer();
		}
		
		if (runServerAlso) {
			zeldaServer = new ZeldaUDPServer();
			Thread t = new Thread(zeldaServer);
			t.start();
		}
		
		panel1 = new SillyPanel(sameScreenMode);
		add(panel1);
		if (sameScreenMode)
		{
			panel2 = new SillyPanel(sameScreenMode);
			add(panel2);
		}
		setTitle("Silly Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		int width = SillyPanel.WIDTH_PIXELS;
        setSize(width, SillyPanel.HEIGHT_PIXELS + SillyPanel.GUI_FOOTER_HEIGHT);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
	
	private boolean getWantsSameScreenMode()
	{
		Frame f = new Frame();
		f.setSize(400,500);
		String network = "NETWORK";
		CustomDialog cd = new CustomDialog(f, "Do you want to play over a network? If so, hit return. If not type 'no' (for two players, one computer)", network);
		cd.pack();
		cd.setVisible(true);
		String answer = cd.getAnswer();
		return !answer.equals(network);
	}
	
	private boolean getWantsClientAndServer()
	{
		Frame f = new Frame();
		f.setSize(400,500);
		String network = "CLIENT AND SERVER";
		CustomDialog cd = new CustomDialog(f, "Want to host this game? (just hit return). To connect to another server, type any key and then hit return. ", network);
		cd.pack();
		cd.setVisible(true);
		String answer = cd.getAnswer();
		return answer.equals(network);
	}	
	
	public static void main(String[] args)
	{
		new SillyFrame();
	}
	
	public void addNotify()
	{
		super.addNotify();
		startUpStuff();
	}
	
	private void startUpStuff()
	{
		KeyListener[] kls = this.getKeyListeners();
		if (kls.length == 0)
		{
			addKeyListener(new MyKeyAdapter());
			setFocusable(true);
		}
	}
	
	private class MyKeyAdapter extends KeyAdapter
	{
		public void keyReleased(KeyEvent e) 
		{
			panel1.doKeyReleased(e);
			if (sameScreenMode)
        	{
        		panel2.doKeyReleased(e);
        	}
        }

        public void keyPressed(KeyEvent e) {
        	panel1.doKeyPressed(e);
        	if (sameScreenMode)
        	{
        		panel2.doKeyPressed(e);
        	}

        }

	}
}
