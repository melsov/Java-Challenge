package silly;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import silly.server.ZeldaUDPServer;

public class Protagonist 
{
	private int x, y;
	public ZeldaMap zeldaMap;
//	public boolean isCurrentlyPossessed = false;
	
	private Image normalImage;
	private Image possessedImage;
	private Image normalImageP2;
	private Image possessedImageP2;
	
	private IServerRequest serverDelegate;

	public boolean iAmPlayerOne;
	public GameStats myStats = new GameStats();
	public GameStats otherStats = new GameStats();
	
	public int getX() {
		return x;
	}
	public void setX(int x_){
		x = x_;
	}
	public int getY() {
		return y;
	}
	public void setY(int y_) {
		y = y_;
	}
	
	public Image getImage() {
		if (iAmPlayerOne) {
			if (myStats.isPossessed)
				return possessedImage;
			return normalImage;
		} else {
			if (myStats.isPossessed)
				return possessedImageP2;
			return normalImageP2;
		}
	}
	
	public Image getOtherImage() {
		if (iAmPlayerOne) {
			if (otherStats.isPossessed)
				return possessedImageP2;
			return normalImageP2;
		} else {
			if (otherStats.isPossessed)
				return possessedImage;
			return normalImage;
		}
	}
	
	public void setOtherImages(Image normal, Image demon) {
		normalImageP2 = normal;
		possessedImageP2 = demon;
	}
	
	public Protagonist(ZeldaMap zeldaMap_, Image normalImage_, Image possessedImage_, IServerRequest serverDelegate_)
	{
		serverDelegate = serverDelegate_;
		zeldaMap = zeldaMap_;
		normalImage = normalImage_;
		possessedImage = possessedImage_;
		
		//inform server before shutting down.
		//(Rumor has it that this doesn't always work. Works in testing however)
		Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
	        	System.out.println("Shutdown Hook is running!");
	        	try {
					tellServerILeft();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
    	});
	}
	
	public void otherHasArrived() {
		//extend introduction to other
	}
	
	private void moveForward() {
		int newX = x+1;
		moveTo(newX, y);
	}
	
	private void moveBackward() {
		int newX = x - 1;
		moveTo(newX, y);
	}
	
	private void moveUp() {
		int newY = y -1;
		moveTo(x, newY);
	}
	
	private void moveDown() {
		int newY = y + 1;
		moveTo(x, newY);
	}
	
	private void moveTo(int xx, int yy) {
		if (!checkWall(xx, yy))
		{
			try {
				if (serverSaysItsOKToMove(xx,yy))
				{
					x = xx;
					y = yy;

					playSound("crunch.wav");
					if (zeldaMap.jellyAt(x, y) != 0) {
						myStats.jellyCount++;
						tellServerThatIGotJelly(x,y);
						zeldaMap.removeJelly(x,y);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean serverSaysItsOKToMove(int xx, int yy) throws IOException 
	{
		String x_str = String.valueOf(xx);
		String y_str = String.valueOf(yy);
		String playerNum = String.valueOf(iAmPlayerOne ? 0 : 1);
		  
		String request = ZeldaUDPServer.MOVE_REQUEST + ":" + x_str + ":" + y_str + ":" + playerNum;
		String response = requestFromServer(request);
		
		if (response.trim().equals("YES"))
			return true;
		return false;
	}
	
	private void tellServerThatIGotJelly(int xx, int yy) throws IOException 
	{
		String x_str = String.valueOf(xx);
		String y_str = String.valueOf(yy);
		String playerNum = String.valueOf(iAmPlayerOne ? 0 : 1);
		String jCountString = String.valueOf(myStats.jellyCount);
		String request = ZeldaUDPServer.I_GOT_JELLY + ":" + x_str + ":" + y_str + ":" + playerNum + ":" + jCountString;
		String response = requestFromServer(request);
	}
	
	private void tellServerILeft() throws IOException
	{
		requestFromServer(ZeldaUDPServer.I_LEFT_THE_GAME_REQUEST);
	}
	
	private String requestFromServer(String request) throws IOException
	{
		return serverDelegate.requestFromServer(request);
	}
	
	private boolean checkWall(int xx, int yy) {
		return zeldaMap.coordIsSolid(xx, yy);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		boolean same_computer_mode = true;
		
		int upkey = KeyEvent.VK_W;
		int leftkey = KeyEvent.VK_A;
		int downkey = KeyEvent.VK_S;
		int rightkey = KeyEvent.VK_D;
		
		if (same_computer_mode) {
			upkey = iAmPlayerOne ? KeyEvent.VK_W : KeyEvent.VK_UP;
			leftkey = iAmPlayerOne ? KeyEvent.VK_A : KeyEvent.VK_LEFT;
			downkey = iAmPlayerOne ? KeyEvent.VK_S : KeyEvent.VK_DOWN;
			rightkey = iAmPlayerOne ? KeyEvent.VK_D : KeyEvent.VK_RIGHT;
		}
		
		if (key == upkey) {
			moveUp();
		}
		if (key == leftkey) {
			moveBackward();
		}
		if (key == downkey) {
			moveDown();
		}
		if (key == rightkey) {
			moveForward();
		}
	}
	//SOUND
	public static synchronized void playSound(final String url) 
	{
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(

        		this.getClass().getResourceAsStream(url));		        		
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
	}
	
}
