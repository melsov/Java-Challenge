package silly;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SillyPanel extends JPanel implements ActionListener, IServerHandlerUpdate
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH_PIXELS = 700;
	public static final int HEIGHT_PIXELS = 700;
	public static final int GUI_FOOTER_HEIGHT = 100;
	
	private static int TILE_WIDTH_PIXELS = WIDTH_PIXELS/ZeldaMap.COLUMNS;
	private static int TILE_HEIGHT_PIXELS = HEIGHT_PIXELS/ZeldaMap.ROWS;
	
	private Image canvas; /*off-screen image*/
	private Graphics cg; /*the graphics of the off-screen image*/
	
	private ZeldaMap zeldaMap = new ZeldaMap();
	private HashMap<Integer, Image> imageLookup = new HashMap<Integer, Image>();
	
	private Protagonist protagonist;
	private ServerHandler serverHandler;
	
	private Timer timer;
	private boolean paused = true;
	
	private boolean otherHasArrived = false;
	
	public SillyPanel()
	{
		setupImageLookup();
	}
	
	public void addNotify()
	{
		super.addNotify();
		doStartOfGameStuff();
	}
	
	private void doStartOfGameStuff()
	{
		String hostAddress = "";
		//TODO: while they fail to give a ping-able server...
		hostAddress = getServerNameFromUser();
				
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		
		setupCanvas();
		drawTheWholeMap();
		GUIPainter.PaintPausedScreen(cg, "WAITING FOR OPPONENT");
		
		//CONSIDER: hostAddress is owned by two objects...
		serverHandler = new ServerHandler(this, hostAddress);
		setupProtagonist(new ProtagonistServerDelegate(hostAddress));
		
		Thread t = new Thread(serverHandler);
		t.start();
		
		//START LATER
		timer = new Timer(12, this);
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
		if (!paused)
		{
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
		startGame();
	}
	
	private String getGameHandleFromUser()
	{
		Frame f = new Frame();
		f.setSize(400,500);
		CustomDialog cd = new CustomDialog(f, "Tell me your name:", RandomNameGenerator.GetName());
		cd.pack();
		cd.setVisible(true);
		return cd.getAnswer();
	}
	
	private String getServerNameFromUser()
	{
		Frame f = new Frame();
		f.setSize(400,500);
		CustomDialog cd = new CustomDialog(f, "Tell me the server that you want to connect to:", "localhost");
		cd.pack();
		cd.setVisible(true);
		return cd.getAnswer();
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
		drawEverything();
	}
	
	private void drawEverything()
	{
		drawTheWholeMap();
		drawOther();
		drawProtagonist();
		GUIPainter.PaintGameInfo(cg, protagonist.myStats, protagonist.otherStats);
	}
	
	private void doPausedRelatedStuff()
	{
		GUIPainter.PaintPausedScreen(cg, "PAUSED");
	}
	
	private void updateMap(int x, int y, int tile_type)
	{
		zeldaMap.set(x, y, tile_type);
		drawTileAt(x,y);
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
	
	private void setupImageLookup()
	{
		Image wallImage = imageWithName("wall.png");
		imageLookup.put(ZeldaMap.WALL, wallImage);
		Image groundImage = imageWithName("ground.png");
		imageLookup.put(ZeldaMap.GROUND, groundImage);
		Image redJellyImage = imageWithName("jelly.png");
		imageLookup.put(ZeldaMap.RED_JELLY, redJellyImage);
	}

	private void setupProtagonist(IServerRequest protagServerDelegate)
	{
		Image normImage = imageWithName("normalLink.png");
		Image demonImage = imageWithName("demonLink.png");
		
		if (zeldaMap == null) { System.out.println("not cool. we need a zelda map at this point"); System.exit(1); }
		
		String playerName = getGameHandleFromUser();
		
		protagonist = new Protagonist(zeldaMap, normImage, demonImage, protagServerDelegate);
		protagonist.myStats.playerName = playerName;
		protagonist.iAmPlayerOne = serverHandler.playerNumber == 0 ? true : false;
		int playerNum = protagonist.iAmPlayerOne ? 0 : 1;
		Point2I spawnPoint = zeldaMap.spawnPointForPlayer(playerNum);
		protagonist.setX(spawnPoint.x);
		protagonist.setY(spawnPoint.y);
		
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
		
	private class MyKeyAdapter extends KeyAdapter
	{
//		public void keyReleased(KeyEvent e) {
//            protagonist.keyPressed(e);
//        }

        public void keyPressed(KeyEvent e) {
        	protagonist.keyPressed(e);
        }

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
		// TODO Auto-generated method stub
		protagonist.otherStats.coord = point;
	}



}
