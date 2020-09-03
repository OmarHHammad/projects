import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {
	
	public static void main(String args[]) throws IOException, FileNotFoundException  {
		decode(new File ("inputFiles/CaesarPlusSeven"), new File("outputFiles/CaesarPlusSeven_decoded"),new Decoder(7));
		decode(new File ("inputFiles/CaesarPlusThirteen"), new File("outputFiles/CaesarPlusThirteen_decoded"),new Decoder(13));
		decode(new File ("inputFiles/CaesarSubTwo"),new File("outputFiles/CaesarSubTwo_decoded"),new Decoder(-2));
		
		decode(new File ("inputFiles/KeywordApple"),new File("outputFiles/KeywordApple_decoded"),new Decoder("apple"));
		decode(new File ("inputFiles/KeywordSoap"),new File("outputFiles/KeywordSoap_decoded"),new Decoder("soap"));
		
	}
	
	
	/**
	 * Using a given decoder, read in lines from the inFile,
	 * translate them, and write to the outFile
	 * @param inFile file to read from
	 * @param outFile file to write to
	 * @param dec decoder to translate with
	 */
	public static void decode(File inFile, File outFile, Decoder dec) {
		String word = "";
		try {
			Scanner scr= new Scanner(inFile);
			while(scr.hasNextLine()){
				word = word + scr.nextLine();
			}
			
			PrintWriter pw = new PrintWriter(outFile);
			pw.print(dec.translateLine(word));
			pw.close();
			scr.close();
		} catch (IOException e) {

		}


		//TODO read the contents of inFile
		// translate using dec
		// write the translated message to outFile
		// make sure the messages are readable after you translate them
	}
}

	

