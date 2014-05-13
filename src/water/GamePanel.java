package water;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Keys keysLi = new Keys();

	public GamePanel() {
		//Game panel is born.
	}
	
	public void addNotify()
	{
		//This is called when game panel gets added by DumbFrame
		//Treat it like the Applet's init().
		//Most things can be done either here or in the constructor
		//But you probably want to start the timer here
		//Because there's a (small) chance that you won't be added to the JFrame right away.
		
		System.out.println("Verify that you see this message when you start the game.");
		System.out.println("And pretty much treat this like you'd treat an Applet class's 'init()'");
		
		Timer t = new Timer(50, this);
		t.start();
		
		addKeyListener(keysLi);
		setFocusable(true);
	}
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
		Ellipse2D.Double e = new Ellipse2D.Double(20, 40, 300, 400);
		g.setPaint(Color.RED);
		g.draw(e);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.print("Action performed was called.");
	}
}
