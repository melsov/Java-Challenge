package silly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUIPainter 
{
	public static void paintGameInfo(Graphics cg, int playerJellyCount, int otherJellyCount)
	{
		Graphics2D g = (Graphics2D )cg;
		String playerJelly = String.valueOf(playerJellyCount);
		String otherJelly = String.valueOf(otherJellyCount);
		char[] p1 = ("P1 JELLY: " + playerJelly).toCharArray();
		char[] p2 = ("P2 JELLY: " + otherJelly).toCharArray();
		g.setColor(Color.BLUE);
		g.drawChars(p1, 0, p1.length, 40, SillyPanel.HEIGHT_PIXELS );
		g.drawChars(p2, 0, p2.length, SillyPanel.WIDTH_PIXELS / 2, SillyPanel.HEIGHT_PIXELS);
		
	}
}
