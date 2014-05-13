package SingleThings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class Keys implements KeyListener, MouseMotionListener, MouseListener{
//MOUSE
public static Point2D mouseLoc = new Point2D.Float();
public static boolean mouseDown;
//KEYS
//Letters
public static boolean Apress;
public static boolean Bpress;
public static boolean Cpress;
public static boolean Dpress;
public static boolean Epress;
public static boolean Fpress;
public static boolean Gpress;
public static boolean Hpress;
public static boolean Ipress;
public static boolean Jpress;
public static boolean Kpress;
public static boolean Lpress;
public static boolean Mpress;
public static boolean Npress;
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
//Numbers
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
//MISC
public static boolean SPACEpress;
public static boolean SHIFTpress;
public static boolean ESCpress;
public static boolean CTRLpress;
public static boolean UPpress;
public static boolean DOWNpress;
public static boolean LEFTpress;
public static boolean RIGHTpress;

	@Override public void keyPressed(KeyEvent e) {setKeyVars(e,true);}
	@Override public void keyReleased(KeyEvent e) {setKeyVars(e,false);}
	@Override public void keyTyped(KeyEvent arg0) {}
	@Override public void mouseMoved(MouseEvent e) {findMouse(e);}
	@Override public void mouseDragged(MouseEvent e) {findMouse(e);}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {mouseDown=true;}
	@Override public void mouseReleased(MouseEvent e) {mouseDown=false;}
	
	public void findMouse(MouseEvent e){mouseLoc.setLocation(e.getPoint());}
	
	public void setKeyVars(KeyEvent e,boolean downOrUp){
		int pressed = e.getKeyCode();
		if(pressed == KeyEvent.VK_A){Apress=downOrUp; }
		if(pressed == KeyEvent.VK_B){Bpress=downOrUp; }
		if(pressed == KeyEvent.VK_C){Cpress=downOrUp; }
		if(pressed == KeyEvent.VK_D){Dpress=downOrUp; }
		if(pressed == KeyEvent.VK_E){Epress=downOrUp; }
		if(pressed == KeyEvent.VK_F){Fpress=downOrUp; }
		if(pressed == KeyEvent.VK_G){Gpress=downOrUp; }
		if(pressed == KeyEvent.VK_H){Hpress=downOrUp; }
		if(pressed == KeyEvent.VK_I){Ipress=downOrUp; }
		if(pressed == KeyEvent.VK_J){Jpress=downOrUp; }
		if(pressed == KeyEvent.VK_K){Kpress=downOrUp; }
		if(pressed == KeyEvent.VK_L){Lpress=downOrUp; }
		if(pressed == KeyEvent.VK_M){Mpress=downOrUp; }
		if(pressed == KeyEvent.VK_N){Npress=downOrUp; }
		if(pressed == KeyEvent.VK_O){Opress=downOrUp; }
		if(pressed == KeyEvent.VK_P){Ppress=downOrUp; }
		if(pressed == KeyEvent.VK_Q){Qpress=downOrUp; }
		if(pressed == KeyEvent.VK_R){Rpress=downOrUp; }
		if(pressed == KeyEvent.VK_S){Spress=downOrUp; }
		if(pressed == KeyEvent.VK_T){Tpress=downOrUp; }
		if(pressed == KeyEvent.VK_U){Upress=downOrUp; }
		if(pressed == KeyEvent.VK_V){Vpress=downOrUp; }
		if(pressed == KeyEvent.VK_W){Wpress=downOrUp; }
		if(pressed == KeyEvent.VK_X){Xpress=downOrUp; }
		if(pressed == KeyEvent.VK_Y){Ypress=downOrUp; }
		if(pressed == KeyEvent.VK_Z){Zpress=downOrUp; }
		if(pressed == KeyEvent.VK_SPACE){SPACEpress=downOrUp; }
		if(pressed == KeyEvent.VK_SHIFT){SHIFTpress=downOrUp; }
		if(pressed == KeyEvent.VK_ESCAPE){ESCpress=downOrUp; }
		if(pressed == KeyEvent.VK_CONTROL){CTRLpress=downOrUp; }
		if(pressed == KeyEvent.VK_1){num1press=downOrUp; }
		if(pressed == KeyEvent.VK_2){num2press=downOrUp; }
		if(pressed == KeyEvent.VK_3){num3press=downOrUp; }
		if(pressed == KeyEvent.VK_4){num4press=downOrUp; }
		if(pressed == KeyEvent.VK_5){num5press=downOrUp; }
		if(pressed == KeyEvent.VK_6){num6press=downOrUp; }
		if(pressed == KeyEvent.VK_7){num7press=downOrUp; }
		if(pressed == KeyEvent.VK_8){num8press=downOrUp; }
		if(pressed == KeyEvent.VK_9){num9press=downOrUp; }
		if(pressed == KeyEvent.VK_0){num0press=downOrUp; }
		if(pressed == KeyEvent.VK_UP){UPpress=downOrUp; }	
		if(pressed == KeyEvent.VK_DOWN){DOWNpress=downOrUp; }	
		if(pressed == KeyEvent.VK_LEFT){LEFTpress=downOrUp; }	
		if(pressed == KeyEvent.VK_RIGHT){RIGHTpress=downOrUp; }	
	}
	

}
