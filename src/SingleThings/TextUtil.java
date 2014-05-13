package SingleThings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;




public class TextUtil {
	public String file_name;
	private ArrayList<String> fileList = new ArrayList<String>();
	
	public TextUtil(String FilePath)
	{
		file_name = FilePath;
	}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
private void updateArray(){
	fileList.clear();
	for(int i=0;i<getLength();i++){
		fileList.add(getAccurateLine(i));
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+	
private String getAccurateLine(int line){
	try{
		return GetFileLine(line);
	}catch(IOException e){
		return "Error in getAccurateLine method of TextUtil";
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
public String getLine(int line){
		try{
			if(line > fileList.size()){
				return GetFileLine(line);
			}else{
				return GetFileLine(line-1);
			}
		}catch(IOException e){
			return "Error in getLine method of TextUtil";
		}
	}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
private String GetFileLine(int line) throws IOException {
	FileReader fr = new FileReader(file_name);
	BufferedReader textReader = new BufferedReader(fr);
	
	int numberOfLines = getLength( );
	String[ ] textData = new String[numberOfLines];
	
	for (int i=0; i < numberOfLines; i++) {
		textData[ i ] = textReader.readLine();
	}
	textReader.close( );
	if(line < numberOfLines){
		return textData[line];
	}else{
		System.out.println("There is no line: " + line + " in the document.");
		return "Error (no such line): The requested line does not exist.";
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
public int getLength(){
	try{	
		FileReader file_to_read = new FileReader(file_name);
		BufferedReader bf = new BufferedReader(file_to_read);
		@SuppressWarnings("unused")
		String aLine;
		int numberOfLines = 0;
		while((aLine=bf.readLine())!=null){
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;
	}catch(IOException e) {
		System.out.println("Error in getlength of TextUtil.");
		return (Integer) 0;
	}
}

//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
public void debugPrintFile(){
	try{	
		FileReader file_to_read = new FileReader(file_name);
		BufferedReader bf = new BufferedReader(file_to_read);
		@SuppressWarnings("unused")
		String aLine;
		int numberOfLines = 0;
		while((aLine=bf.readLine())!=null){
			numberOfLines++;
			System.out.print(numberOfLines + " :");
			if (aLine.equals("") || aLine == null) System.out.println("<empty line>");
			else System.out.println(aLine);
		}
		bf.close();
	} catch(IOException e) {
		System.out.println("Error in getlength of TextUtil.");
	}
}
	
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
public void writeLastLine( String text, boolean newline ) throws IOException 
{
	FileWriter write = new FileWriter( file_name , true);
	PrintWriter print_line = new PrintWriter( write );
	if(newline){
		print_line.printf( "%s" + "%n" , text);
	}else{
		print_line.printf( "%s", text);
	}
	print_line.close();

}


//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+		
private void eraseFile( ) throws IOException {
	FileWriter write = new FileWriter( file_name , false);
//	PrintWriter print_line = new PrintWriter( write );
//	FileWriter write2 = new FileWriter( file_name , true);
//	PrintWriter print_line2 = new PrintWriter( write2 );
//	print_line.printf( "%s", " ");
//	print_line2.printf( "%s"+ "%n", " ");
//	print_line.close();
//	print_line2.close();
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+	
private void writeToLine( int line, String text) throws IOException{
	try
	{
		updateArray();
		eraseFile();
		while(fileList.size()<line + 1){
			fileList.add("");
		}
		
		fileList.set(line , text);
		
		for(int i=0;i<fileList.size();i++){
			writeLastLine(fileList.get(i),true);	
		}
	} catch(IOException e) {
		System.out.println("Error in writeToLine in TextUtil: " + e.getMessage());	
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+	
public void writeLine(int line, String text){
	try{
		writeToLine(line-1,text);	
	}catch(IOException e){
		System.out.println("Error in writeLine in TextUtil");
	}
}
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
//+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
/*
 * EXCELLENT: a public method that just provides a connection to a private method.
 */
public void erase(){
	try{
		eraseFile();
	}catch(IOException e){
		System.out.println("Erase failed. Error in errase in TextUtil.");
	}
}

}
