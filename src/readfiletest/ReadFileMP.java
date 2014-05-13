package readfiletest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ReadFileMP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFileMP rf = new ReadFileMP();
	}
	
	public ReadFileMP() {
		String f = readFile("poem.txt");
		System.out.println(f);
	}

	String readFile(String filename) {
        File f = new File(filename);
        String src = f.getAbsolutePath();
        System.out.println(src);
        try {
			System.out.println(f.getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try {
        	String text = new Scanner( new File(src) ).useDelimiter("\\A").next();
			return text;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return "";
}
	
}
