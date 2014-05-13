package water;

import javax.swing.JFrame;
import static java.lang.System.out;

public class DumbFrame extends JFrame 
{
	private static int T = 4;
	public DumbFrame()
	{
		System.out.println("DumbFrame comes into existence in the world!! \n My main job is to add a JPanel (GamePanel) to myself.");
		
		// This code is patterned after Skeleton.java on this blog:
		//http://zetcode.com/tutorials/javagamestutorial/basics/
		
		//MY MAIN JOB: ADD GAME PANEL
		add(new GamePanel());
		
		System.out.println("Now for some boiler-plate/set-up code");
		
		setTitle("I'm dumb!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 680);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
		
        int K=6;
        out.println(K);
		K = 5;
		out.println(K);
	}
}
