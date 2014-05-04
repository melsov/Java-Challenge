package silly;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

//--------------------------------------------
// PARTS THAT ARE RELEVANT FOR KEY LISTENERS
// ARE SURROUNDED / MARKED WITH "//**"
//--------------------------------------------

public class KeyPressTester extends JFrame implements ActionListener, KeyListener //**//
{
	public KeyPressTester()
	{
		setTitle("Key Press Test Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
	
	public static void main(String[] args)
	{
		new KeyPressTester();
	}
	
	
	public void addNotify()
	{
		super.addNotify();
		
		//** FOR AN APPLET YOU WOULD PUT THIS IN INIT 
		addKeyListener(this);
		this.setFocusable(true); // maybe not necessary
		//** ****** //
	}
	
	//** *********** COPY FROM HERE TO... **********//
	private void doSomethingWithKeyPress(KeyEvent e)
	{
		int theHKeyCode = KeyEvent.VK_H;
		int theKeyTheyPressedCode = e.getKeyCode();
		
		if (theHKeyCode == theKeyTheyPressedCode) {
			System.out.println("they pressed h!");
		} else {
			System.out.println("they pressed something other than h!");
		}
	}
	
	//REQUIRED METHODS (although you don't have to do anything in any or all of them).
	@Override
	public void keyPressed(KeyEvent e) {
		doSomethingWithKeyPress(e);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	//** ...HERE ****************** //

	
	//NOT PART OF KEY LISTENER
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
