package silly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUIPainter 
{
	private static Point2I GuiOrigin = new Point2I(0, SillyPanel.HEIGHT_PIXELS);
	private static Point2I GuiDimensions = new Point2I(SillyPanel.WIDTH_PIXELS, SillyPanel.GUI_FOOTER_HEIGHT);
	private static final int LINE_HEIGHT_PIXELS = 20;
	
	public static void PaintGameInfo(Graphics cg, GameStats playerStats, GameStats otherStats)
	{
		Graphics2D g = (Graphics2D )cg;
		
		g.setColor(Color.BLUE);
		clearGuiArea(g);
		
		String playerJelly = String.valueOf(playerStats.jellyCount);
		String otherJelly = String.valueOf(otherStats.jellyCount);
		char[] p1 = (playerStats.playerName + ": JELLY: " + playerJelly).toCharArray();
		char[] p2 = (otherStats.playerName + ": JELLY: " + otherJelly).toCharArray();
		
		int lineStartHeight = 15;
		g.setColor(Color.WHITE);
		g.drawChars(p1, 0, p1.length, GuiOrigin.x + 10, GuiOrigin.y + lineStartHeight);
		g.drawChars(p2, 0, p2.length, GuiOrigin.x + GuiDimensions.x / 2 + 10, GuiOrigin.y + lineStartHeight);
		
	}
	
	private static int incrementLine(int lineHeight) {
		return lineHeight + LINE_HEIGHT_PIXELS;
	}
	
	private static void clearGuiArea(Graphics2D g)
	{
		g.fillRect(GuiOrigin.x, GuiOrigin.y, GuiDimensions.x, GuiDimensions.y);
	}
	
	public static void PaintPausedScreen(Graphics cg, String text)
	{
		Graphics2D g = (Graphics2D )cg;
		g.setColor(Color.RED);
		
		char[] pausedchars = ("TIME OUT").toCharArray();
		g.drawChars(pausedchars, 0, pausedchars.length, SillyPanel.WIDTH_PIXELS/4 + 10, SillyPanel.HEIGHT_PIXELS/2  - 30);
	
	}
}
