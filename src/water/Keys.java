package water;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class Keys implements KeyListener, MouseMotionListener, MouseListener{

public static  boolean Apress;
public   boolean Bpress;
public   boolean Cpress;
public   boolean Dpress;
public   boolean Epress;
public   boolean Fpress;
public   boolean Gpress;
public   boolean Hpress;
public   boolean Ipress;
public   boolean Jpress;
public   boolean Kpress;
public   boolean Lpress;
public   boolean Mpress;
public   boolean Npress;
public static boolean Opress;
public static boolean Ppress;
public static boolean Qpress;
public static boolean Rpress;
public static boolean Spress;
public static boolean Tpress;
public static boolean Upress;
public static boolean Vpress;
public static boolean Wpress;
public static boolean Xpress;
public static boolean Ypress;
public static boolean Zpress;
public static boolean SPACEpress;
public static boolean SHIFTpress;
public static boolean ESCpress;
public static boolean CTRLpress;
public static boolean num1press;
public static boolean num2press;
public static boolean num3press;
public static boolean num4press;
public static boolean num5press;
public static boolean num6press;
public static boolean num7press;
public static boolean num8press;
public static boolean num9press;
public static boolean num0press;
public static Point2D mouseLoc = new Point2D.Float();
public static boolean mouseDown;

	public Keys() {
		System.out.println("keys starting up");
	}

@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("got a key press. key code: " + e.getKeyCode() + " code for a: " + KeyEvent.VK_A);
int pressed = e.getKeyCode();
if(pressed == KeyEvent.VK_A){Apress=true; System.out.println("got A pressed. Apress now: " + Apress); }
if(pressed == KeyEvent.VK_B){Bpress=true; }
if(pressed == KeyEvent.VK_C){Cpress=true; }
if(pressed == KeyEvent.VK_D){Dpress=true; }
if(pressed == KeyEvent.VK_E){Epress=true; }
if(pressed == KeyEvent.VK_F){Fpress=true; }
if(pressed == KeyEvent.VK_G){Gpress=true; }
if(pressed == KeyEvent.VK_H){Hpress=true; }
if(pressed == KeyEvent.VK_I){Ipress=true; }
if(pressed == KeyEvent.VK_J){Jpress=true; }
if(pressed == KeyEvent.VK_K){Kpress=true; }
if(pressed == KeyEvent.VK_L){Lpress=true; }
if(pressed == KeyEvent.VK_M){Mpress=true; }
if(pressed == KeyEvent.VK_N){Npress=true; }
if(pressed == KeyEvent.VK_O){Opress=true; }
if(pressed == KeyEvent.VK_P){Ppress=true; }
if(pressed == KeyEvent.VK_Q){Qpress=true; }
if(pressed == KeyEvent.VK_R){Rpress=true; }
if(pressed == KeyEvent.VK_S){Spress=true; }
if(pressed == KeyEvent.VK_T){Tpress=true; }
if(pressed == KeyEvent.VK_U){Upress=true; }
if(pressed == KeyEvent.VK_V){Vpress=true; }
if(pressed == KeyEvent.VK_W){Wpress=true; }
if(pressed == KeyEvent.VK_X){Xpress=true; }
if(pressed == KeyEvent.VK_Y){Ypress=true; }
if(pressed == KeyEvent.VK_Z){Zpress=true; }
if(pressed == KeyEvent.VK_SPACE){SPACEpress=true; }
if(pressed == KeyEvent.VK_SHIFT){SHIFTpress=true; }
if(pressed == KeyEvent.VK_ESCAPE){ESCpress=true; }
if(pressed == KeyEvent.VK_CONTROL){CTRLpress=true; }
if(pressed == KeyEvent.VK_1){num1press=true; }
if(pressed == KeyEvent.VK_2){num2press=true; }
if(pressed == KeyEvent.VK_3){num3press=true; }
if(pressed == KeyEvent.VK_4){num4press=true; }
if(pressed == KeyEvent.VK_5){num5press=true; }
if(pressed == KeyEvent.VK_6){num6press=true; }
if(pressed == KeyEvent.VK_7){num7press=true; }
if(pressed == KeyEvent.VK_8){num8press=true; }
if(pressed == KeyEvent.VK_9){num9press=true; }
if(pressed == KeyEvent.VK_0){num0press=true; }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int pressed = e.getKeyCode();
		System.out.println("got a key");
		if(pressed == KeyEvent.VK_A){Apress=false; System.out.println("got A released. Apress now: " + Apress); }
		if(pressed == KeyEvent.VK_B){Bpress=false; }
		if(pressed == KeyEvent.VK_C){Cpress=false; }
		if(pressed == KeyEvent.VK_D){Dpress=false; }
		if(pressed == KeyEvent.VK_E){Epress=false; }
		if(pressed == KeyEvent.VK_F){Fpress=false; }
		if(pressed == KeyEvent.VK_G){Gpress=false; }
		if(pressed == KeyEvent.VK_H){Hpress=false; }
		if(pressed == KeyEvent.VK_I){Ipress=false; }
		if(pressed == KeyEvent.VK_J){Jpress=false; }
		if(pressed == KeyEvent.VK_K){Kpress=false; }
		if(pressed == KeyEvent.VK_L){Lpress=false;}
		if(pressed == KeyEvent.VK_M){Mpress=false;}
		if(pressed == KeyEvent.VK_N){Npress=false;}
		if(pressed == KeyEvent.VK_O){Opress=false;}
		if(pressed == KeyEvent.VK_P){Ppress=false;}
		if(pressed == KeyEvent.VK_Q){Qpress=false;}
		if(pressed == KeyEvent.VK_R){Rpress=false;}
		if(pressed == KeyEvent.VK_S){Spress=false;}
		if(pressed == KeyEvent.VK_T){Tpress=false;}
		if(pressed == KeyEvent.VK_U){Upress=false;}
		if(pressed == KeyEvent.VK_V){Vpress=false;}
		if(pressed == KeyEvent.VK_W){Wpress=false;}
		if(pressed == KeyEvent.VK_X){Xpress=false;}
		if(pressed == KeyEvent.VK_Y){Ypress=false;}
		if(pressed == KeyEvent.VK_Z){Zpress=false;}
		if(pressed == KeyEvent.VK_SPACE){SPACEpress=false; }
		if(pressed == KeyEvent.VK_SHIFT){SHIFTpress=false;}
		if(pressed == KeyEvent.VK_ESCAPE){ESCpress=false;}
		if(pressed == KeyEvent.VK_CONTROL){CTRLpress=false; }
		if(pressed == KeyEvent.VK_1){num1press=false;}
		if(pressed == KeyEvent.VK_2){num2press=false;}
		if(pressed == KeyEvent.VK_3){num3press=false;}
		if(pressed == KeyEvent.VK_4){num4press=false;}
		if(pressed == KeyEvent.VK_5){num5press=false;}
		if(pressed == KeyEvent.VK_6){num6press=false;}
		if(pressed == KeyEvent.VK_7){num7press=false;}
		if(pressed == KeyEvent.VK_8){num8press=false;}
		if(pressed == KeyEvent.VK_9){num9press=false;}
		if(pressed == KeyEvent.VK_0){num0press=false;}
		
	}

	@Override public void keyTyped(KeyEvent arg0) {}
	@Override public void mouseMoved(MouseEvent e) {findMouse(e,false);}
	@Override public void mouseDragged(MouseEvent e) {findMouse(e,true);}
	
	public void findMouse(MouseEvent e,boolean pressed){
	mouseLoc.setLocation(e.getPoint());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	mouseDown=true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	mouseDown=false;
		
	}
}
