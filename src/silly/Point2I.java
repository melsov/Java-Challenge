package silly;

public class Point2I 
{
	public int x, y;
	
	
	public Point2I(int xx, int yy)
	{
		x = xx;
		y = yy;
	}
	
	public static Point2I Add(Point2I a, Point2I b) {
		return new Point2I(a.x + b.x, a.y + b.y);
	}
	
	public boolean equal(Point2I other) {
		return this.x == other.x && this.y == other.y;
	}
	
	public String toString() {
		return String.valueOf(x) + ":" + String.valueOf(y);  
	}
	
	public static Point2I FromString(String pstring){
		if (pstring == null || pstring.length() == 0)
			return null;
		String[] xy = pstring.split(":");
		int xx = Integer.parseInt(xy[0]);
		int yy = Integer.parseInt(xy[1]);
		return new Point2I(xx,yy);
	}
	
	public Point2I plusY() {
		return new Point2I(x, y + 1);
	}
	
	public Point2I minusY() {
		return new Point2I(x, y - 1);
	}
	
	public Point2I plusX() {
		return new Point2I(x + 1, y);
	}
	
	public Point2I minusX() {
		return new Point2I(x - 1, y);
	}
}
