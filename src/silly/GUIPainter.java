package silly;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUIPainter 
{
	private static Point2I GuiOrigin = new Point2I(0, SillyPanel.HEIGHT_PIXELS);
	private static Point2I GuiDimensions = new Point2I(SillyPanel.WIDTH_PIXELS, SillyPanel.GUI_FOOTER_HEIGHT);
	private static final int LINE_HEIGHT_PIXELS = 20;
	
	private static final int LARGE_FONT_SIZE = 18;
	private static final int SMALL_FONT_SIZE = 14;
	
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
		SetFontSize(g, SMALL_FONT_SIZE);
		g.setColor(Color.WHITE);
		g.drawChars(p1, 0, p1.length, GuiOrigin.x + 10, GuiOrigin.y + lineStartHeight);
		g.drawChars(p2, 0, p2.length, GuiOrigin.x + GuiDimensions.x / 2 + 10, GuiOrigin.y + lineStartHeight);
		
		lineStartHeight = incrementLine(lineStartHeight);
		
		p1 = lineTwoWithStats(playerStats); // (playerStats.isPossessed ? "POSSESSED" : "NORMAL").toCharArray();
		p2 = lineTwoWithStats(otherStats); //(otherStats.isPossessed ? "POSSESSED" : "NORMAL").toCharArray();
		g.drawChars(p1, 0, p1.length, GuiOrigin.x + 10, GuiOrigin.y + lineStartHeight);
		g.drawChars(p2, 0, p2.length, GuiOrigin.x + GuiDimensions.x / 2 + 10, GuiOrigin.y + lineStartHeight);
		
		lineStartHeight = incrementLine(lineStartHeight);
		
		p1 = (playerStats.isVictorious ? "WON" : "").toCharArray();
		p2 = (otherStats.isVictorious ? "WON" : "").toCharArray();
		g.drawChars(p1, 0, p1.length, GuiOrigin.x + 10, GuiOrigin.y + lineStartHeight);
		g.drawChars(p2, 0, p2.length, GuiOrigin.x + GuiDimensions.x / 2 + 10, GuiOrigin.y + lineStartHeight);
		
		
	}
	
	private static char[] lineTwoWithStats(GameStats stats) {
		String pnum = stats.playerIndex == 0 ? "P1" : "P2";
		String possd = (stats.isPossessed ? "POSSESSED" : "NORMAL");
		return (pnum + " : " + possd).toCharArray();
	}
	
	private static int incrementLine(int lineHeight) {
		return lineHeight + LINE_HEIGHT_PIXELS;
	}
	
	private static void clearGuiArea(Graphics2D g)
	{
		g.fillRect(GuiOrigin.x, GuiOrigin.y, GuiDimensions.x, GuiDimensions.y);
	}
	
	public static void PaintGameStateScreen(Graphics cg, String text)
	{
		Graphics2D g = (Graphics2D )cg;
		int midtop = SillyPanel.HEIGHT_PIXELS/2  - 30;
		
		g.setColor(new Color(12, 122, 255, 255));
		g.fillRect(0, midtop, SillyPanel.WIDTH_PIXELS, 100 );
		g.setColor(Color.RED);
		char[] textchars = (text).toCharArray();
		
		SetFontSize(g, LARGE_FONT_SIZE);
		g.drawChars(textchars, 0, textchars.length, SillyPanel.WIDTH_PIXELS/16 + 10, midtop + 40);
	
	}
	
	public static void PaintResultsForBoth(Graphics cg, GameStats playerStats, GameStats otherStats)
	{
		Graphics2D g = (Graphics2D )cg;
		int midtop = SillyPanel.HEIGHT_PIXELS/2  - 30;
		
		boolean pOneWon = playerOneWon(playerStats);
		if (!pOneWon) {
			pOneWon = playerOneWon(otherStats);
		}
		char[] p1 = (playerStats.playerName + " " + WonLostStringWith(playerStats) ).toCharArray();
		char[] p2 = (otherStats.playerName + " " + WonLostStringWith(otherStats) + ". PRESS 'R' TO RESTART").toCharArray();
		
		int lineStartHeight = 15;
		g.setColor(new Color(12, 122, 255, 255));
		g.fillRect(0, midtop, SillyPanel.WIDTH_PIXELS, 100 );
		SetFontSize(g, LARGE_FONT_SIZE);
		g.setColor(ColorForPlayerOneWon(pOneWon));
		g.drawChars(p1, 0, p1.length, SillyPanel.WIDTH_PIXELS/16 + 10, midtop + 40 + lineStartHeight);
		lineStartHeight = incrementLine(lineStartHeight);
		g.drawChars(p2, 0, p2.length, SillyPanel.WIDTH_PIXELS/16 + 10, midtop + 40 + + lineStartHeight);
		
		
	}
	
	private static boolean playerOneWon(GameStats stats) {
		return stats.playerIndex == 0 && stats.isVictorious == true;
	}
	
	private static Color ColorForPlayerOneWon(boolean pOneWon) {
		if (pOneWon) {
			return new Color(60, 255, 170, 255);
		}
		return new Color(255, 122, 170, 255);
	}
	
	private static String WonLostStringWith(GameStats stats) {
		return stats.isVictorious ? "WON" : "LOST";
	}
	
	private static void SetFontSize(Graphics2D g, int size) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, size));
	}
}
