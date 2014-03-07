package trirunner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.Timer;
import javax.swing.JApplet;
import javax.swing.JFrame;


/*
 * Courtesy: http://zetcode.com/tutorials/javagamestutorial/
 * 
 */

public class TriRunner extends JApplet implements ActionListener 
{

	/**
	 * @param args
	 */
	
	private static Timer timer;
	
	private Shapester shape1;
	private Zigster zig1;
	
	/*
	 * Do me a favor and 
	 * just blindly accept 
	 * that we need this 
	 * main function to get 
	 * things rolling.
	 */
	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Tri Runner");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        JApplet applet = new TriRunner(); 
        f.getContentPane().add("Center", applet);
        applet.init();
        
        f.pack();
        f.setSize(new Dimension(550,500));
        f.setVisible(true);
		
		TriRunner tr = new TriRunner();
		tr.startDoingStuff();
	}
	
	public TriRunner()
	{
	}
	
	/*
	 * Do me a favor:
	 * just accept that
	 * this function is called
	 * at the start of the start of
	 * everything when the program starts.
	 */
	public void init()
	{
		startDoingStuff();
	}
	
	public void startDoingStuff()
	{
		shape1 = new Shapester();
		
		zig1 = new Zigster();
		zig1.setx(80);
		
		timer = new Timer(5, this);
        timer.start();
	}
	
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
		g.clearRect(0, 0, 550, 500);
		

		drawAShapester(g, shape1);
//		drawAShapester(g, zig1);
		
	}
	
	private void drawAShapester(Graphics2D g, Shapester the_shapester)
	{
		
		setForeground(the_shapester.getColor());
		
		Point2D.Double[] points = the_shapester.getPoints();
		Point2D.Double firstPoint = points[0];
		GeneralPath line = new GeneralPath(GeneralPath.WIND_EVEN_ODD, points.length );
		
		line.moveTo(firstPoint.x, firstPoint.y);
		
		for (int i = 1; i < points.length ; ++i)
		{
			Point2D.Double nextPoint = points[i];
			line.lineTo(nextPoint.x, nextPoint.y);
		}
		
		g.fill(line);
//		g.draw(line);
	}

	@Override
	public void actionPerformed(ActionEvent the_event) 
	{
	
		shape1.moveALittle();
		
		zig1.moveALittle();
		
		repaint();
		
		
	}

}
