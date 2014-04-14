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
	
	public String debugString()
	{
		String result = "";
		if (gameStats == null) {
			result += ":GameStats are null:";
		} else {
			result += gameStats.debugString() + "\n";
		}
		result += "Ready: " + String.valueOf(ready) + "\n";
		result += contactInfo.toString();
		return result;
	}
}
