package silly;

public class PlayerInfo 
{
	public boolean isInDemonState = false;
	public IPoint2 coord = new IPoint2(0,0);
	public PlayerContactInfo contactInfo;
	public int jellyCount = 0;
	
	public PlayerInfo(PlayerContactInfo pci)
	{
		contactInfo = pci;
	}
}
