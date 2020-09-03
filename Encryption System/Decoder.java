import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * A class that is used to decode messages using a key.
 * A key is a mapping from letter to letter.
 * In a valid key, every letter appears once, so that the process can be reversed.
 */

public class Decoder {
	char[] key = new char[26];
	
	/**
	 * Constructor for the Decoder
	 * Sets the initial key to the default key
	 * [a,b,c, ... x,y,z]
	 */
	public Decoder() {
		for(int i = 0; i < 26; i++)
		{
			key[i] = toLetter(i);
		}
	}
	
	/**
	 * Contructor for the Decoder
	 * Set the initial key by offsetting the default key by i
	 * @param caesarNum
	 */
	public Decoder(int caesarNum) {
		this();
		char temp[] = new char[26];
		for(int i = 0; i < 26; i++)
		{
			int k = (i-caesarNum);
			while(k < 0)
			{
				k = k + 26;
			}
			while( k > 25)
			{
				k = k - 26;
			}
			temp[i] = key[k];
		}
		for(int i = 0; i < 26; i++)
		{
			key[i] = temp[i];
		}
		
		
	}
	
	/**
	 * Contructor for the Decoder
	 * Uses a keyword. See the handout for details.
	 * @param keyword to be used
	 */
	public Decoder(String keyword) {
		this();
		char temp[] = new char[26];
		keyword = keyword.toLowerCase();
		int counter = 0;
		for(int i = 0; i < keyword.length() ; i++) // 
		{
			if(contains(keyword.charAt(i), key) == true){ // skips over any non abc value
				if(contains(keyword.charAt(i), temp) == false) // skips over any value already in array
				{
					temp[counter] = keyword.charAt(i);	
					counter++;
				}else {
					keyword = keyword.substring(0, i) + keyword.substring(i + 1,keyword.length());
					i--;
				}
			}
		}
		
		
		for(int i = counter; i < 26; i++)
		{
			for(int k = 0; k < 26; k++)
			{
				if(contains(toLetter(k), temp) == false)
				{
					temp[i] = toLetter(k);
					k++;
					break;
				}
			}
		}
		for(int i = 0; i < 26; i++)
		{
			key[i] = temp[i];
		}
		
	}
	
	
	/**
	 * Read one line from the given file and take the first 26 characters for the key
	 * Throw an IllegalArgumentException if this does not result in a valid key
	 * A valid key should have all the letters from a to z
	 * @param key
	 * @throws IllegalArgumentException if the file or its contents aren't valid
	 */
	public Decoder(File key)  {
		this();
			String fileLine = "";
		char temp[] = new char[26];
		try {
			Scanner scr= new Scanner(key);
			while(scr.hasNextLine()){
				fileLine = scr.nextLine(); // sets varibale to the next and only line
			}
			int abcCounter = 0;
			for(int i = 0; i < fileLine.length(); i++) // adds values of string to array
			{
				if(contains(fileLine.charAt(i), temp) == false) // if value is not already in array
				{
					temp[abcCounter] = fileLine.charAt(i);
					++abcCounter;
				}
				
			}
			if(abcCounter != 26) // if this is 26, then all the letters got placed in array at least once
			{
				if(key.length() == 0) // if the file was empty
				{
					scr.close();
					throw new IllegalArgumentException("File was blank");
				}else {
					scr.close();
					throw new IllegalArgumentException("Invalid key");
				}
				
			}
			for(int i = 0; i < 26; i++)
			{
				this.key[i] = temp[i];
			}
			scr.close();
			 
			} catch (FileNotFoundException e) { // if we cant find the file
				throw new IllegalArgumentException("File cannot be located");
				
			}

		
	}
	
	/**
	 * return a copy of the key
	 * @return a copy of the key
	 */
	public char[] getKey() {
		return Arrays.copyOf(key, 26);
	}
	
	@Override
	public String toString() {
		String output = "Key looks like: \n";
		for(int i = 0; i<26; i++) {
			output = output + (toLetter(i)) +"->" + key[i] + "\n";
		}
		return output;
	}
	// the char in array is what the string should turn into. ex:
	// index 0 is a. whatever is the value at 0 is what a should turn into
	// input aba, key {c, h, j}, output chc
	
	
	/**
	 * Take the line and translate it according to the current key
	 * @param lineIn the line to be translated
	 * @return the translated line
	 * @throws IllegalArgumentException if lineIn is null
	 */
	public String translateLine(String lineIn) {
		String output = "";
		if(lineIn == null)
		{
			throw new IllegalArgumentException("there is no value to translate");
		}
		lineIn = lineIn.toLowerCase();
		List<Character> messages = Arrays.asList(' ', '!' ,'?' ,'.' ,','); // punctuation list to ignore
		for(int i = 0; i < lineIn.length(); i++)
		{
			if(messages.contains(lineIn.charAt(i))){// if it sees a char that is punctuation, go to next interation
				output = output + lineIn.charAt(i);
			}else {
				output = output + key[toIndex(lineIn.charAt(i))]; 
			}		
		}
		return output;
	}
	
	/**
	 * Method that stores the key in a file
	 * @param f the file to store the key in
	 */
	public void saveKey(File f) {
		String writeString = "";
		for(char value: key)
		{
			writeString = writeString + value;
		}
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.print(writeString);
			pw.close();
		} catch (FileNotFoundException e) {
		
	}
}
	
	//some helper methods you can use
	
	/**
	 * Method that removes duplicate characters from a string.
	 * Also removes all characters that are not lowercase alphabetical.
	 * It retains only the first occurrence of each char value.
	 * @param word the string to remove duplicates from
	 * @return the new string with duplicates removed
	 */
	private static String cleanString(String word) { 
		final StringBuilder output = new StringBuilder();   
		for (int i = 0; i < word.length(); i++) { 
			String character = word.substring(i, i + 1); 
			if (output.indexOf(character) < 0) 
				if(isLetter(character.charAt(0)))
					output.append(character); 
		}
		return output.toString(); 
	}
	
	private static char toLetter(int index) {
		return (char)(index + 'a');
	}
	
	private static int toIndex(char letter) {
		return letter - 'a';
	}
	
	private static boolean isLetter(char c) {
		return c >= 'a' && c < 'a' + 26;
	} 
	
	public boolean contains(char c, char[] array) {
	    for (char x : array) {
	        if (x == c) {
	            return true;
	        }
	    }
	    return false;
	}
}


