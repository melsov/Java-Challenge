package silly;

import java.net.InetAddress;

public class PlayerContactInfo {
	InetAddress IPAddress;
	int port;
	
	public PlayerContactInfo(InetAddress inet, int prt)
	{
		IPAddress = inet;
		port = prt;
	}
}
