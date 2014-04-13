package silly;

public interface IServerHandlerUpdate 
{
	void otherHasArrived();
	GameStats playerGameStats();
	void introduceOther(GameStats otherStats);
	void updateOtherCoord(Point2I point);
	void otherGotJellyAt(Point2I point);
	void updateOtherJellyCount(int otherJellyCount);
	
}
