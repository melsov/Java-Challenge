package textfiles;

import java.io.IOException;

public class FileData {
	public static void main(String[ ] args) throws IOException {
//		String file_name = "//Library//Application Support//Harry's Game";
//		String file_name = "//Harry's Game";
//		String file_name = "/Library/Application Support/Harry's Game/HarrysGame.rtf";
//		String file_name = "//Library/Application Support//Harry's Game//HarrysGame.rtf";
		// String file_name = "/Library/Application Support/Harry's Game/HarrysGame.txt";
		String file_name = "text/HarrysGame.txt";
	try{
		ReadFile file = new ReadFile( file_name );
		String[ ] aryLines = file.OpenFile( );
		for (int i=0; i < aryLines.length; i++ ) {
			System.out.println( aryLines[ i ] ) ;
		}
	}catch(IOException e){
		System.out.println( e.getMessage() );	
	}
	}
}
