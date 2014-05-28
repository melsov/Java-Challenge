package cyrusantivirus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Control extends JPanel implements ActionListener
{
	Timer animate = new Timer(20,this);
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static Rectangle2D screen = new Rectangle2D.Double(0,0,dim.getWidth(),dim.getHeight());
	public static int timerRepeats=0;
	int textOpacity=1;
	
	public static void main(String[] args){
		int CanvasWidth;
		int CanvasHeight;
		JFrame fr = new JFrame();
		CanvasWidth = (int) dim.getWidth();
		CanvasHeight = (int) dim.getHeight();
		fr.setSize(CanvasWidth, CanvasHeight );
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(fr);
		Control db = new Control();
		db.setSize(CanvasWidth, CanvasHeight);
		fr.add(db);
		fr.setVisible(true);
		fr.setResizable(false);	
	}
	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	public void addNotify(){
		super.addNotify();
		animate.start();
	}
	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timerRepeats++;
		repaint();
			
	}
	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	public void paint(Graphics g2){
		Graphics2D g = (Graphics2D) g2;
		super.paintComponent(g);
		g.setColor(new Color(55,132,0,255));
		g.fill(screen);
		startAnimation(g);
	}

	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	public void loadImage(Graphics2D g,String file){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(file));
		} catch (IOException e) {
		}
		g.drawImage(img,null,0,0);
	}
	
	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
	public void startAnimation(Graphics2D g){
		if(timerRepeats>100 && timerRepeats<102){g.setColor(Color.blue);g.fill(screen);loadImage(g,"CyrusAntiVirus.png");}
		if(timerRepeats>200 && timerRepeats<203){ g.setColor(Color.blue);g.fill(screen);loadImage(g,"CyrusAntiVirus.png");}
		if(timerRepeats>300 && timerRepeats<304){ g.setColor(Color.blue);g.fill(screen);loadImage(g,"CyrusAntiVirus.png");}
		if(timerRepeats>400 && timerRepeats<451){ g.setColor(Color.blue);g.fill(screen);loadImage(g,"CyrusAntiVirus.png");}
		if(timerRepeats>450){g.setColor(Color.blue);g.fill(screen);loadImage(g,"CyrusAntiVirus.png");}
	}
	//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
}
