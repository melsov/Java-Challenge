package NewTry;
import java.io.*;
import java.util.ArrayList;

public class TextUtil 
{
    public String path;
    public String[] lines;
    public TextUtil(String pathy,int length){
        path=pathy;
        lines = new String[length];
//        for(int i = 0;i<length;i++){
//        	String gotString = getLine(i+1);
//            lines[i]=gotString;
//        }
        populateLinesArrayFromFile();
        
        for (String s : lines ) {
        	System.out.print("*"); System.out.println(s);
        }
    }
    
    //more efficient
    private void populateLinesArrayFromFile()
    {
    	BufferedReader in;
        int linenum = 0;
        String read;
        try {
            in = new BufferedReader(new FileReader(path));

            do {
                read = in.readLine();
                lines[linenum] = read;
                linenum++;
            } while (read != null && linenum < lines.length);
            in.close();
           
        } catch(java.io.FileNotFoundException e) {
        	clear(); // clear will create the file if it doesn't exist (directory must exist however)
        	
        } catch(IOException e) {
            System.out.println("There was a problem:" + e);
        }
    }

    public void clear(){
    	try{
            new FileWriter(path , false); // false = 'don't append' == clobber the file
    	}catch(IOException e){
    		System.out.println("There was a problem oh noesss " + e);
    	}
    }
   
    // INCOMPLETE AND POSSIBLY INCOHERENT RANT:
    // This method and the public mem var "lines"
    // are stepping on each other toes! Meaning...
    // Someone using the TextUtil class (probably also you but never mind that)
    // could use either "jane.lines[5]" or "jane.getLine(5)" to get the String at line 5.
    // You might say/think, "This is not a big deal, so I'm not that concerned." It is a big deal. Be concerned!
    // Why: you want very much for your classes to be "black boxes:" stuff goes in, other stuff comes out
    // and someone on the outside of the black box doesn't have to care how it worksÑ-they only have to care
    // that it DOES work.
    // The user of the class (its 'client'), in fact, doesn't want to know how it works and your classes
    // want to help them not know by limiting the ways in which outsiders can use it: i.e. which properties
    // and methods are public.
    // to know which methods/properties to make public you have to decide
    // what TextUtil's purpose in life is...
    // it seems like its purpose is to be a 'stand-in' for a file on the local system.
    // so an outsider should be able to treat it like a file and not have to care
    // that its really using an array to hold the lines of the file.
    // so long story short, the lines array should be private
    // and, more importantly, getLine should read from the array.
    // OR...don't use a "lines" array at all and just read from the file each time
    // someone needs something...
    // to be continued...
    public String getLine(int line){
        BufferedReader in;
        String read = null;
        int linenum = line;
        try {
            in = new BufferedReader(new FileReader(path));

            while(linenum > 0){
                read = in.readLine();
                linenum--;
            }
            in.close();
           return read;
        } catch(java.io.FileNotFoundException e) {
        	clear(); // clear will create the file if it doesn't exist (directory must exist however)
        	return null;
        } catch(IOException e) {
            System.out.println("There was a problem:" + e);
            return "error";
        }
    }

    public void write(String text) {
        BufferedWriter out;
    	try {
            out = new BufferedWriter(new FileWriter(path,true));
            out.write(text);
            out.newLine();
            out.close();
        }catch(IOException e){
            System.out.println("There was a problem:" + e);
        }
    }

    public void update(){
        clear();
        for(int i=0;i<lines.length;i++){
            if(lines[i]!=null){
                write(lines[i]);
            } else {
                write("");
            }
        }
    }

}


