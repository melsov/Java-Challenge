package silly;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import silly.server.ServerCommunication;

public class SillyPanel extends JPanel implements ActionListener, IServerHandlerUpdate
{
	
	public SillyPanel(boolean wantSameScreenMode, boolean runningClientAndServer)
	{
		sameScreenMode = wantSameScreenMode;
		isAlsoRunningServer = runningClientAndServer;
		setupImageLookup();
	}
	
	public void addNotify()
	{
		super.addNotify();
		doStartOfGameStuff();
	}
	
	private void doStartOfGameStuff()
	{
		D.print("Beginning of start of game stuff");
		gameState = 0;
		gameStateString = "PAUSED";
		paused = true;
		otherHasArrived = false;
		endThreads();
		
		zeldaMap = new ZeldaMap();
		//CONSIDER: hostAddress is owned by two objects...
		protagonist = null;
		serverHandler = null;
		
		setupServerHandler();
		
		setupCanvas();
		drawTheWholeMap();
		GUIPainter.PaintGameStateScreen(cg, "WAITING FOR OPPONENT");
		
		setupProtagonist(new ProtagonistServerDelegate(hostAddress));
		
		Thread t = new Thread(serverHandler);
		t.start();
		
		timer = new Timer(12, this);
		//WILL START TIME LATER
	}
	
	private void setupServerHandler()
	{
		if (!sameScreenMode && !isAlsoRunningServer) // need to ask user for the server name
		{
			String problemConnectingString = "";
			//while they fail to give a ping-able server...
			while(serverHandler == null || serverHandler.connectionStatus != HandlerConnectionStatus.ACCEPTED)
			{
				hostAddress = getServerNameFromUser(hostAddress, problemConnectingString);
				serverHandler = new ServerHandler(this, hostAddress);
				if (serverHandler.connectionStatus == HandlerConnectionStatus.REJECTED)
					problemConnectingString = "THAT SERVER ALREADY HAS TWO PEOPLE. SORRY. ";
				else if (serverHandler.connectionStatus == HandlerConnectionStatus.NO_CONNECTION)
					problemConnectingString = "DIDN'T FIND THAT SERVER. SORRY. ";
			}
		}
		else {
			serverHandler = new ServerHandler(this, hostAddress);
			if (serverHandler.connectionStatus != HandlerConnectionStatus.ACCEPTED) {
				D.print("strange: tryinng to play with 3 or more people on the same computer?. I will die now.");
				System.exit(2);
			}
		}
	}
	
	private void endThreads()
	{
		if (serverHandler != null) {
			serverHandler.timeToQuit = true;
		}
		if (timer != null) {
			timer.stop();
		}
	}
	
	private void startGame()
	{
		if (paused)
		{
			paused = false;
			timer.start();
		}
	}
	
	private void pauseGame()
	{
		if (!paused) {
			paused = true;
		}
	}

	@Override
	public void otherHasArrived() {
		otherHasArrived = true;
		if (protagonist != null) {
			protagonist.otherHasArrived();
		} 
	}
	
	public GameStats playerGameStats() {
		return protagonist.myStats;
	}
	
	public void introduceOther(GameStats otherStats) 
	{
		protagonist.otherStats = otherStats;
		
		CountDown countDown = new CountDown();
		Thread t = new Thread(countDown);
		t.start();
	}
	
	private static String getGameHandleFromUser(String defaultName)
	{
		return CustomDialog.GetGameHandleFromUser(defaultName);
	}
	
	private static String getServerNameFromUser(String theHostAddress, String problemString)
	{
		return CustomDialog.GetServerNameFromUser(theHostAddress, problemString);
	}
	
	/*
	 * Gets called by the timer every n milliseconds
	 */
	@Override
	public void actionPerformed(ActionEvent the_event) 
	{
		if (!paused)
		{
			changeStuffAboutTheGame();
		} else {
			doPausedRelatedStuff();
		}
		repaint(); //causes the 'paint' method to be called again.
	}
	
	private void changeStuffAboutTheGame()
	{
		//change stuff about the game
		checkWonOrLost();
		drawEverything();
		checkConnections();
	}
	
	private void checkWonOrLost()
	{
		int winlosestate = 0;
		String result_string = "";
		if (protagonist.myStats.isVictorious) {
			winlosestate = 1;
			result_string = "YOU WON! PRESS 'R' TO RESTART, 'Q' TO QUIT";
		} else if (protagonist.otherStats.isVictorious) {
			winlosestate = 2;
			result_string = "YOU LOST. PRESS 'R' TO RESTART, 'Q' TO QUIT";
		} else if (zeldaMap.getJellyCount() < 1) {
			winlosestate = 3;
			result_string = "THE GAME IS OVER AND, IN THE END, NOTHING HAPPENED. PRESS 'R' TO RESTART, 'Q' TO QUIT";
		}
		
		if (winlosestate > 0) {
			gameStateString = result_string;
			paused = true;
			gameState = winlosestate;
		}
	}
	
	private void checkConnections()
	{
		if (protagonist.lostConnection())
		{
			D.print("protag lost connection. leaving now...");
			System.exit(10);
		}
	}
	
	private void drawEverything()
	{
		drawTheWholeMap();
		drawOther();
		drawProtagonist();
		PlayerInfoPack infoPack = new PlayerInfoPack(protagonist, isAlsoRunningServer);
		GUIPainter.PaintGameInfo(cg, infoPack); 
	}
	
	private void doPausedRelatedStuff()
	{
		if (gameState == 0) {
			GUIPainter.PaintGameStateScreen(cg, gameStateString);
			return;
		} 

		GUIPainter.PaintResultsForBoth(cg, protagonist.myStats, protagonist.otherStats);

	}
	
	private void setupCanvas()
	{
		if (canvas == null)
		{
			canvas = createImage(WIDTH_PIXELS,HEIGHT_PIXELS + GUI_FOOTER_HEIGHT) ;
			cg = canvas.getGraphics();
		}
		cg.setColor(Color.white);
		cg.fillRect(0, 0, WIDTH_PIXELS, HEIGHT_PIXELS + GUI_FOOTER_HEIGHT);
	}
	
	public void paint(Graphics g)
    {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, 0,0,WIDTH_PIXELS, HEIGHT_PIXELS + GUI_FOOTER_HEIGHT, null);
    }
	
	private void drawTheWholeMap()
	{
		for(int i = 0; i < ZeldaMap.COLUMNS; ++i)
		{
			for(int j = 0; j < ZeldaMap.ROWS; ++j)
			{
				drawTileAt(i,j);
			}
		}
	}
	
	private void drawTileAt(int x, int y)
	{
		int tile_type = zeldaMap.tileAt(x ,y );
		Image tile_image = imageLookup.get(tile_type);
		cg.drawImage(tile_image, x * TILE_WIDTH_PIXELS, y * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
		int jelly_type = zeldaMap.jellyAt(x,y);
		if (jelly_type != 0) {
			cg.drawImage(imageLookup.get(jelly_type),  x * TILE_WIDTH_PIXELS, y * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
		}
	}
	
	private void drawProtagonist()
	{
		Image protagImage = protagonist.getImage();
		cg.drawImage(protagImage, protagonist.getX() * TILE_WIDTH_PIXELS, protagonist.getY() * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
	}
	
	private void drawOther()
	{
		Image otherImage = protagonist.getOtherImage();
		cg.drawImage(otherImage, protagonist.otherStats.coord.x * TILE_WIDTH_PIXELS, protagonist.otherStats.coord.y * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
	}
	
	private void setupImageLookup() {
        Image wallImage = imageWithName("wall.png");
        imageLookup.put(ZeldaMap.WALL, wallImage);
        Image groundImage = imageWithName("ground.png");
        imageLookup.put(ZeldaMap.GROUND, groundImage);
        Image doorNorthImage = imageWithName("door_north.png");
        imageLookup.put(ZeldaMap.DOOR_NORTH, doorNorthImage);
        Image redJellyImage = imageWithName("jelly.png");
        imageLookup.put(ZeldaMap.RED_JELLY, redJellyImage);
    }

	private void setupProtagonist(IServerRequest protagServerDelegate)
	{
		Image normImage = imageWithName("normalLink.png");
		Image demonImage = imageWithName("demonLink.png");
		
		if (zeldaMap == null) { System.out.println("not cool. we need a zelda map at this point"); System.exit(1); }
		
		String playerName = getGameHandleFromUser(nameOfPlayer);
		
		protagonist = new Protagonist(zeldaMap, normImage, demonImage, protagServerDelegate);
		protagonist.myStats.playerName = playerName;
		protagonist.myStats.playerIndex = serverHandler.playerNumber; // == 0 ? true : false;
		int playerNum = protagonist.iAmPlayerOne() ? 0 : 1;
		Point2I spawnPoint = zeldaMap.spawnPointForPlayer(playerNum);
		protagonist.setCoord(spawnPoint); 

		protagonist.setOtherImages( imageWithName("normalLinkP2.png"), imageWithName("demonLinkP2.png"));
		
		if (otherHasArrived) {
			protagonist.otherHasArrived();
		}
	}
	
	private Image imageWithName(String imageName) 
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
        return ii.getImage();
	}
	
	public void doKeyReleased(KeyEvent e) 
	{
		if (gameState > 0) 
		{
    		int restartKey = KeyEvent.VK_R;
    		int quitKey = KeyEvent.VK_Q;
    		int helpKey = KeyEvent.VK_H;
    		int key = e.getKeyCode();
    		if (key == restartKey)
    		{
    			//sleep for a moment, to make sure the 'r'
    			//doesn't get typed into the next dialog box.
    			try {
					Thread.sleep(20);
				} catch (InterruptedException e1) { e1.printStackTrace(); }
				
    			restart();
    		} else if (key == quitKey) {
    			System.exit(0);
    		} else if (key == helpKey) {
    			showHelp();
    		}
    	} 
	}
	
	 public void doKeyPressed(KeyEvent e) {
     	if (gameState == 0) {
	        protagonist.keyPressed(e);
     	} 
     }
	 
	 private void showHelp()
	 {
		 JOptionPane.showMessageDialog(new Frame(),
				    "Collect enough Jelly to become possessed.\n" +
				    "Once you are possessed you can go through the door.\n" +
				    "The player who goes through the door first wins.");
	 }
		
	 private class CountDown implements Runnable
	 {
		@Override
		public void run() 
		{
			for(int i = 3; i > 0; --i)
			{
				SoundPlayer.PlaySound("tok.wav");
				String count = String.valueOf(i);
				GUIPainter.PaintGameStateScreen(cg, count);
				repaint();
				sleeps(1500);
			}
			
			startGame();
		}
		
		private void sleeps(int millis) {
			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		 
	 }
	
	private void restart()
	{
		doStartOfGameStuff();
	}

	@Override
	public void otherGotJellyAt(Point2I point) {
		zeldaMap.removeJelly(point.x,point.y);
	}

	@Override
	public void updateOtherJellyCount(int otherJellyCount) {
		protagonist.otherStats.jellyCount = otherJellyCount;
		
	}

	@Override
	public void updateOtherCoord(Point2I point) {
		if (point == null)
			return;
		protagonist.otherStats.coord = point;
	}

	@Override
	public void pushStateChanged(ServerCommunication scomm) {
		protagonist.gameStateChanged(scomm);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH_PIXELS = 700;
	public static final int HEIGHT_PIXELS = 700;
	public static final int GUI_FOOTER_HEIGHT = 150;
	
	private static int TILE_WIDTH_PIXELS = WIDTH_PIXELS/ZeldaMap.COLUMNS;
	private static int TILE_HEIGHT_PIXELS = HEIGHT_PIXELS/ZeldaMap.ROWS;
	
	private Image canvas; /*off-screen image*/
	private Graphics cg; /*the graphics of the off-screen image*/
	
	private ZeldaMap zeldaMap;
	private HashMap<Integer, Image> imageLookup = new HashMap<Integer, Image>();
	
	private Protagonist protagonist;
	private ServerHandler serverHandler;
	
	private Timer timer;
	private boolean paused = true;
	
	private boolean otherHasArrived = false;
	
	private String gameStateString = "PAUSED";
	private int gameState = 0;
	
	private String hostAddress = "localhost";
	private String nameOfPlayer = "";
	
	private boolean sameScreenMode;
	private boolean isAlsoRunningServer;


}
