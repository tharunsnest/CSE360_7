import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Parser
{
	public String parse(File file)
	{
		String line = null;
		String formattedOutput = null;

		/*
		 * try { FileReader fileReader = new FileReader(file);
		 * 
		 * @SuppressWarnings("resource") BufferedReader bufferedReader = new
		 * BufferedReader(fileReader); ArrayList<String> words = new
		 * ArrayList<String>(); while((line = bufferedReader.readLine()) != null) {
		 * String word = ""; for (int i = 0; i < line.length(); i++) { if
		 * (line.charAt(i) != ' ') { word += line.charAt(i); } else { words.add(word);
		 * word = ""; } }
		 * 
		 * } } catch (FileNotFoundException ex) {
		 * System.out.println("Unable to open file."); }
		 * 
		 * catch(IOException ex) { System.out.println("Error reading file."); }
		 */

		formattedOutput = "hello";
		return formattedOutput;

	}

}