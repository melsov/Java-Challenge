package silly.server;

import silly.GameStats;
import silly.Point2I;
import silly.Protagonist;

public class ServerCommunication 
{
	public String header;
	public int playerIndex = 0;
	public GameStats gameStats;
	public int someIntValue = 0;
	public Point2I someRelevantPoint;
	
	public static String seperatorHeader = ";";
	public static String separator = ":";
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_) {
		this(_header_, _gameStats_, _playerIndex_, 0);
	}
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_, int _utilityValue_) 
	{
		this(_header_, _gameStats_, _playerIndex_, _utilityValue_, null);
	}
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_, int _utilityValue_, Point2I _point_) 
	{
		header = _header_;
		gameStats = _gameStats_;
		playerIndex = _playerIndex_;
		someIntValue  = _utilityValue_;
		someRelevantPoint = _point_;
	}
	
	public static ServerCommunication ServerCommunicationWithHeaderAndIntValue(String _header_, Protagonist p, int the_int)
	{
		return new ServerCommunication(_header_, null, PlayerIndexForProtagonist(p), the_int);
	}
	
	public static ServerCommunication ServerCommunicationWithHeaderAndPointValue(String _header_, Protagonist p, Point2I some_point)
	{
		return new ServerCommunication(_header_, null, PlayerIndexForProtagonist(p), 0, some_point);
	}
	
	public static ServerCommunication ServerCommunicationWithHeaderPointAndIntValues(String _header_, Protagonist p, int some_int, Point2I some_point)
	{
		return new ServerCommunication(_header_, null, PlayerIndexForProtagonist(p), some_int, some_point);
	}
	
	public static ServerCommunication ServerCommunicationForGotJelly(Protagonist p)
	{
		return new ServerCommunication(ZeldaUDPServer.I_GOT_JELLY, null, PlayerIndexForProtagonist(p), p.myStats.jellyCount, p.myStats.coord);
	}
	
	public static ServerCommunication ServerCommunicationWithHeader(String _header_)
	{
		return new ServerCommunication(_header_, null, 0);
	}
	
	public static ServerCommunication ServerCommunicationForGameStateChange(Protagonist protagonist)
	{
		return ServerCommunicationWithProtagonistAndHeader(protagonist, ZeldaUDPServer.STATE_CHANGED_REQUEST);
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
		
		String somePointAsString = "";
		if (someRelevantPoint != null)
			somePointAsString = someRelevantPoint.toString();
		
		return header + seperatorHeader + 
		String.valueOf(playerIndex) + seperatorHeader + 
		String.valueOf(someIntValue) + seperatorHeader + 
		gss + seperatorHeader +
		somePointAsString; 
	}
	
	public static ServerCommunication FromString(String scs)
	{
		String[] header_and_value = scs.split(seperatorHeader);
		String hheader = header_and_value[0].trim();

		int pIndex = Integer.parseInt(header_and_value[1].trim());
		int some_val = Integer.parseInt(header_and_value[2].trim());

		GameStats gs = GameStats.FromString(header_and_value[3].trim());
		Point2I relevantPoint = null;
		if (header_and_value.length > 4)
			relevantPoint = Point2I.FromString(header_and_value[4].trim());
		
		return new ServerCommunication(hheader, gs, pIndex, some_val, relevantPoint );
	}
	
	private static int PlayerIndexForProtagonist(Protagonist p) {
		return p.myStats.playerIndex;
	}	
}
