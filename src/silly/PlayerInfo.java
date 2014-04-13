package silly;

public class PlayerInfo 
{
	public GameStats gameStats = new GameStats();
	public PlayerContactInfo contactInfo;
	public boolean ready = false;
	
	public PlayerInfo(PlayerContactInfo pci)
	{
		contactInfo = pci;
	}
}
