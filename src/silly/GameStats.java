package silly;

public class GameStats 
{
	public String playerName = "";
	public int jellyCount;
	public boolean isPossessed;
	public int playerHealth = 10;
	public Point2I coord = new Point2I(0,0);
	public boolean isVictorious = false;
	public int playerIndex = -1;
	
	public static String sep = ":";
	
	private static String StringWithInt(int i)
	{
		String result = "";
		result = String.valueOf(i);
		return result;
	}
	
	private static int IntFromString(String s)
	{
		int result = 0;
		result = Integer.parseInt(s.trim());
		return result;
	}
	
	public String toString()
	{
		return playerName + sep + 
			GameStats.StringWithInt(jellyCount) + sep + 
			String.valueOf(isPossessed) + sep +
			GameStats.StringWithInt(playerHealth) + sep + 
			coord.toString() + sep +
			String.valueOf(isVictorious) + sep +
			GameStats.StringWithInt(playerIndex);
	}
	
	public static GameStats FromString(String gss)
	{
		if (gss == null || gss.length() == 0)
			return null;
		GameStats result = new GameStats();
		String[] gs = gss.split(sep);
		for (String s : gs)
			s = s.trim();
		
		result.playerName = gs[0];
		result.jellyCount = GameStats.IntFromString(gs[1]);
		result.isPossessed = Boolean.parseBoolean(gs[2]);
		result.playerHealth = GameStats.IntFromString(gs[3]);
		int xx = GameStats.IntFromString(gs[4]);
		int yy = GameStats.IntFromString(gs[5]);
		result.coord = new Point2I(xx,yy);
		result.isVictorious = Boolean.parseBoolean(gs[6]);
		result.playerIndex = GameStats.IntFromString(gs[7]);
		return result;
	}
	
	public boolean equals(GameStats other)
	{
		return playerName.equals(other.playerName) &&
			jellyCount == other.jellyCount &&
			isPossessed == other.isPossessed &&
			playerHealth == other.playerHealth &&
			coord.equal(other.coord);
	}
	
	public static boolean ToStringTest() //check!
	{
		GameStats gs = new GameStats();
		gs.playerName = "Mumble";
		gs.playerHealth = 24;
		String gss = gs.toString();
		GameStats samegs = GameStats.FromString(gss);
		return samegs.equals(gs); // did pass
	}
}
