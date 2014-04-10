package udpclientserver;


import java.io.*;
import java.net.*;


import javax.swing.JTextArea.*;
import javax.swing.*;

import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.GroupLayout.*;

import silly.SillyPanel;

class UDPServer extends JFrame
{
	private JTextArea textArea;
	private JScrollPane jScrollPane1;
	
	public UDPServer()
	{
		setTitle("UDP Server");
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
		UDPServer udpserver= new UDPServer();
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
			System.out.println("RECEIVED: " + sentence);
			udpserver.writeToPanel("Received: " + sentence + "\n");
			
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