package silly;

import javax.swing.JFrame;

/*
 * Notes 
	for a (not very much like) Zelda (original NES) game (map)
 * Plan for the game map.
 * Link moves around a 2D map.
 * The map is made of "tiles"
 * Tiles are actually numbers in a 2D array
 * A 2D array is an array where you can look up values
 * with 2 indices. For example: "something = map[x,y];"
 * 
 * TWO CLASSES: MAP AND PAINTER
 * Split up the jobs involved with making and showing
 * the map using classes:
 * one class holds the array as a member variable
 * and its only job is to tell other classes what kind of tile
 * is at a given x and y on the map. (or what set of tiles 
 * is in a given square area)
 * Another class, Painter, will own an image. It's job will be to paint
 * the map onto the image. It will have a method
 * the takes a square section of the 2D array as a parameter.
 * The square section represents the part of the map that we want to draw.
 * 
 * OBLIVIOUSNESS IS A GOOD THING:
 * It's a good thing that the class that draws the map
 * doesn't know why its drawing stuff, it just knows how to
 * translate a bunch of numbers into pictures that it will tile together.
 * 
 * And it's a good this that the map class doesn't know how to represent itself.
 * It just keeps track of the numbers in its 2D array and dishes them out when asked.
 * It has no idea why it does this. 
 * 
 * MORE CLASSES:
 * A class to actually make the game happen: i.e. the main class. 
 * Call it MainZelda.
 * As part of the as 
 * 
 */


public class SillyFrame extends JFrame
{
	public SillyFrame()
	{
		add(new SillyPanel());
		setTitle("Silly Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SillyPanel.WIDTH_PIXELS, SillyPanel.HEIGHT_PIXELS + 40);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
	}
	
	public static void main(String[] args)
	{
		new SillyFrame();
	}	
}