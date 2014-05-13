package silly;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	
	private ImageIcon linkIcon;
	private ImageIcon demonLinkIcon;

	private boolean fullscreen;

	public SillyFrame()
	{
		linkIcon = getImageIcon("normalLink.png");
		demonLinkIcon = getImageIcon("demonLinkP2.png");
		
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
		
		panel1 = new SillyPanel(sameScreenMode, runServerAlso);
		add(panel1);
		if (sameScreenMode)
		{
			/* SAME SCREEN MODE IS A HACK. PANEL1 SIMPLY 'HIDES' BEHIND PANEL 2.
			 * AND, THIS JFRAME DIRECTS ARROWS AND 'WASD' KEYPRESSES TO PANEL1 AND PANEL2. 
			 * OTHERWISE, ITS THE SAME AS NETWORK MODE. */
			panel2 = new SillyPanel(sameScreenMode, runServerAlso);
			add(panel2);
		}
		setTitle("Silly Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		int width = SillyPanel.WIDTH_PIXELS;
        setSize(width, SillyPanel.HEIGHT_PIXELS + SillyPanel.GUI_FOOTER_HEIGHT);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        this.setFullScreen();
	}
	
	private boolean getWantsSameScreenMode()
	{
		//Custom button text
		Object[] options = {"Two player mode",
							"Over a network",
		                    };
		Frame frame = new Frame();
		
		int n = JOptionPane.showOptionDialog(frame,
		    "Would you like to play the game with two computers (over a network) or on one computer (two player mode)?",
		    "Choose game mode",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    linkIcon,
		    options,
		    options[0]);
		return n == 0;
	}
	
	private boolean getWantsClientAndServer()
	{
		//Custom button text
		Object[] options = {"Client and server",
							"Just client",
							"How do I find my computer's network name?",
		                    };
		Frame frame = new Frame();
		
		int n = JOptionPane.showOptionDialog(frame,
		    "You're playing someone on another computer.\n One of you has to be the 'client and server'\n" +
		    	" and the other has to be 'just a client'.\n " +
			    "Client-and-server has to tell client her or his computer's network name.",
		    "Client and server or just client?",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    demonLinkIcon,
		    options,
		    options[0]);

		if (n == 2) {
			JOptionPane.showMessageDialog(frame,
		    "On a mac: go to System Preferences. \nGo to the 'Sharing' section.\n" +
		    "Look for the sentence: 'Computers on your local network can access your computer at:'\n" +
		    "The next line should say something like: 'comp-name.local'\n" +
		    "This is your computer's name.\n" +
		    "If you are playing as 'Client and server' \n tell the other person (who is 'Just client') this name.\n" +
		    " They will have to type it soon.");
			return getWantsClientAndServer();
		}
		
		return n == 0;
	}
	
	private ImageIcon getImageIcon(String imURL) {
		return new ImageIcon(this.getClass().getResource(imURL));
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
	
	public void setFullScreen() {   
        if (!this.fullscreen) {
//            size = this.getSize();
//            if (this.parent == null) {
//                this.parent = getParent();
//            }
//            this.frame = new Frame();
//            this.frame.setUndecorated(true);
//            this.frame.add(this);
//            this.frame.setVisible(true);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = ge.getScreenDevices();
//            devices[0].setFullScreenWindow(this.frame);
            devices[0].setFullScreenWindow(this);
            this.fullscreen = true;
        } 
        else {
//            if (this.parent != null) {
//                this.parent.add(this);
//            }
//            if (this.frame != null) {
//                this.frame.dispose();
//            }
//            this.fullscreen = false;
////            this.setSize(size);
//            this.revalidate();
        }       
        this.requestFocus();
    }
}
