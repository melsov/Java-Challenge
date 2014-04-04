package trirunner;



//when you first put these in an Eclipse project
// there will be an error with a lightbulb on the side.
// Right click it, and agree to move to package...

//ASSIGNMENT 1:
/* 	THE POINT OF THIS ASSIGNMENT AND ASSIGNMENT 2 IS TO TEACH YOU HOW TO USE CLASSES, 
 *  HOW TO MAKE A CLASS THAT INHERITS FROM ANOTHER CLASS AND SEE HOW YOU COULD USE A
 *  LARGE NUMBER OF OBJECTS OF SOME KIND OF CLASS IN (IN THIS CASE) AN ARRAY.
 * 
 * 1) DRAG TriRunner, Shapester and Starster TO THE src FOLDER IN AN ECLIPSE PROJECT
 * AND OPEN ALL THREE 
 * 
 * YOU'LL PROBABLY GET AN ERROR AT THE TOP OF EACH FILE NEXT TO package trirunner
 * THIS IS BECAUSE WE ARE BEING FANCY AND DECLARING OUR OWN PACKAGE FOR THESE CLASSES
 * BUT BY DEFAULT THE FILES WENT INTO THE DEFAULT PACKAGE.
 * TO SOLVE THE PROBLEM RIGHT CLICK THE LIGHTBULB ON THE SIDE AND AGREE TO MOVE TO PACKAGE trirunner
 * trirunner SHOULD NOW APPEAR AS A PACKAGE UNDER * (default package) ON THE LEFT
 * AND THERE SHOULD BE NO MORE ERRORS. DO THE SAME FOR SHAPESTER AND STARSTER.
 * HIT PLAY AND MAKE SURE THAT YOU SEE A TRIANGLE AND STAR SLIDING DIAGONALLY IN THE APPLET
 * (then quit the applet with COMMAND + Q)
 * 
 * 2) MAKE A NEW CLASS 'DIAMONDSTER': 
 * TO START, CLICK THE trirunner PACKAGE AND CHOOSE:
 * New > Class and name it Diamondster
 * 
 * 3) MAKE Diamondster extend Shapester 
 * ( write 'extends Shapester' after public class Diamondster)
 * 
 * 4) GIVE Diamondster A MEMBER VARIABLE: Color color; (SEE STARSTER FOR AN EXAMPLE OF THIS)
 * 
 * 4.5) OVERRIDE getColor() TO MAKE Diamondster RETURN ITS MEM VAR color (SEE SHAPESTER'S getColor())
 * 
 * 5) GIVE Diamondster A CONSTRUCTOR THAT 
 * 		TAKES THREE VARIABLES double _x, double _y and Color _color AS PARAMETERS
 * 		AND SET x to _x, y to _y and color to _color in the constructor
 * 		(STARSTER HAS AN EXAMPLE OF A CONSRUCTOR THAT TAKES double _x, double _y)
 * 
 * 6) OVERRIDE THE getPoints method TO MAKE DIAMONDSTER RETURN 
 * 		A 5 POINT ARRAY FORMING A DIAMOND (I.E. SPECIFICALLY A SQUARE TURNED 45 DEGREES)
 * 		(5 points because the trirunner draws an outline and then fills it. 
 * 		the last point should be the same as the first one, 
 * 		to make a line between the start and end points)
 * 
 * 7) TEST DIAMONDSTER: MODIFY TRIRUNNER SO THAT IT HAS
 * 		A DIAMONDSTER MEMBER VARIABLE diamond1 LIKE shape1 and star1 (see comment below)
 * 
 * 8) INSIDE startDoingStuff() IN TriRunner
 * 		SET diamond1 TO BE A 'new Diamondster(40, 50, Color.purple)' // OR SOME OTHER x, y, color 
 * 		AND MAKE IT DRAW IN THE THE APPLET BY ADDING A LINE TO THE paint() METHOD
 * 		(AGAIN, FOLLOW THE PATTERN OF shape1 AND star1)
 * 
 * 9) MOVE ON TO ASSIGNMENT 2. (INSIDE 'startDoingStuff()')
 * 
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.Timer;
import javax.swing.JApplet;
import javax.swing.JFrame;

/*
 * TriRunner is a kind of JApplet
 * a JApplet is a thing that knows how to show up on screen
 * 
 * 'implements ActionListener' is needed in order to use the timer (we are animating shapes)
 * The timer needs to interact with a class that knows 'how to do the ActionListener thing'.
 * Actually all this means is that we are guaranteeing that TriRunner has a method called:
 * public void actionPerform(ActionEvent e)
 * which gets called every 5 milliseconds and allows us, in this case, to update the position of shapes.
 * By the way, ActionListener is an 'interface,' not a class, and an interface is a like a 
 * contract that a class signs. More on the what and why of interfaces later.
 * 
 */
public class TriRunner extends JApplet implements ActionListener 
{
	private static Timer timer;
	
	private Shapester shape1;
	private Starster star1;
	private Diamondster quartz;
	
	private static int BOARDWIDTH = 850;
	private static int BOARDHEIGHT = 950;
	
	//Use these for you assignment
	private static int COLUMNS = 30;
	private static int ROWS = 100;
	private Shapester[] shapes = new Shapester[COLUMNS * ROWS];
	
	
	/*
	 * Do me a favor and 
	 * just blindly accept 
	 * that we need this 
	 * main function to get 
	 * things rolling.
	 */
	public static void main(String[] args) 
	{
		JFrame f = new JFrame("Tri Runner");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
        JApplet applet = new TriRunner(); 
        f.getContentPane().add("Center", applet);
        applet.init();
        
        f.pack();
        f.setSize(new Dimension(BOARDWIDTH,BOARDHEIGHT));
        f.setVisible(true);
		
		TriRunner tr = new TriRunner();
		tr.startDoingStuff();
	}
	
	public TriRunner()
	{
	}
	
	/*
	 * Do me a favor:
	 * just accept that
	 * this function is called
	 * at the start of everything; 
	 * when the program starts.
	 */
	public void init()
	{
		startDoingStuff();
	}
	
	public void startDoingStuff()
	{
		// make new shapester and starster
		// objects. 
		shape1 = new Shapester();
		star1 = new Starster(80, 80);
	
		
		// timer's constructor. takes number of milliseconds
		// between ticks and an ActionListener as parameters.
		// 'this' is a keyword that refers to the object
		// that is being defined by this class, in other words
		// a TriRunner type object.
		timer = new Timer(5, this);
        timer.start();
       Color col = new Color(1.0f,0.3f,0.3f,0.7f);
        quartz= new Diamondster(50,50, col);
        
/*		
 * 	ASSIGNMENT 2: 
 *   	1.) COMMENT OUT timer.start() SINCE YOU WON'T BE ANIMATING FOR THIS. 
 *      2.)MAKE A NESTED FOR LOOP LIKE THE ONE BELOW (OR JUST USE THE ONE BELOW) AND
 *      USE IT TO MAKE A GRID OF DIAMOND SHAPES
 *      THAT CHANGE COLORS IN THE FOLLOWING WAY:
 *      	EVEN NUMBER ROWS ARE ALL THE SAME COLOR AND ODD ROWS ALTERNATE BETWEEN TWO COLORS 
 *        		DEPENDING ON WHETHER THE COLUMN IS ODD OR EVEN
 * 
 *      TO MAKE COLORS USE
 *        Color col = new Color(255,255,0,255); // YELLOW // ( Color(RED,GREEN,BLUE,TRANSPARENCY); 255 is max)
 *        
 *       3.) MODIFY THE PAINT METHOD TO DRAW EACH SHAPESTER IN THE ARRAY OF SHAPES
 *       DON'T USE NESTED LOOPS FOR THIS. JUST ONE FOR LOOP THAT GOES FROM i = 0 TO i < shapes.length
 *       
 *       4.) RUN THE PROGRAM. IF YOU GET A HARLEQUIN PATTERN OF DIAMONDS WITH THE RIGHT COLOR SCHEME, YOU'RE DONE.
 *       IF DIAMONDS ARE TOO FAR APART (I.E. NOT TOUCHING) OR IF ROWS OF DIAMONDS OVERLAP EACH OTHER (YOU HAVE TO SHIFT EVERY
 *       OTHER ROW TO GET THEM TO FIT) THEN TRY TO FIX YOUR PROGRAM. (HINT REGARDING SHIFTING EVERY OTHER ROW: MAY 
 *       HAVE MENTIONED THIS ELSEWHERE BUT, A HANDY WAY TO CHECK WHETHER AN INT IS EVEN OR ODD IS: num % 2 == 0 
 *       THAT TRANSLATES TO: "does num divided by two have zero as its remainder?"
 *       
 *       5.) EMAIL ME YOUR CODE. (OR BETTER YET SET UP A GITHUB ACCOUNT).
 * 
 */
        int width = 40, height = 40;
		for(int i = 0; i < COLUMNS; ++i)
		{
			for(int j = 0; j < ROWS; ++j)
			{
				//TODO: fill in code to make shapesters that 
        		//appears in the right spot in the grid
        		
				//GET RID OF ALL OF THESE LINES
				// AND REPLACE WITH SOMETHING SIMILAR
				// WHERE YOU CREATE A DIAMONDSTER INSTEAD
				Starster star = new Starster(i * width * 2,j * height * 2);
				star.color = new Color(i*30 %255, 122, j*40%155, 255);
				star.width = width;
				star.height = height;
				
				shapes[i * ROWS + j] = star; 
				//ABOUT THE ABOVE LINE:
				// TAKE A SEC TO SEE HOW WE GET THE INDEX WE WANT WITH i * ROWS + j
        		// IN OTHER WORDS: (the_column_we_are_on * total_number_of_rows + the_row_we_are_one)
			    // OR IN OTHER WORDS: (the_column_we_are_on * num_items_per_column + the_row_we_are_one)
        		// (AND THIS WORKS BECAUSE WE START COUNTING FROM ZERO.
        		//	if COLUMNS = 50 and ROWS = 10 and we're on the second column, 6th row
        		//		second column means i is 1
        		//		i * ROWS + j --> 1 * 10 + 5 --> 15 which is the 16th element in the array
			//    )
			}
		}
        
	}
	
	
	//This is a JApplet method that gets called
	//every time the JApplet's patch of screen
	//needs to be repainted.
	@Override
	public void paint(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
		g.clearRect(0, 0, BOARDWIDTH, BOARDHEIGHT);
		
		//****!comment this out for the diamonster homework
		drawAShapester(g, shape1);
		
		//TIP/FACTOID!: star1 is actually a Starster object, not a Shapester object
		//And drawAShapester asks for a Shapester type object.
		//Java is OK with this because Starster can "pass" as a Shapester since it inherits from Shapester
		
<<<<<<< HEAD
		//****!comment this out for the diamondster homework
		drawAShapester(g, star1);
=======
		//****!comment this out for the diamonster homework
		drawAShapester(g, quartz);
>>>>>>> FETCH_HEAD
		
		// FOR ASSIGNMENT PART 2 (STEP 3): MAKE A FOR LOOP HERE
		// THAT GOES THROUGH EACH DIAMONDSTER IN THE shapes ARRAY
		// AND CALLS drawAShapester() with it
		// (USE THE LENGTH PROPERTY OF THE shapes ARRAY: shapes.length TO SET THE LIMIT
		// IN THE FOR LOOP. int i = 0; i < shapes.length; ++i etc.
		
		
	}
	
	
	//AFTER ASSIGNMENT 2:
	//WE WANT TO CHANGE THE WHOLE RELATIONSHIP BETWEEN
	//TRIRUNNER AND SHAPESTER. SPECIFICALLY,
	//WE WANT TO GIVE SHAPESTER A PUBLIC METHOD
	//"DRAW" THAT WILL REPLACE THE FUNCTIONALITY
	//OF THE DRAW-A-SHAPESTER METHOD.
	//THE MOTIVATION FOR DOING THIS IS THAT THIS WAY
	//SHAPESTER CLASSES WON'T NECESSARILY HAVE TO USE POINTS 
	// (THE GETPOINTS METHOD WILL NO LONGER BE A 
	//	PUBLIC METHOD)
	// THEY COULD USE AN IMAGE INSTEAD.
	// THE 'DRAW' METHOD WILL TAKE A Graphics2D OBJECT AS A PARAMETER
	//
	private void drawAShapester(Graphics2D g, Shapester the_shapester)
	{
		g.setPaint(the_shapester.getColor());
		
		Point2D.Double[] points = the_shapester.getPoints();
		Point2D.Double firstPoint = points[0];
		GeneralPath line = new GeneralPath(GeneralPath.WIND_EVEN_ODD, points.length );
		
		// "move to" the first point (without drawing a line)
		line.moveTo(firstPoint.x, firstPoint.y);
		
		for (int i = 1; i < points.length ; ++i)
		{
			Point2D.Double nextPoint = points[i];
			// "draw a line to" this point from the previous point
			line.lineTo(nextPoint.x, nextPoint.y);
		}
		
		g.fill(line); //fills in
//		g.draw(line); //draws an outline
	}

	/*
	 * Gets called by the timer every n milliseconds
	 */
	@Override
	public void actionPerformed(ActionEvent the_event) 
	{
	
		shape1.moveALittle();
		
		star1.moveALittle();

		quartz.moveALittle();
		repaint(); //causes the 'paint' method to be called again.
		
		
	}

}
