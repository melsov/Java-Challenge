package silly;

public class ZeldaMap 
{
	//FINDE MEM VARS AT BOTTOM OF FILE...
	
	public ZeldaMap()
	{
		setupTiles();
		setupJellyMap();
	}
	
	private void setupTiles()
	{
		for(int i = 0; i < tiles.length; ++i)
		{
			int[] tile_column = tiles[i];
			for(int j = 0; j < tile_column.length; ++j)
			{
				if (i == HORIZONTAL_CENTER && j == VERTICAL_CENTER)
				{
					tile_column[j] = DOOR_NORTH;
				} else if (isWallAt(i,j))
				{
					tile_column[j] = WALL;
				} else {
					tile_column[j] = GROUND;
				}
			}
		}
	}
	
	private boolean isWallAt(int i, int j) 
	{
		if (i == 0 || j == 0 || i == COLUMNS - 1 || j == ROWS - 1) // ON THE BORDER
			return true;
		
		//HORSE-SHOE SHAPED WALL
		int distR = Math.abs(VERTICAL_CENTER - j);
		int distC = Math.abs(HORIZONTAL_CENTER - i);
		int distToCenter = (int) Math.sqrt(distR*distR + distC*distC);  
		if (( distToCenter== VERTICAL_CENTER - 5) && //DIST TO CENTER == SOME RADIUS AND...
				(Math.abs(i - HORIZONTAL_CENTER) > 2 || j > VERTICAL_CENTER))      //NOT IN THE '12 O'CLOCK' REGION
			return true;
		
		//WALL AROUND THE DOOR
		return (distC == 1 && j == VERTICAL_CENTER )  || (j - VERTICAL_CENTER == -1 && distC <= 1);
	}
	
	private void setupJellyMap()
	{
		jellyCount = 0;
		for(int i = 0; i < tiles.length; ++i)
		{
			int[] jelly_column = jellyMap[i];
			for(int j = 0; j < jelly_column.length; ++j)
			{
				int tile_type = tiles[i][j];
				if (tile_type == GROUND)
				{
					//jelly in a circle formation
					int distR = Math.abs(VERTICAL_CENTER - j);
					int distC = Math.abs(HORIZONTAL_CENTER - i);
					int distToCenter = (int) Math.sqrt(distR*distR + distC*distC);
					if (distToCenter == VERTICAL_CENTER - 3)
					{
						jelly_column[j] = RED_JELLY;
						jellyCount++;
					}
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
	public boolean doorAt(Point2I p) {
		return doorAt(p.x, p.y);
	}
	public boolean doorAt(int x, int y) {
		return tiles[x][y] == DOOR_NORTH;
	}
	public int jellyAt(int x, int y) {
		return jellyMap[x][y];
	}
	public void removeJelly(int x, int y) {
		setJelly(x,y,0);
		jellyCount--;
	}
	private void setJelly(int x, int y, int jelly_type) {
		jellyMap[x][y] = jelly_type;
	}
	
	public int getJellyCount() {
		return jellyCount;
	}
	
	public Point2I spawnPointForPlayer(int playerNum) {
		//TODO: acutally check which player we are.
		if (playerNum == 0)
			return spawnPlayerOnePoint;
		return spawnPlayerTwoPoint;
	}
	
	private int [][] tiles = new int[COLUMNS][ROWS];
	private int [][] jellyMap = new int[COLUMNS][ROWS];
	
	public static int COLUMNS = 30;
	public static int ROWS = 30;
	private static int VERTICAL_CENTER = ROWS/2;
	private static int HORIZONTAL_CENTER = COLUMNS/2;
	
	public static final int WALL = 431;
	public static final int GROUND = 432;
	public static final int DOOR_NORTH = 433;
	public static final int RED_JELLY = 243;
	
	private int jellyCount = 0;
	
	private Point2I spawnPlayerOnePoint = new Point2I(1,ROWS - 2);
	private Point2I spawnPlayerTwoPoint = new Point2I(COLUMNS - 2,ROWS - 2);
	
}
