package silly;

import java.io.*;
import static java.lang.System.out;
import java.net.*;

import javax.swing.*;

class ZeldaUDPServer extends JFrame
{
	private JTextArea textArea;
	private JScrollPane jScrollPane1;
	
	public static String MOVE_REQUEST = "CANIMOVETO"; 
	public static String WHICH_PLAYER_REQUEST = "AMIPLAYERONEORTWO";
	public static String I_LEFT_THE_GAME_REQUEST = "ILEFTTHEGAME";
	public static String OTHER_MOVED = "OTHERMOVED";
	public static String SAY_HI_REQUEST = "CLIENTLISTENERSAYSHI";
	
	private int playerCount = 0;
	private PlayerContactInfo[] playerRolodex = new PlayerContactInfo[2];
	
	public ZeldaUDPServer()
	{
		//SET UP OUTPUT WINDOW
		setTitle("Zelda UDP Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        
        textArea = new JTextArea();
        textArea.setColumns(40);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        
        jScrollPane1 = new JScrollPane(textArea);

        getContentPane().add("Center", jScrollPane1);
        pack();
        
        setVisible(true);
        setResizable(true);
        
       

	}
	
	public void writeToPanel(String ss)
	{
		textArea.insert(ss, textArea.getCaretPosition());
	}
	
	public static void main(String args[]) throws Exception
	{	
		ZeldaUDPServer zeldaserver = new ZeldaUDPServer();
		DatagramSocket serverSocket = new DatagramSocket(9874);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)
		{
			receiveData = new byte[1024]; //wipe out any old data
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());

			zeldaserver.writeToPanel("Received: " + sentence + "\n");
			
			//SEND
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String response = zeldaserver.handleClientQuery(sentence, receivePacket);
			zeldaserver.writeToPanel("Responded: to IP " + IPAddress.toString() + " port: " + port + " : " + response + "\n");
			sendData = response.getBytes();
			DatagramPacket sendPacket =
				new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
       }
	}
	
	private String handleClientQuery(String message, DatagramPacket receivePacket)
	{
		String[] msg_parts = message.split(":");
		
		String msg_header = msg_parts[0].trim();
		
		if (msg_header.equals(MOVE_REQUEST)) {
			return handleCanIMove(msg_parts);
		} else if (msg_header.equals(WHICH_PLAYER_REQUEST)) {
			return handleWhichPlayerAmI();
		}else if (msg_header.equals(I_LEFT_THE_GAME_REQUEST)) {
			handleILeftTheGame();
		}else if (msg_header.equals(SAY_HI_REQUEST)) {
			return handleClientSaysHi(receivePacket);
		}
		
		return "WHAT?";
	}
	
	private String handleClientSaysHi(DatagramPacket receivePacket)
	{
		InetAddress IPAddress = receivePacket.getAddress();
		int port = receivePacket.getPort();
		PlayerContactInfo pci = new PlayerContactInfo(IPAddress, port);
		
		String response;
		if (playerCount == 0)
		{
			response  = "ONE";
			playerRolodex[0] = pci;
		} else if (playerCount == 1) {
			response = "TWO";
			playerRolodex[1] = pci;
		} else {
			response = "NEITHER";
		}
		return response;
	}
	
	private String handleCanIMove(String[] msg_parts)
	{
		String xx = msg_parts[1];
		String yy = msg_parts[2];
		
		int x;
		int y;
		
		try {
			x = Integer.parseInt(xx.trim());
			y = Integer.parseInt(yy.trim());
		} catch(java.lang.NumberFormatException e) {
			out.println("Exception...");
			//TODO: tell other client that other player moved.
			return "YES"; 
		}
		
		if (mapOccupiedAt(x, y)) {
			return "NO";
		}
		return "YES";
	}
	
	private String handleWhichPlayerAmI()
	{
		if (playerCount == 0) {
			playerCount++;
			return "ONE";
		} else if (playerCount == 1) {
			playerCount++;
			return "TWO";
		}
		return "NEITHER";
	}
	
	private void handleILeftTheGame()
	{
		playerCount--;
	}
	
	private boolean mapOccupiedAt(int xx, int yy)
	{
		return false; //method stub
	}
	
}