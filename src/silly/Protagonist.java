package silly;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Protagonist 
{
	private int x, y;
	public ZeldaMap zeldaMap;
	public boolean isCurrentlyPossessed = false;
	
	private Image normalImage;
	private Image possessedImage;
	
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
		if (isCurrentlyPossessed)
			return possessedImage;
		return normalImage;
	}
	
	public Protagonist(ZeldaMap zeldaMap_, Image normalImage_, Image possessedImage_)
	{
		zeldaMap = zeldaMap_;
		normalImage = normalImage_;
		possessedImage = possessedImage_;
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
			if (serverSaysItsOKToMove(xx,yy))
			{
				x = xx;
				y = yy;
	
				playSound("crunch.wav");
			}
		}
	}
	
	private boolean serverSaysItsOKToMove(int xx, int yy) 
	{
		return true; //method stub
	}
	
	private boolean checkWall(int xx, int yy) {
		return zeldaMap.coordIsSolid(xx, yy);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
		{
			moveUp();
		}
		if (key == KeyEvent.VK_A)
		{
			moveBackward();
		}
		if (key == KeyEvent.VK_S)
		{
			moveDown();
		}
		if (key == KeyEvent.VK_D)
		{
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
