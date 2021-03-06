package silly;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ProtagonistServerDelegate implements IServerRequest
{
	private String serverIP;
	DatagramSocket clientSocket;
	
	private boolean lostConnection;
	
	public ProtagonistServerDelegate(String serverIP_)
	{
		serverIP = serverIP_;
	}

	/*
	 * Abstracts away the dirty business of asking the server a question
	 * and getting a response.
	 * Sends a request string to the server
	 * and then waits for a response.
	 */
	@Override
	public String requestFromServer(String request) 
	{
		if (clientSocket == null) {
			try {
				clientSocket = new DatagramSocket();
				clientSocket.setSoTimeout(4000);
			} catch (SocketException e) {
				e.printStackTrace();
				System.out.println("AY! no client socket. I will die now");
				lostConnection = true;
			}
		}
		
		InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName(serverIP);
		} catch (UnknownHostException e1) {
			lostConnection = true;
			e1.printStackTrace();
		}
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//TODO: make this process time out after a while.
		sendData = request.getBytes();

		//SEND
		try {
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			System.out.println("Send failed. I will die now.");
			lostConnection = true;
			e.printStackTrace();
		}
		
		//RECEIVE
		String response = "";
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.receive(receivePacket);
		} catch (java.net.SocketTimeoutException e) {
			e.printStackTrace();
			System.out.println("Receive failed. yikes.");
			System.out.println("I can't recover from what happened. I will now go away.");
			lostConnection = true;
		} catch (IOException e) {
			e.printStackTrace();
			lostConnection = true;
		}
		response = new String(receivePacket.getData());
		return response.trim();
	}

	@Override
	public void close() {
		clientSocket.close();
	}

	@Override
	public boolean lostConnection() {
		return lostConnection;
	}

}
