package silly;

import static java.lang.System.out;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SillyPanel extends JPanel implements ActionListener
{
	public static final int WIDTH_PIXELS = 700;
	public static final int HEIGHT_PIXELS = 700;
	
	private static int TILE_WIDTH_PIXELS = WIDTH_PIXELS/ZeldaMap.COLUMNS;
	private static int TILE_HEIGHT_PIXELS = HEIGHT_PIXELS/ZeldaMap.ROWS;
	
	private Image canvas; /*off-screen image*/
	private Graphics cg; /*the graphics of the off-screen image*/
	
	private ZeldaMap zeldaMap = new ZeldaMap();
	private HashMap<Integer, Image> imageLookup = new HashMap<Integer, Image>();
	
	private Protagonist protagonist;
	private ServerListener serverListener;
	
	private Timer timer;
	
	public SillyPanel()
	{
		setupImageLookup();
	}
	
	public void addNotify()
	{
		super.addNotify();
		
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		
		setupCanvas();
		drawTheWholeMap();
		
		serverListener = new ServerListener();
		
		setupProtagonist();
		
		Thread t = new Thread(serverListener);
		t.start();
		
		//MAKE THE GAME START
		timer = new Timer(12, this);
		timer.start();
	}
	
	/*
	 * Gets called by the timer every n milliseconds
	 */
	@Override
	public void actionPerformed(ActionEvent the_event) 
	{
		changeStuffAboutTheGame();
		repaint(); //causes the 'paint' method to be called again.
	}
	
	private void changeStuffAboutTheGame()
	{
		//change stuff about the game
		drawTheWholeMap();
		drawOther();
		drawProtagonist();
		
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
			canvas = createImage(WIDTH_PIXELS,HEIGHT_PIXELS);
			cg = canvas.getGraphics();
		}
		cg.setColor(Color.white);
		cg.fillRect(0, 0, WIDTH_PIXELS, HEIGHT_PIXELS);
	}
	
	public void paint(Graphics g)
    {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, 0,0,WIDTH_PIXELS, HEIGHT_PIXELS, null);
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
	}
	
	private void drawProtagonist()
	{
		Image protagImage = protagonist.getImage();
		cg.drawImage(protagImage, protagonist.getX() * TILE_WIDTH_PIXELS, protagonist.getY() * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
	}
	
	private void drawOther()
	{
		Image otherImage = imageWithName("normalLinkP2.png"); // protagonist.getOtherImage();
		cg.drawImage(otherImage, serverListener.otherPlayerCoord.x * TILE_WIDTH_PIXELS, serverListener.otherPlayerCoord.y * TILE_HEIGHT_PIXELS, TILE_WIDTH_PIXELS, TILE_HEIGHT_PIXELS, null);
	}
	
	private void setupImageLookup()
	{
		Image wallImage = imageWithName("wall.png");
		imageLookup.put(ZeldaMap.WALL, wallImage);
		Image groundImage = imageWithName("ground.png");
		imageLookup.put(ZeldaMap.GROUND, groundImage);
	}

	private void setupProtagonist()
	{
		Image normImage = imageWithName("normalLink.png");
		Image demonImage = imageWithName("demonLink.png");
		
		
		if (zeldaMap == null) // get mad
		{
			System.out.println("not cool. we need a zelda map at this point");
		}
		protagonist = new Protagonist(zeldaMap, normImage, demonImage);
		protagonist.iAmPlayerOne = serverListener.playerNumber == 0 ? true : false;
		int playerNum = protagonist.iAmPlayerOne ? 0 : 1;
		IPoint2 spawnPoint = zeldaMap.spawnPointForPlayer(playerNum);
		protagonist.setX(spawnPoint.x);
		protagonist.setY(spawnPoint.y);
		
		protagonist.setOtherImages( imageWithName("normalLinkP2.png"), imageWithName("demonLinkP2.png"));
		
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
	
	private class ServerListener implements Runnable
	{
//		public static final int SERVER_LISTENER_PORT = 9871;
		public IPoint2 otherPlayerCoord = new IPoint2(0,0);
		DatagramSocket listenerSocket = null;
		public int playerNumber = -1;
		
		public ServerListener()
		{
			try {
				listenerSocket = new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		private void sayHiToServer() 
		{
			String response = "";
			try {
				response = requestFromServer(ZeldaUDPServer.SAY_HI_REQUEST);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (response.equals("ONE"))
			{
				playerNumber = 0;
			} else if (response.equals("TWO")) {
				playerNumber = 1;
			}
			
		}
		
		private String requestFromServer(String request) throws IOException
		{
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			
			//TODO: rewrite so that this process can time out after a while.
			sendData = request.getBytes();
			  
			//SEND
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
			listenerSocket.send(sendPacket);
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			listenerSocket.receive(receivePacket);
			String response = new String(receivePacket.getData());

//			listenerSocket.close();
			return response.trim();
		}
		
		@Override
		public void run() 
		{
			byte[] receiveData = new byte[1024];

			while(true)
			{
				receiveData = new byte[1024]; //wipe out any old data
				//RECEIVE
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					listenerSocket.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String sentence = new String( receivePacket.getData());
				handleClientQuery(sentence);

	       }
			
		}
		
		private void handleClientQuery(String message)
		{
			String[] msg_parts = message.split(":");
			String msg_header = msg_parts[0].trim();
			
			if (msg_header.equals(ZeldaUDPServer.OTHER_MOVED)) {
				handleOtherMoved(msg_parts);
			}
		}
		
		private void handleOtherMoved(String[] msg_parts)
		{
			String xx = msg_parts[1];
			String yy = msg_parts[2];
			
			int x = -1;
			int y = -1;
			
			try {
				x = Integer.parseInt(xx.trim());
				y = Integer.parseInt(yy.trim());
			} catch(java.lang.NumberFormatException e) {
				out.println("Exception...");
			}
			otherPlayerCoord = new IPoint2(x,y);
		}
	}

}
