package silly;

import java.applet.Applet;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.applet.*;

public class Keytester2 extends Applet implements  KeyListener{

/**

* 

*/

private static final long serialVersionUID = 1L;

int width, height;
int x, y;
String s = "";

public void init() {
   width = getSize().width;
   height = getSize().height;
   setBackground( Color.black );

   x = width/2;
   y = height/2;

   addKeyListener( this );
}

private void FuckYourKeybinds(KeyEvent e){

int pressed = e.getKeyCode();

int wanted = KeyEvent.VK_H;

if(pressed == wanted){

System.out.println("HE PRESSED H");


}else{

System.out.println("FUCK NO HE DIDNT");

}

}



@Override

public void keyPressed(KeyEvent e) {

FuckYourKeybinds(e);

}

@Override

public void keyReleased(KeyEvent arg0) {

}

public void keyTyped( KeyEvent e ) {
	System.out.print("typed");
    char c = e.getKeyChar();
    if ( c != KeyEvent.CHAR_UNDEFINED ) {
       s = s + c;
       repaint();
       e.consume();
    }
 }

public void paint( Graphics g ) {
    g.setColor( Color.gray );
    g.drawLine( x, y, x, y-10 );
    g.drawLine( x, y, x+10, y );
    g.setColor( Color.green );
    g.drawString( s, x, y );
 }


}

