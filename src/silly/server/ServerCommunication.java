package silly.server;

import silly.GameStats;

public class ServerCommunication 
{
	public String header;
	public int playerIndex = 0;
	public GameStats gameStats;
	public static String seperatorHeader = ";";
	public static String separator = ":";
	
	public ServerCommunication(String _header_, GameStats _gameStats_, int _playerIndex_) 
	{
		header = _header_;
		gameStats = _gameStats_;
		playerIndex = _playerIndex_;
	}
	
	public String toString()
	{
		return header + seperatorHeader + String.valueOf(playerIndex) + seperatorHeader + gameStats.toString();
	}
	
	public static ServerCommunication FromString(String scs)
	{
		String[] header_and_value = scs.split(seperatorHeader);
		String hheader = header_and_value[0];
		String iindex = header_and_value[1].trim();
		System.out.println(iindex);
		int pIndex = Integer.parseInt(iindex);
		GameStats gs = GameStats.FromString(header_and_value[2].trim());
		return new ServerCommunication(hheader, gs, pIndex);
	}
}
