package silly;

import java.awt.Color;
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
		
		setupProtagonist();
		
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
			System.out.println("not cool. we need a zelda map right now");
		}
		protagonist = new Protagonist(zeldaMap, normImage, demonImage);
		protagonist.setX(5);
		protagonist.setY(5);
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

}
