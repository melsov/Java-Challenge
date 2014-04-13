package silly.server;

import java.io.*;

import static java.lang.System.out;
import java.net.*;

import javax.swing.*;

import silly.Point2I;
import silly.PlayerContactInfo;
import silly.PlayerInfo;

public class ZeldaUDPServer extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JScrollPane jScrollPane1;
	
	public static String MOVE_REQUEST = "CANIMOVETO"; 
	public static String WHICH_PLAYER_REQUEST = "AMIPLAYERONEORTWO";
	public static String I_LEFT_THE_GAME_REQUEST = "ILEFTTHEGAME";
	public static String I_GOT_JELLY = "IGOTJELLY";
	public static String SAY_HI_REQUEST = "CLIENTLISTENERSAYSHI";

	public static String OTHER_ARRIVED = "OTHERARRIVED";
	public static String OTHER_MOVED = "OTHERMOVED";
	public static String OTHER_GOT_JELLY = "OTHERGOTJELLY";
	
	private int playerCount = 0;
	private PlayerInfo[] playerRolodex = new PlayerInfo[2];
	
	private DatagramSocket serverSocket;
	
	public ZeldaUDPServer()
	{
		try {
			serverSocket  = new DatagramSocket(9874);
		} catch (SocketException e) {
			System.out.println("Ahhh...I'm dead. no datagram socket");
			e.printStackTrace();
			System.exit(1);
		}
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
		
		byte[] receiveData = new byte[1024];
		
		while(true)
		{
			receiveData = new byte[1024]; //wipe out any old data
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			zeldaserver.serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());

			zeldaserver.writeToPanel("Received: " + sentence + "\n");
			
			//SEND
//			InetAddress IPAddress = receivePacket.getAddress();
//			int port = receivePacket.getPort();
			String response = zeldaserver.handleClientQuery(sentence, receivePacket);
			zeldaserver.sendResponse(response, receivePacket.getAddress(), receivePacket.getPort());
//			zeldaserver.writeToPanel("Responded: to IP " + IPAddress.toString() + " port: " + port + " : " + response + "\n");
//			sendData = response.getBytes();
//			DatagramPacket sendPacket =
//				new DatagramPacket(sendData, sendData.length, IPAddress, port);
//			serverSocket.send(sendPacket);
			
//			if (zeldaserver.playerRolodex[0] != null)
//			{
//				//send test msg;
//				PlayerContactInfo pci = zeldaserver.playerRolodex[0];
//				sendData = "hi from server".getBytes();
//				DatagramPacket testPacket = new DatagramPacket(sendData, sendData.length, pci.IPAddress, pci.port);
//				serverSocket.send(testPacket);
//			}
       }
	}
	
	private void sendResponse(String response, PlayerContactInfo pci) {
		sendResponse(response, pci.IPAddress, pci.port);
	}
	private void sendResponse(String response, InetAddress IPAddress, int port)
	{
		byte[] sendData = new byte[1024];

		writeToPanel("Responded: to IP " + IPAddress.toString() + " port: " + port + " : " + response + "\n");
		sendData = response.getBytes();
		DatagramPacket sendPacket =
			new DatagramPacket(sendData, sendData.length, IPAddress, port);
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
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
		}else if (msg_header.equals(I_GOT_JELLY)) {
			handleClientGotJelly(msg_parts);
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
			playerRolodex[0] = new PlayerInfo(pci);
			playerCount++;
		} else if (playerCount == 1) {
			response = "TWO";
			playerRolodex[1] = new PlayerInfo(pci);
			playerCount++;
		} else {
			response = "NEITHER";
		}
		return response;
	}
	
	private String handleCanIMove(String[] msg_parts)
	{
		//TODO: replace these communications with a custom
		// serializable object
		String xx = msg_parts[1];
		String yy = msg_parts[2];
		String playerIndex_string = msg_parts[3];
		
		int x;
		int y;
		int playerIndex = -1;
		
		try {
			x = Integer.parseInt(xx.trim());
			y = Integer.parseInt(yy.trim());
			playerIndex = Integer.parseInt(playerIndex_string.trim());
		} catch(java.lang.NumberFormatException e) {
			out.println("Number format exception ...");
			
			//TODO: tell other client that other player moved.
			return "YES"; 
		}
		
		if (playerIndex == -1) {
			System.out.println("Big problem: no player index in can I move message. exiting. playerIndex string was: " + playerIndex_string);
			System.exit(1);
		}
		PlayerInfo otherPlayerInfo  = playerRolodex[playerIndex == 1 ? 0 : 1];

		if (mapOccupiedAt(x, y, otherPlayerInfo.gameStats.coord)) {
			return "NO";
		}
		PlayerInfo thisPlayerInfo = playerRolodex[playerIndex];
		thisPlayerInfo.gameStats.coord = new Point2I(x,y);
		
		//tell other client that other player moved.
		String response = ZeldaUDPServer.OTHER_MOVED + ":" + xx + ":" + yy;
		sendResponse(response, otherPlayerInfo.contactInfo);
		
		return "YES";
	}
	
	private void handleClientGotJelly(String[] msg_parts)
	{
		String xx = msg_parts[1];
		String yy = msg_parts[2];
		String playerIndex_string = msg_parts[3];
		String jjCount = msg_parts[4];

		int playerIndex = -1;
		int jellyCount = -1;
		try {
			playerIndex = Integer.parseInt(playerIndex_string.trim());
			jellyCount = Integer.parseInt(jjCount.trim());
		} catch(java.lang.NumberFormatException e) {
			out.println("Exception in parse jelly Count string ?...jjCount: " + jjCount);
		}
		
		//tell other client that other player moved.
		if (playerIndex == -1) {
			System.out.println("Big problem: no player index in can I move message. exiting. playerIndex string was: " + playerIndex_string);
			System.exit(1);
		}

		PlayerInfo thisPlayerInfo = playerRolodex[playerIndex];
		thisPlayerInfo.gameStats.jellyCount = jellyCount;
		
		//tell other player about this
		PlayerInfo otherPlayerInfo  = playerRolodex[playerIndex == 1 ? 0 : 1];
		String response = ZeldaUDPServer.OTHER_GOT_JELLY + ":" + xx + ":" + yy + ":" + thisPlayerInfo.gameStats.jellyCount;
		sendResponse(response, otherPlayerInfo.contactInfo);
		
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
	
	private boolean mapOccupiedAt(int xx, int yy, Point2I otherCoord)
	{
		return otherCoord.equal(new Point2I(xx,yy));
	}
	
}