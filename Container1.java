import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Notes (A.J. England) 4-19-18 @ 9:50pm:
 * 	Updates:
 * 		 -Fixed division by zero error
 * 
 * 
 * Notes (Kurgan Freedle) 4-19-18 @ 5:46pm:
 *  Updates:
 *  	 -working implementation of line length
 *  	 -working implementation of double spacing
 *  TODO:
 *  	-recurring error (division by zero) at line 257: DUE TO cases where there is only one word on a line. Fixing in a few minutes
 *  	-add analysis of blank lines added
 *  	-stop output screen from showing
 * 
 * 
 * 
 * Notes (Kurgan Freedle) 4-19-18 @ 5:26pm:
 *  Updates:
 *  	 -removed variables that held layouts for no apparent reason
 *  	 -added double spacing GUI elements
 *  	 -added line length option
 *  	 -added error handling for the following:
 *  			-no input file selected
 *  			-no output file selected
 *  			-line length input is blank, or isn't a number
 * 
 * 
 * 
 * Notes (A.J. England) 4-18-18 @ 8pm:
 *  Updates:
 *  	 -all the justify methods should be completely functional now
 *  	 -no longer a blank space after lines or newlines after last line
 *  	 -some changes to Container3.java too
 *  Issues and things to add:
 *  	 -remove the output preview page and go straight to analysis page
 * 		 -add the new lines to the analysis page
 * 		 -add double spacing option
 * 		 -add line length option
 * 		 -make the "restart" button do something? Or maybe remove it
 *  
 * Notes (A.J. England) 4-18-18 @ 2pm:
 * 	Updates:
 *  	 -fixed left/right justify (was broken after fullJustify implementation)
 *  	 -Full justify works closer to how it should
 *	Issues and things to add:
 * 		 -remove the output preview page and go straight to analysis page
 * 		 -add the new lines to the analysis page
 * 		 -full justify works for some cases, but in situations where more than one space needs to be added between words it fails
 * 		 -add double spacing option
 * 		 -add line length option
 * 		 -for right/left justify the output lines are length 81, not 80
 * 			-and full justify is 82 (81 + 1 space after last word on each line)
 * 
 */

@SuppressWarnings(
{ "serial", "unused", "hiding" })
public class Container1 extends JPanel
{
	int lineLength = 80;
	File inputFile;
	File outputFile;
	JFrame jframe;
	Boolean doubleSpaced;

	public Container1(JFrame jframe)
	{
		lineLength = 80;
		this.jframe = jframe;
		
		//Input section
		JPanel input = new JPanel();
		input.setLayout(new FlowLayout());
		JLabel label1 = new JLabel("Input: ");
		JTextField fileName = new JTextField("", 20);
		fileName.setEditable(false);
		
		JButton chooseFile = new JButton("...");
		chooseFile.addActionListener(new ActionListener()
		{
			// File inputFile;
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
				fileChooser.showOpenDialog(getParent());
				inputFile = fileChooser.getSelectedFile();
				if (inputFile != null) // This prevents an error if you click to
										// select an input file then close the
										// window without selecting one
				{
					// System.out.println(inputFile.getPath());
					fileName.setText(inputFile.getName());
				}
			}
		});

		input.add(label1);
		input.add(fileName);
		input.add(chooseFile);

		//Output section
		JPanel output = new JPanel();
		output.setLayout(new FlowLayout());

		JLabel label2 = new JLabel("Output: ");
		JTextField outputFileName = new JTextField("", 20);
		outputFileName.setEditable(false);
		JButton chooseOutputFile = new JButton("...");
		chooseOutputFile.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
				fileChooser.showOpenDialog(getParent());
				outputFile = fileChooser.getSelectedFile();
				if (outputFile != null) // This prevents an error if you click
										// to select an output file then close
										// the window without selecting one
				{
					// System.out.println(outputFile.getPath());
					outputFileName.setText(outputFile.getName());
				}
			}
		});

		output.add(label2);
		output.add(outputFileName);
		output.add(chooseOutputFile);

		// justify section
		JPanel justify = new JPanel();
		justify.setLayout(new FlowLayout());

		JLabel label3 = new JLabel("Justify: ");
		JRadioButton leftJustify = new JRadioButton("Left");
		JRadioButton fullJustify = new JRadioButton("Full");
		JRadioButton rightJustify = new JRadioButton("Right");
		ButtonGroup bG = new ButtonGroup();
		bG.add(leftJustify);
		bG.add(fullJustify);
		bG.add(rightJustify);

		justify.add(label3);
		justify.add(leftJustify);
		justify.add(rightJustify);
		justify.add(fullJustify);

		//double space section
		JPanel spacingChoice = new JPanel();
		spacingChoice.setLayout(new FlowLayout());
		
		JLabel label4 = new JLabel("Spacing: ");
		JRadioButton singleSpacing = new JRadioButton("Single");
		JRadioButton doubleSpacing = new JRadioButton("Double");
		ButtonGroup spacing = new ButtonGroup();
		spacing.add(singleSpacing);
		spacing.add(doubleSpacing);
		
		spacingChoice.add(label4);
		spacingChoice.add(singleSpacing);
		spacingChoice.add(doubleSpacing);
		
		//line length section
		JPanel lineLengthChoice = new JPanel();
		lineLengthChoice.setLayout(new FlowLayout());
		
		JLabel label5 = new JLabel("Line length: ");
		JTextField length = new JTextField("", 20);
		length.setEditable(true);
		
		lineLengthChoice.add(label5);
		lineLengthChoice.add(length);
		
		// Okay button
		JButton okay = new JButton("Okay");
		okay.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//Error checks
				//check no input file
				if (fileName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please select a valid input file.");
					return;
				}
				
				//check no output file
				if (outputFileName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please select a valid output file.");
					return;
				}
				
				//check for non-integer line length
				String lineLengthString = length.getText();
				lineLength = Integer.parseInt(lineLengthString);
				if (!lineLengthString.matches("\\d+"))
				{
					JOptionPane.showMessageDialog(null, "Please input an integer for the 'line length' option.");
					return;
				}
				//store line length
				lineLength = Integer.parseInt(lineLengthString);
				
				//stores whether the output should be double spaced or not
				boolean doubleSpaced = doubleSpacing.isSelected();
				
				jframe.getContentPane().removeAll();
				String formattedOutput = parse(inputFile, doubleSpaced);
				String outputForAnalysis = formattedOutput;
				String newFormattedOutput = "";
				// writing output to file
				try
				{
					// @SuppressWarnings("resource")
					if (!(leftJustify.isSelected()))
					{
						// array of Strings, each string is a line of the left-justified output
						String[] lines = formattedOutput.split("\n");

						// A.J. England notes:
						// new full-justify method, completely functional in my (very limited) testing
						if (fullJustify.isSelected())
						{
							formattedOutput = "";

							// for each line i
							for (int i = 0; i < lines.length; i++)
							{
								int whiteSpaceToAdd = lineLength - lines[i].length();
								// array of strings, each string is a word in the line of lines[i]
								String[] words = lines[i].split(" ");
								int whiteSpaceAdded = 0;

								// this is used when more than one extra space needs to be added between words
								// to justify correctly
								int mult = 0; // how many non-space characters are on line i
								if (words.length > 1)
								{
									for (int j = 0; j < words.length; j++)
										mult += words[j].length();
									mult = (whiteSpaceToAdd / (words.length - 1)) + 1;
									// System.out.print(whiteSpaceToAdd + " ");
								}

								whiteSpaceToAdd = whiteSpaceToAdd - ((mult - 1) * (words.length - 1));
								// System.out.println(whiteSpaceToAdd + " " + words.length + " " + mult);
								// for each word j on lines[i]
								for (int j = 0; j < words.length; j++)
								{
									formattedOutput += words[j];
									if (j + 1 < words.length)
									{
										for (int k = 0; k < mult; k++)
										{
											formattedOutput += " ";
										}
										if (whiteSpaceAdded < whiteSpaceToAdd)
										{
											formattedOutput += " ";
											whiteSpaceAdded++;
										}
									}

								}
								formattedOutput += "\n";
								if (doubleSpaced) {
									formattedOutput += "\n";
								}
							}
						}


						if (rightJustify.isSelected())
						{
							formattedOutput = "";
							for (int i = 0; i < lines.length; i++)
							{
								String holder = lines[i];
								String modified = "";
								//System.out.println(i + " " + lines[i].length());
								for (int j = lines[i].length(); j < lineLength; j++)
								{
									modified += " ";
								}
								modified += holder + "\n";
								formattedOutput += modified;
							}

						}
						if (formattedOutput.charAt(formattedOutput.length() - 1) == '\n')
							formattedOutput = formattedOutput.substring(0, formattedOutput.length() - 1);
					}
					PrintWriter out = new PrintWriter(outputFile);
					/* String */ newFormattedOutput = formattedOutput.replaceAll("\n", System.lineSeparator());
					out.write(newFormattedOutput);
					out.close();
				}
				catch (FileNotFoundException ex)
				{
					System.out.println("File not found.");
				}
				catch (IOException ex)
				{
					System.out.println("Could not write file.");
				}
				catch (NullPointerException ex)
				{

				}

				JPanel container2 = new Container2(jframe, newFormattedOutput, inputFile);
				jframe.add(container2);
				jframe.revalidate();
				jframe.repaint();
			}

		});
		// container panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(input);
		this.add(output);
		this.add(justify);
		this.add(spacingChoice);
		this.add(lineLengthChoice);
		this.add(okay);
	}

	public File getInputFile()
	{
		return inputFile;
	}

	public File getoutputFile()
	{
		return outputFile;
	}

	public JFrame getJFrame()
	{
		return jframe;
	}

	private String parse(File file, boolean doubleSpaced)
	{

		String line = null;
		String formattedOutput = "";

		try
		{
			FileReader fileReader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> words = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null)
			{

				if (line.isEmpty())
				{

				}
				else
				{

					String[] lineOfWords = line.split("\\s+");

					for (int i = 0; i < lineOfWords.length; i++)
					{
						words.add(lineOfWords[i]);
					}

					/*
					 * for (int i = 0; i < line.length(); i++) { if (line.charAt(i) != ' ') { word
					 * += line.charAt(i); } else { words.add(word); word = ""; } }
					 */
				}
			}

			int chars = 0;
			String holder = "";

			// iterate through words
			for (int i = 0; i < words.size(); i++)
			{

				if (chars == 0 && words.get(i).length() >= lineLength)
				{
					formattedOutput += words.get(i) + "\n";
					if (doubleSpaced)
						formattedOutput += "\n";
				}
				else
				{
					chars += words.get(i).length();

					if (chars >= lineLength)
					{
						if (chars > lineLength)
						{
							chars -= words.get(i).length();
							holder = holder.substring(0, holder.length() - 1);
							holder += "\n";
							if (doubleSpaced)
								holder += "\n";

							formattedOutput += holder;
							
							holder = "";
							chars = 0;
							i--;
						}
						else if (chars == lineLength)
						{
							holder += words.get(i);
							holder += "\n";
							if (doubleSpaced)
								holder += "\n";
							formattedOutput += holder;
							holder = "";
							chars = 0;
						}
						if (formattedOutput.charAt(formattedOutput.length() - 2) == ' ')
						{
							formattedOutput = formattedOutput.substring(0, formattedOutput.length() - 2) + "\n";
							if (doubleSpaced)
								holder += "\n";
						}
					}
					else if (chars < lineLength)
					{
						holder += words.get(i) + " ";
						chars += 1;
					}
				}

				// formattedOutput += holder + "\n";
			}
			formattedOutput += holder + "\n";
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file.");
		}

		catch (IOException ex)
		{
			System.out.println("Error reading file.");
		}
		catch (NullPointerException ex)
		{
			System.out.println("Error reading file.");
		}

		//System.out.println(formattedOutput);
		if (formattedOutput.charAt(formattedOutput.length() - 2) == ' ')
			formattedOutput = formattedOutput.substring(0, formattedOutput.length() - 2);
		return formattedOutput;

	}

}
