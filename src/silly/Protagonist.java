package silly;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
	private Image normalImageP2;
	private Image possessedImageP2;
	
	public boolean iAmPlayerOne;
	
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
		return getImage(false);
	}
	
	public Image getOtherImage() {
		return getImage(true);
	}
	
	private Image getImage(boolean wantOther) {
		if (iAmPlayerOne != wantOther) {
			if (isCurrentlyPossessed)
				return possessedImage;
			return normalImage;
		} else {
			if (isCurrentlyPossessed)
				return possessedImageP2;
			return normalImageP2;
		}
	}
	
	public void setOtherImages(Image normal, Image demon) {
		normalImageP2 = normal;
		possessedImageP2 = demon;
	}
	
	public Protagonist(ZeldaMap zeldaMap_, Image normalImage_, Image possessedImage_)
	{
		zeldaMap = zeldaMap_;
		normalImage = normalImage_;
		possessedImage = possessedImage_;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
	        	System.out.println("Shutdown Hook is running !");
	        	try {
					tellServerILeft();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
    	});
		
//		try {
//			figureOutWhichPlayerIAm();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void figureOutWhichPlayerIAm() throws IOException
	{
		String response = requestFromServer(ZeldaUDPServer.WHICH_PLAYER_REQUEST);
		if (response.equals("ONE"))
		{
			iAmPlayerOne = true;
		} else if (response.equals("TWO")) {
			iAmPlayerOne = false;
		} else {
			System.out.println("don't know which player??");
			System.exit(1); //drop dead. too many players.
		}
	}
	
	private boolean serverSaysItsOKToMove(int xx, int yy) throws IOException 
	{
		String x_str = String.valueOf(xx);
		String y_str = String.valueOf(yy);
		  
		String request = ZeldaUDPServer.MOVE_REQUEST + ":" + x_str + ":" + y_str;
		String response = requestFromServer(request);
		
		if (response.trim().equals("YES"))
			return true;
		return false;
	}
	
	private void tellServerILeft() throws IOException
	{
		requestFromServer(ZeldaUDPServer.I_LEFT_THE_GAME_REQUEST);
	}
	
	private String requestFromServer(String request) throws IOException
	{
		//TODO: try owning a copy of clientSocket?
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//TODO: rewrite so that this process can time out after a while.
		sendData = request.getBytes();
		  
		//SEND
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
		clientSocket.send(sendPacket);
		//RECEIVE
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String response = new String(receivePacket.getData());

		clientSocket.close();
		return response.trim();
	}
	
	private boolean checkWall(int xx, int yy) {
		return zeldaMap.coordIsSolid(xx, yy);
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		int upkey = iAmPlayerOne ? KeyEvent.VK_W : KeyEvent.VK_UP;
		int leftkey = iAmPlayerOne ? KeyEvent.VK_A : KeyEvent.VK_LEFT;
		int downkey = iAmPlayerOne ? KeyEvent.VK_S : KeyEvent.VK_DOWN;
		int rightkey = iAmPlayerOne ? KeyEvent.VK_D : KeyEvent.VK_RIGHT;
		
		if (key == upkey)
		{
			moveUp();
		}
		if (key == leftkey)
		{
			moveBackward();
		}
		if (key == downkey)
		{
			moveDown();
		}
		if (key == rightkey)
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
