package silly;

import static java.lang.System.out;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerHandler implements Runnable
{
	public IPoint2 otherPlayerCoord = new IPoint2(0,0);
	DatagramSocket listenerSocket = null;
	public int playerNumber = -1;
	private String serverIP = ""; // "localhost";
	
	public ServerHandler(String serverIP_)
	{
		serverIP = serverIP_;
		try {
			listenerSocket = new DatagramSocket();
			listenerSocket.setSoTimeout(4000); //2 seconds then die
		} catch (SocketException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		sayHiToServer();
		try {
			listenerSocket.setSoTimeout(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	private void sayHiToServer() 
	{
		String response = "";
		try {
			response = requestFromServer(ZeldaUDPServer.SAY_HI_REQUEST);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (response.equals("ONE"))
		{
			playerNumber = 0;
		} else if (response.equals("TWO")) {
			playerNumber = 1;
		}
		else {
			System.out.println("Say response: " + response + "\n exiting");
			System.exit(1);
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
			System.out.println("Zelda client server listener didn't hear back from the server promptly. I will die now.");
			System.exit(1234);
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
			handleClientQuery(sentence);

       }
		
	}
	
	private void handleClientQuery(String message)
	{
		System.out.println("Got msg: " + message);
		String[] msg_parts = message.split(":");
		String msg_header = msg_parts[0].trim();
		
		if (msg_header.equals(ZeldaUDPServer.OTHER_MOVED)) {
			handleOtherMoved(msg_parts);
		}
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
		otherPlayerCoord = new IPoint2(x,y);
	}
}
