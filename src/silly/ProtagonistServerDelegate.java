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
	
	public ProtagonistServerDelegate(String serverIP_)
	{
		serverIP = serverIP_;
	}

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
				System.exit(1);
			}
		}
		
		InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName(serverIP);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//TODO: make this process time out after a while.
		sendData = request.getBytes();

		//SEND
		String response = "";
		try {
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			System.out.println("Send failed");
			e.printStackTrace();
		}
		
		//RECEIVE
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			clientSocket.receive(receivePacket);
		} catch (java.net.SocketTimeoutException toE) {
			System.out.println("Receive failed. yikes.");
			toE.printStackTrace();
			
//			//EXPERIMENTAL??
//			try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("I can't recover from what happened. I will now go away.");
			System.exit(1);
//			return requestFromServer(request); /* just try again */
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		response = new String(receivePacket.getData());
		return response.trim();
	}

	@Override
	public void close() {
		clientSocket.close();
	}

}
