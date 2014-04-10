package udpclientserver;


import java.io.*;
import java.net.*;

import javax.swing.JFrame;

import silly.SillyPanel;

class UDPServer extends JFrame
{
	public UDPServer()
	{
		setTitle("UDP Server");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
	
	public static void main(String args[]) throws Exception
	{	
		DatagramSocket serverSocket = new DatagramSocket(9874);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)
		{
			//RECEIVE
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String( receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);
			
			//SEND
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket =
				new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
       }
  }
}