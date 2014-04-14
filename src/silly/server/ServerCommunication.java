package silly.server;

import silly.GameStats;
import silly.Protagonist;

public class ServerCommunication 
{
	public String header;
	public int playerIndex = 0;
	public GameStats gameStats;
	public int someIntValue = 0;
	
	public static String seperatorHeader = ";";
	public static String separator = ":";
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_) 
	{
		header = _header_;
		gameStats = _gameStats_;
		playerIndex = _playerIndex_;
	}
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_, int _utilityValue_) 
	{
		header = _header_;
		gameStats = _gameStats_;
		playerIndex = _playerIndex_;
		someIntValue  = _utilityValue_;
	}
	
	public static ServerCommunication ServerCommunicationWithHeaderAndIntValue(String _header_, Protagonist p, int the_int)
	{
		return new ServerCommunication(_header_, null, PlayerIndexForProtagonist(p), the_int);
	}
	
	public static ServerCommunication ServerCommunicationForGameStateChange(Protagonist protagonist)
	{
		return ServerCommunicationWithProtagonistAndHeader(protagonist, ZeldaUDPServer.STATE_CHANGED_REQUEST);
//		String header = ZeldaUDPServer.STATE_CHANGED_REQUEST;
//		GameStats gs = protagonist.myStats;
//		int pi = protagonist.iAmPlayerOne ? 0 : 1;
//		return new ServerCommunication(header, gs, pi);
	}
	
	public static ServerCommunication ServerCommunicationWithProtagonistAndHeader(Protagonist protagonist, String header)
	{
		GameStats gs = protagonist.myStats;
		int pi = PlayerIndexForProtagonist(protagonist); 
		return new ServerCommunication(header, gs, pi);
	}
	
	public String toString()
	{
		String gss = "";
		if (gameStats != null)
			gss = gameStats.toString();
		return header + seperatorHeader + String.valueOf(playerIndex) + seperatorHeader 
		+ String.valueOf(someIntValue) + seperatorHeader + gss; // gameStats.toString();
	}
	
	public static ServerCommunication FromString(String scs)
	{
		String[] header_and_value = scs.split(seperatorHeader);
		String hheader = header_and_value[0];
		String iindex = header_and_value[1].trim();
		int pIndex = Integer.parseInt(iindex);
		int some_val = Integer.parseInt(header_and_value[2]);
		String gss = header_and_value[3].trim();
		GameStats gs = null;
		if (gss.length() > 0)
			gs = GameStats.FromString(gss);
		ServerCommunication sc = new ServerCommunication(hheader, gs, pIndex);
		sc.someIntValue = some_val;
		return sc;
	}
	
	private static int PlayerIndexForProtagonist(Protagonist p) {
		return p.myStats.playerIndex;
	}	
}
