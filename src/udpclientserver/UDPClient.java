package udpclientserver;


import java.io.*;
import java.net.*;

import javax.swing.JFrame;



public class UDPClient extends JFrame
{
	public UDPClient()
	{
		setTitle("UDP Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
	
   public static void main(String args[]) throws Exception
   {
      BufferedReader inFromUser =
         new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      
      System.out.println("say something to the server.");
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      
      //SEND
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9874);
      clientSocket.send(sendPacket);
      //RECEIVE
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
   }
}