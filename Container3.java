import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class Container3 extends JPanel
{
	public Container3(String output, File file)
	{
		String line = null;
		String formattedOutput = "";
		int wordsProcessed = 0;
		int numberOfLines = 0;
		int blankLinesRemoved = 0;
		double wordsPerLine = 0;
		double lineLength = 0;

		try
		{
			FileReader fileReader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> words = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null)
			{

				if (line.isEmpty() || line.matches("^\\s*$"))
				{
					blankLinesRemoved++;
				}
				else
				{

					String[] lineOfWords = line.split(" +");

					for (int i = 0; i < lineOfWords.length; i++)
					{
						words.add(lineOfWords[i]);
					}
				}
			}

			wordsProcessed = words.size();
			String[] lines = output.split("\r\n|\r|\n");
			numberOfLines = lines.length;

			wordsPerLine = wordsProcessed / numberOfLines;

			String output2 = output.trim();
			lineLength = output2.length() / numberOfLines;

			JLabel wordsProcessedLabel = new JLabel("Words Processed: " + wordsProcessed);
			JLabel numberOfLinesLabel = new JLabel("Number of Lines: " + numberOfLines);
			JLabel blankLinesRemovedLabel = new JLabel("Blank Lines Removed: " + blankLinesRemoved);
			JLabel wordsPerLineLabel = new JLabel("Average Words Per Line: " + wordsPerLine);
			JLabel lineLengthLabel = new JLabel("Average Line Length: " + lineLength + " characters");

			JButton outputB = new JButton("Output");
			JButton restartB = new JButton("Restart");
			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout());
			buttons.add(outputB);
			buttons.add(restartB);

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(wordsProcessedLabel);
			this.add(numberOfLinesLabel);
			this.add(blankLinesRemovedLabel);
			this.add(wordsPerLineLabel);
			this.add(lineLengthLabel);
			this.add(buttons);

		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file.");
		}

		catch (IOException ex)
		{
			System.out.println("Error reading file.");
		}

	}
}