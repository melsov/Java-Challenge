package silly;

import java.net.InetAddress;

public class PlayerContactInfo {
	public InetAddress IPAddress;
	public int port;
	
	public PlayerContactInfo(InetAddress inet, int prt)
	{
		IPAddress = inet;
		port = prt;
	}
}
