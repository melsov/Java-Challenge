package silly;

public class ZeldaMap 
{
	public static int COLUMNS = 30;
	public static int ROWS = 30;
	
	private int [][] tiles = new int[COLUMNS][ROWS];
	
	public static final int GROUND = 432;
	public static final int WALL = 2;
	
	private IPoint2 spawnPlayerOnePoint = new IPoint2(5,4);
	private IPoint2 spawnPlayerTwoPoint = new IPoint2(12,15);
	
	public ZeldaMap()
	{
		setupTiles();
	}
	
	private void setupTiles()
	{
		for(int i = 0; i < tiles.length; ++i)
		{
			int[] tile_column = tiles[i];
			for(int j = 0; j < tile_column.length; ++j)
			{
				if (i == 0 || j == 0 || i == COLUMNS - 1 || j == ROWS - 1)
				{
					tile_column[j] = WALL;
				} else {
					tile_column[j] = GROUND;
				}
			}
		}
	}
	
	public boolean coordIsSolid(int x, int y)
	{
		int tile_type = tiles[x][y];
		return tile_type == WALL; //for now, wall is the only solid tile type, so this works.
	}
	
	public int tileAt(int x, int y)
	{
		return tiles[x][y];
	}
	
	public void set(int x, int y, int tile_type)
	{
		tiles[x][y] = tile_type;
	}
	
	public IPoint2 spawnPointForPlayer(int playerNum) {
		//TODO: acutally check which player we are.
		if (playerNum == 0)
			return spawnPlayerOnePoint;
		return spawnPlayerTwoPoint;
	}
}
