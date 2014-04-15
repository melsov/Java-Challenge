package silly;

public interface IServerRequest 
{
	String requestFromServer(String request);
	void close();
	boolean lostConnection();
}
