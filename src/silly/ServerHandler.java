package silly;

import static java.lang.System.out;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import silly.server.ServerCommunication;
import silly.server.ZeldaUDPServer;

public class ServerHandler implements Runnable
{
	DatagramSocket listenerSocket = null;
	public int playerNumber = -1;
	private String serverIP = ""; // "localhost";
	private IServerHandlerUpdate updateDelegate;
	public boolean timeToQuit = false;
	public HandlerConnectionStatus connectionStatus;
	
	
	public ServerHandler(IServerHandlerUpdate updateDelegate_, String serverIP_)
	{
		updateDelegate = updateDelegate_;
		serverIP = serverIP_;
		try {
			listenerSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		sayHiToServer();
	}
	
	private void sayHiToServer()
	{
		try {
			listenerSocket.setSoTimeout(4000); //4 seconds then die
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		doSayHiToServer();
		try {
			listenerSocket.setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	private void doSayHiToServer() 
	{
		String response = "";
		try {
			response = requestFromServer(ZeldaUDPServer.SAY_HI_REQUEST ); 
		} catch (IOException e) {
			e.printStackTrace();
			connectionStatus = HandlerConnectionStatus.NO_CONNECTION;
		}
		
		if (response.equals("ONE"))
		{
			playerNumber = 0;
			connectionStatus = HandlerConnectionStatus.ACCEPTED;
		} else if (response.equals(ZeldaUDPServer.OTHER_ARRIVED)) {
			playerNumber = 1;
			connectionStatus = HandlerConnectionStatus.ACCEPTED;
			handleOtherArrived();
		}
		else {
			if (response.equals("NEITHER"))
			{
				System.out.println("Say response: " + response + "\n too many players in other words");
				connectionStatus = HandlerConnectionStatus.REJECTED;
			}
//			System.exit(1);
		}
		System.out.println("Say response: " + response);
		
	}
	
	private String requestFromServer(String request) throws IOException
	{
		InetAddress IPAddress = InetAddress.getByName(serverIP);
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//TODO: rewrite so that this process can time out after a while.
		sendData = request.getBytes();
		  
		//SEND
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
		listenerSocket.send(sendPacket);
		//RECEIVE
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		try {
			listenerSocket.receive(receivePacket);
		} catch(java.net.SocketTimeoutException timeOutE) {
			connectionStatus = HandlerConnectionStatus.NO_CONNECTION;
		}
		String response = new String(receivePacket.getData());

		return response.trim();
	}
	
	@Override
	public void run() 
	{
		
		byte[] receiveData = new byte[1024];

		while(true)
		{
			if (timeToQuit)
				break;
			receiveData = new byte[1024]; //wipe out any old data
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				listenerSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sentence = new String( receivePacket.getData());
			handleServerMessage(sentence);

		}
		
	}
	
	private void handleServerMessage(String message)
	{
		String[] msg_parts = message.split(":");
		String msg_header = msg_parts[0].trim();
		
		String[] comm_msg_parts = message.split(ServerCommunication.seperatorHeader);
		String comm_msg_header = comm_msg_parts[0];
		
		if (msg_header.equals(ZeldaUDPServer.OTHER_MOVED)) {
			handleOtherMoved(msg_parts);
		} else if (msg_header.equals(ZeldaUDPServer.OTHER_GOT_JELLY)) {
			handleOtherGotJelly(msg_parts);
		} else if (msg_header.equals(ZeldaUDPServer.OTHER_ARRIVED)) {
			handleOtherArrived();
		} else if (comm_msg_header.equals(ZeldaUDPServer.OTHER_EXTENDS_INTRO)) {
			handleOtherExtendsIntro(message);
		} else if (comm_msg_header.equals(ZeldaUDPServer.STATE_CHANGED_REQUEST)
				|| comm_msg_header.equals(ZeldaUDPServer.I_WON_REQUEST)) {
			handleStateChanged(message);
		}
	}
	
	private void handleOtherArrived()
	{
		updateDelegate.otherHasArrived();
	}
	
	private void handleStateChanged(String message)
	{
		ServerCommunication comm = ServerCommunication.FromString(message);
		updateDelegate.pushStateChanged(comm);
	}
	
	private void handleOtherExtendsIntro(String message)
	{
		ServerCommunication comm = ServerCommunication.FromString(message);
		updateDelegate.introduceOther(comm.gameStats);
	}
	
	private void handleOtherMoved(String[] msg_parts)
	{
		String xx = msg_parts[1];
		String yy = msg_parts[2];
		
		int x = -1;
		int y = -1;
		
		try {
			x = Integer.parseInt(xx.trim());
			y = Integer.parseInt(yy.trim());
		} catch(java.lang.NumberFormatException e) {
			out.println("Exception...");
		}
//		otherPlayerCoord = new Point2I(x,y);
		updateDelegate.updateOtherCoord(new Point2I(x,y));
	}
	
	private void handleOtherGotJelly(String[] msg_parts)
	{
		String xx = msg_parts[1];
		String yy = msg_parts[2];
		String jjcount = msg_parts[3];
		
		int x = -1;
		int y = -1;
		int jcount = -1;
		
		try {
			x = Integer.parseInt(xx.trim());
			y = Integer.parseInt(yy.trim());
			jcount = Integer.parseInt(jjcount.trim());
		} catch(java.lang.NumberFormatException e) {
			out.println("Exception...");
		}

		updateDelegate.updateOtherJellyCount(jcount);
		updateDelegate.otherGotJellyAt(new Point2I(x,y));
	}
}
