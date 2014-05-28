package Singles;

import java.applet.*;

import java.awt.*;

import java.awt.event.*;



import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.Timer;

import drawbot.DrawBot;



import java.awt.event.KeyEvent;



public class MovingLine2 extends JPanel implements ActionListener {

/**

* 

*/

private static final long serialVersionUID = 1L;

public boolean running = true;

public boolean debug = false;

static int appletXsize = 1280;

    static int appletYsize = 650;

int mx1 = appletXsize/2;

    int my1 = appletYsize/2;

    Color cholera = new Color(255,0,255,255);

    public  int x2 = 100;

    public int y2 = 100;

    public  int x1 = 100;

    public int y1 = 100;

    public float PenSize = 20;

  private static Timer timer;

  float[] dash1 = {0f, 2f, 0f};
  
  public static void main(String[] args) {
	  //You have to make a JFrame to hold the JPanel
	  	JFrame fr = new JFrame();
		fr.setSize(appletXsize, appletYsize);
		
		// Moving line is a JPanel
		MovingLine2 ml2 = new MovingLine2();
		ml2.setSize(appletXsize, appletYsize);
		
		//Which you can add to the frame
		fr.add(ml2);
		// Yes, make this jFrame visible!
		fr.setVisible(true);
		fr.setResizable(true);
		//done!
  }

   

public void paint (Graphics garrison_jr){

Graphics2D g = (Graphics2D) garrison_jr;

g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

BasicStroke roundEnd = new BasicStroke(PenSize,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

g.setStroke(roundEnd);

g.setPaint(cholera);

g.drawLine(mx1, my1, x2, y2);


}






public void addNotify(){

super.addNotify();

//setSize(appletXsize,appletYsize);

timer = new Timer(10,this);



timer.start();

}





public void flee() {

  if (debug==true){

  System.out.println("FleeCalled");

  }

  x2++;

            repaint();



       

}







@Override

public void actionPerformed(ActionEvent arg0) {

if (debug==true){

System.out.println("ActionPerformedCalled");

}

flee();



}



 





} 

