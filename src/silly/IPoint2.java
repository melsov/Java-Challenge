package silly;

public class IPoint2 
{
	int x, y;
	
	public IPoint2(int xx, int yy)
	{
		x = xx;
		y = yy;
	}
	
	public static IPoint2 Add(IPoint2 a, IPoint2 b) {
		return new IPoint2(a.x + b.x, a.y + b.y);
	}
}
