package silly;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.omg.CORBA.portable.InputStream;

import silly.server.ServerCommunication;
import silly.server.ZeldaUDPServer;

public class Protagonist 
{
	public ZeldaMap zeldaMap;
	
	private Image normalImage;
	private Image possessedImage;
	private Image normalImageP2;
	private Image possessedImageP2;
	
	private IServerRequest serverDelegate;

//	public boolean iAmPlayerOne;
	public GameStats myStats = new GameStats();
	public GameStats otherStats = new GameStats();
	
	public static int JELLY_POSSESSED_THRESHOLD = 5;
	
	public boolean iAmPlayerOne() {
		return myStats.playerIndex == 0;
	}
	
	public int getX() {
		return myStats.coord.x;
	}
	public void setX(int x_){
		myStats.coord.x = x_;
	}
	public int getY() {
		return myStats.coord.y;
	}
	public void setY(int y_) {
		myStats.coord.y = y_;
	}
	public void setCoord(Point2I p) {
		myStats.coord = p;
	}
	
	public boolean lostConnection() {
		return serverDelegate.lostConnection();
	}
	
	public Image getImage() {
		if (iAmPlayerOne()) {
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
		if (iAmPlayerOne()) {
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
		D.print("Hark. a new protagonist enters the world");
		serverDelegate = serverDelegate_;
		zeldaMap = zeldaMap_;
		normalImage = normalImage_;
		possessedImage = possessedImage_;
		
		//inform server before shutting down.
		//(Rumor has it that this doesn't always work. Works in testing however)
		Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
	        	System.out.println("Shutdown Hook is running! (Protagonist class)");
	        	try {
					tellServerILeft();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
    	});
	}
	
	public void otherHasArrived() 
	{
		sendServerMyIntroduction();
		sendInitialJellyCount();
	}
	
	public void gameStateChanged(ServerCommunication scomm)
	{
		setGameStatsWithPlayerIndex(scomm);
	}
	
	private void setGameStatsWithPlayerIndex(ServerCommunication scomm) {
		if (scomm == null || scomm.gameStats == null)
			return;
		if (myPlayerIndex() == scomm.playerIndex)
		{
			myStats = scomm.gameStats;
			return;
		}
		otherStats = scomm.gameStats;
		
	}
	private int myPlayerIndex() {
		return iAmPlayerOne() ? 0 : 1;
	}
	
	private void moveForward() {
		moveTo(myStats.coord.plusX());
	}
	
	private void moveBackward() {
		moveTo(myStats.coord.minusX());
	}
	
	private void moveUp() {
		moveTo(myStats.coord.minusY());
	}
	
	private void moveDown() {
		moveTo(myStats.coord.plusY());
	}
	
	private void moveTo(Point2I point) {
		if (!checkWall(point))
		{
			try {
				if (serverSaysItsOKToMove(point.x, point.y))
				{
					myStats.coord = point;
					
					if (zeldaMap.jellyAt(point.x, point.y) != 0) {
						myStats.jellyCount++;
						tellServerThatIGotJelly(point.x, point.y);
						zeldaMap.removeJelly(point.x, point.y);
						playSound("tok.wav");
						checkPossessed();
					} else if (zeldaMap.doorAt(myStats.coord)) {
						if (myStats.isPossessed) {
							win();
						}
					} else {
						playSound("crunch.wav");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void endGameOnServer()
//	{
////		try {
////			requestFromServer(ZeldaUDPServer.I_WON_REQUEST);
////		} catch (IOException e) { e.printStackTrace(); }
//		sendServerMyStatsWithHeader(ZeldaUDPServer.I_WON_REQUEST);
//	}
	
	private void win()
	{
		D.print("****ABOUT TO WIN*****\n\n");
		myStats.isVictorious = true;
		sendServerMyStatsWithHeader(ZeldaUDPServer.I_WON_REQUEST);
	}
	
	private void checkPossessed()
	{
		if (myStats.jellyCount == JELLY_POSSESSED_THRESHOLD)
		{
			myStats.isPossessed = true;
			sendServerUpdateWithMyStats();
		}
	}
	
	private void sendServerUpdateWithMyStats()
	{
		sendServerMyStatsWithHeader(ZeldaUDPServer.STATE_CHANGED_REQUEST);
	}
	
	private void sendServerMyStatsWithHeader(String header)
	{
		ServerCommunication comm = ServerCommunication.ServerCommunicationWithProtagonistAndHeader(this, header);
		try {
			requestFromServer(comm.toString());
		} catch (IOException e) { e.printStackTrace();
		}
	}
	
	private void sendInitialJellyCount()
	{
		int jcount =  zeldaMap.getJellyCount();
		if (jcount < 1) { System.out.println("BAD INITIAL JELLY COUNT  exiting..."); System.exit(2); }
		sendServerIntUpdate(ZeldaUDPServer.NOTIFY_INITIAL_JELLY_COUNT_REQUEST, jcount);
	}
	
	private void sendServerIntUpdate(String header, int the_int)
	{
		ServerCommunication comm = ServerCommunication.ServerCommunicationWithHeaderAndIntValue(header, this, the_int);
		try {
			requestFromServer(comm.toString());
		} catch (IOException e) { e.printStackTrace();
		}
	}

	private boolean serverSaysItsOKToMove(int xx, int yy) throws IOException 
	{
		String x_str = String.valueOf(xx);
		String y_str = String.valueOf(yy);
		String playerNum = String.valueOf(iAmPlayerOne() ? 0 : 1);
		  
		String request = ZeldaUDPServer.MOVE_REQUEST + ":" + x_str + ":" + y_str + ":" + playerNum;
		String response = requestFromServer(request);
		
		if (response.trim().equals("YES"))
			return true;
		return false;
	}

	//TODO: merge a bit with sendServerMyStatsWithHeader...
	private void sendServerMyIntroduction()
	{
		//extend introduction to other
		ServerCommunication comm = new ServerCommunication(ZeldaUDPServer.INTRODUCTION_REQUEST, this.myStats, iAmPlayerOne() ? 0 : 1);
		try {
			requestFromServer(comm.toString());
		} catch (IOException e) { e.printStackTrace();
		}
	}
	
	private void tellServerThatIGotJelly(int xx, int yy) throws IOException 
	{
		String x_str = String.valueOf(xx);
		String y_str = String.valueOf(yy);
		String playerNum = String.valueOf(iAmPlayerOne() ? 0 : 1);
		String jCountString = String.valueOf(myStats.jellyCount);
		String request = ZeldaUDPServer.I_GOT_JELLY + ":" + x_str + ":" + y_str + ":" + playerNum + ":" + jCountString;
		String response = requestFromServer(request);
	}
	
	private void tellServerILeft() throws IOException
	{
		sendServerMyStatsWithHeader(ZeldaUDPServer.I_LEFT_THE_GAME_REQUEST);
//		requestFromServer(ZeldaUDPServer.I_LEFT_THE_GAME_REQUEST);
	}
	
	private String requestFromServer(String request) throws IOException
	{
		return serverDelegate.requestFromServer(request);
	}
	
	private boolean checkWall(Point2I point) {
		return zeldaMap.coordIsSolid(point.x, point.y);
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
			upkey = iAmPlayerOne() ? KeyEvent.VK_W : KeyEvent.VK_UP;
			leftkey = iAmPlayerOne() ? KeyEvent.VK_A : KeyEvent.VK_LEFT;
			downkey = iAmPlayerOne() ? KeyEvent.VK_S : KeyEvent.VK_DOWN;
			rightkey = iAmPlayerOne() ? KeyEvent.VK_D : KeyEvent.VK_RIGHT;
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
		SoundPlayer.PlaySound(url);
	}
	
}
