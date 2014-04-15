package silly;

public class PlayerInfoPack 
{
	public GameStats clientAndServerStats;
	public GameStats justClientStats;
	
	public PlayerInfoPack(Protagonist pro, boolean isCliAndServer)
	{
		if (isCliAndServer)
		{
			clientAndServerStats = pro.myStats;
			justClientStats = pro.otherStats;
		} else {
			clientAndServerStats = pro.otherStats;
			justClientStats =  pro.myStats;
		}
	}
}
