package silly.server;

import java.io.*;
import java.net.*;
import javax.swing.*;
import silly.D;
import silly.GameStats;
import silly.Point2I;
import silly.PlayerContactInfo;
import silly.PlayerInfo;

public class ZeldaUDPServer extends JFrame implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JScrollPane jScrollPane1;
	
	public static String MOVE_REQUEST = "CANIMOVETO"; 
	public static String I_LEFT_THE_GAME_REQUEST = "ILEFTTHEGAME";
	public static String I_GOT_JELLY = "IGOTJELLY";
	public static String STATE_CHANGED_REQUEST = "STATECHANGED";
	public static String SAY_HI_REQUEST = "CLIENTLISTENERSAYSHI";
	public static String INTRODUCTION_REQUEST = "PLAYERSENDSINTRODUCTION";
	public static String NOTIFY_INITIAL_JELLY_COUNT_REQUEST = "THISISTHEINITIALJELLYCOUNT";
	public static String I_WON_REQUEST = "IWONREQUEST";

	public static String OTHER_ARRIVED = "OTHERARRIVED";
	public static String OTHER_EXTENDS_INTRO = "OTHEREXTENDSINTRO";
	public static String OTHER_MOVED = "OTHERMOVED";
	public static String OTHER_GOT_JELLY = "OTHERGOTJELLY";
	public static String TIME_TO_START_THE_GAME = "STARTTHEGAME";
	
	private PlayerInfo[] playerRolodex = new PlayerInfo[2];
	
	private DatagramSocket serverSocket;
	private int initialJellyCount;
	private int capturedJellies;
	
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
		zeldaserver.runServer();
	}
	
	private void runServer()
	{
		byte[] receiveData = new byte[1024];
		
		while(true)
		{
			receiveData = new byte[1024]; //wipe out any old data
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				this.serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String sentence = new String( receivePacket.getData());

			this.writeToPanel("Received: " + sentence + "\n");
			
			//SEND
			String response = this.handleClientQuery(sentence, receivePacket);
			this.sendResponse(response, receivePacket.getAddress(), receivePacket.getPort());
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
		ServerCommunication scomm = ServerCommunication.FromString(message);
		String scomm_msg_header = scomm.header; 
		
		if (scomm_msg_header.equals(MOVE_REQUEST)) {
			return handleCanIMove(scomm);
		} else if (scomm_msg_header.equals(I_LEFT_THE_GAME_REQUEST)) {
			handleILeftTheGame(scomm);
		} else if (scomm_msg_header.equals(SAY_HI_REQUEST)) {
			handleClientSaysHi(receivePacket);
		} else if (scomm_msg_header.equals(I_GOT_JELLY)) {
			handleClientGotJelly(scomm);
		} else if (scomm_msg_header.equals(INTRODUCTION_REQUEST)) {
			handlePlayerIntroduction(scomm, receivePacket);
		} else if (scomm_msg_header.equals(STATE_CHANGED_REQUEST)) {
			passServerCommunicationToOther(scomm);
		} else if (scomm_msg_header.equals(I_WON_REQUEST)) {
			handleIWon(scomm);
		} else if (scomm_msg_header.equals(NOTIFY_INITIAL_JELLY_COUNT_REQUEST)) {
			handleInitialJellyCount(scomm);
		}
		
		return "WHAT?";
	}
	
	private void reset()
	{
		if (playerRolodex[0] != null && playerRolodex[1] != null) {
			playerRolodex = new PlayerInfo[2];
		}
		
		capturedJellies = 0;
		writeToPanel("SOMEBODY WON. OR ALL JELLIES WERE TAKEN");
	}
	
	private void handleInitialJellyCount(ServerCommunication scomm) {
		initialJellyCount = scomm.someIntValue;
	}
	
	private void handleIWon(ServerCommunication scomm) {
		PlayerInfo otherPInfo = getOtherPlayerInfoWith(scomm.playerIndex);
		if (otherPInfo == null) {
			return;
		}
		sendResponse(scomm.toString(), otherPInfo.contactInfo);
		reset();
	}
	
	private void passServerCommunicationToOther(ServerCommunication scomm) {
		PlayerInfo otherPInfo = getOtherPlayerInfoWith(scomm.playerIndex);
		sendResponse(scomm.toString(), otherPInfo.contactInfo);
	}
	
	private void handleClientSaysHi(DatagramPacket receivePacket)
	{
		InetAddress IPAddress = receivePacket.getAddress();
		int port = receivePacket.getPort();
		PlayerContactInfo pci = new PlayerContactInfo(IPAddress, port);

		if (playerRolodex[0] == null)
		{
			playerRolodex[0] = new PlayerInfo(pci);
			sendResponse("ONE", pci);
		} else if (playerRolodex[1] == null) {
			playerRolodex[1] = new PlayerInfo(pci);
			announcePlayerArrivals();
		} else {
			sendResponse("NEITHER", pci);
		}
	}
	
	private void handlePlayerIntroduction(ServerCommunication communication, DatagramPacket receivePacket)
	{
		//RECORD STATS
		PlayerInfo thisPInfo = getThisPlayerInfoWith(communication.playerIndex);
		GameStats thisGameStats = communication.gameStats;
		thisPInfo.gameStats = thisGameStats;
		thisPInfo.ready = true;
		
		PlayerInfo otherPInfo = getOtherPlayerInfoWith(communication.playerIndex);
		if (thisPInfo.ready && otherPInfo.ready){
			introducePlayersToEachOther();
		}
	}
	
	private void announcePlayerArrivals()
	{
		for(int i = 0; i < playerRolodex.length; ++i)
		{
			PlayerInfo pinfo = getThisPlayerInfoWith(i);
			sendResponse(OTHER_ARRIVED, pinfo.contactInfo);
		}
	}
	
	private void introducePlayersToEachOther()
	{
		//SEND STATS TO OTHER
		for(int i = 0; i < playerRolodex.length; ++i)
		{
			PlayerInfo thisPInfo = getThisPlayerInfoWith(i);
			PlayerInfo otherPInfo = getOtherPlayerInfoWith(i);

			if (otherPInfo.gameStats.jellyCount != 0) {
				System.exit(1);
			}
			ServerCommunication toOtherCommunication = new ServerCommunication(OTHER_EXTENDS_INTRO, thisPInfo.gameStats, getOtherIndexWith(i));
			sendResponse(toOtherCommunication.toString(), otherPInfo.contactInfo);
			
			debugRolodex();
		}
	}

	private PlayerInfo getThisPlayerInfoWith(int thisPlayerIndex) {
		return playerRolodex[thisPlayerIndex];
	}
	
	private PlayerInfo getOtherPlayerInfoWith(int thisPlayerIndex) {
		return playerRolodex[getOtherIndexWith(thisPlayerIndex)];
	}
	private int getOtherIndexWith(int thisPlayerIndex) {
		if (thisPlayerIndex > 1 || thisPlayerIndex < 0) {
			System.out.print("bad player index: " + thisPlayerIndex);
			System.exit(1);
		}
		return thisPlayerIndex == 1 ? 0 : 1; 
	}
	
	private String handleCanIMove(ServerCommunication scomm)
	{
		PlayerInfo thisPlayerInfo = getThisPlayerInfoWith(scomm.playerIndex);
		PlayerInfo otherPlayerInfo = getOtherPlayerInfoWith(scomm.playerIndex); 
		
		if (otherPlayerInfo == null || otherPlayerInfo.gameStats == null) {
			return "YES";
		}
		Point2I requestPoint = scomm.someRelevantPoint;
		if (mapOccupiedAt(requestPoint.x, requestPoint.y, otherPlayerInfo.gameStats.coord)) {
			return "NO";
		}
		thisPlayerInfo.gameStats.coord = requestPoint;
		
		//tell other client that other player moved.
		scomm.header = OTHER_MOVED;
		String response = scomm.toString();
		sendResponse(response, otherPlayerInfo.contactInfo);
		
		return "YES"; //then tell this client that they can move
	}
	
	private void handleClientGotJelly(ServerCommunication scomm)
	{
		PlayerInfo thisPlayerInfo = getThisPlayerInfoWith(scomm.playerIndex);
		PlayerInfo otherPlayerInfo = getOtherPlayerInfoWith(scomm.playerIndex);
		
		if (thisPlayerInfo == null || thisPlayerInfo.gameStats == null) {
			return;
		}
		
		if (thisPlayerInfo.gameStats.isVictorious || otherPlayerInfo.gameStats.isVictorious) {
			return;
		}
		
		int jellyCount = scomm.someIntValue;
		thisPlayerInfo.gameStats.jellyCount = jellyCount;
		
		//tell other player about this
		scomm.header = OTHER_GOT_JELLY;
		sendResponse(scomm.toString(), otherPlayerInfo.contactInfo);
		
		capturedJellies++; //NOT THE GREATEST WAY TO DO THIS...
		checkTotalJelly();
	}

	private void checkTotalJelly()
	{
		if (capturedJellies >= initialJellyCount) {
			reset();
		}
	}
	
	private void handleILeftTheGame(ServerCommunication scomm) {
		playerRolodex[scomm.gameStats.playerIndex] = null;
	}
	
	private boolean mapOccupiedAt(int xx, int yy, Point2I otherCoord) {
		return otherCoord.equal(new Point2I(xx,yy));
	}
	
	private void debugRolodex() {
		for (PlayerInfo pi : playerRolodex) {
			debugPInfo(pi);
		}
	}
	
	private void debugPInfo(PlayerInfo pi)
	{
		if (pi == null)
		{
			D.print("null pinfo");
			return;
		}
		D.print(pi.debugString());
	}

	@Override
	public void run() {
		runServer();
	}
	
}