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

public class Container1 extends JPanel
{

	File inputFile;
	File outputFile;
	JFrame jframe;

	public Container1(JFrame jframe)
	{
		this.jframe = jframe;
		// input section
		JPanel input = new JPanel();
		FlowLayout inputLayout = new FlowLayout();
		input.setLayout(inputLayout);

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
				if (inputFile != null)	//This prevents an error if you click to select an input file then close the window without selecting one
				{
					//System.out.println(inputFile.getPath());
					fileName.setText(inputFile.getName());
				}
			}
		});

		input.add(label1);
		input.add(fileName);
		input.add(chooseFile);

		// output section
		JPanel output = new JPanel();
		FlowLayout outputLayout = new FlowLayout();
		output.setLayout(outputLayout);

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
				if (outputFile != null)	//This prevents an error if you click to select an output file then close the window without selecting one
				{
					//System.out.println(outputFile.getPath());
					outputFileName.setText(outputFile.getName());
				}
			}
		});

		output.add(label2);
		output.add(outputFileName);
		output.add(chooseOutputFile);

		// justify section
		JPanel justify = new JPanel();
		justify.setLayout(outputLayout);

		JLabel label3 = new JLabel("Justify: ");
		JRadioButton leftJustify = new JRadioButton("Left");
		JRadioButton rightJustify = new JRadioButton("Right");
		ButtonGroup bG = new ButtonGroup();
		bG.add(leftJustify);
		bG.add(rightJustify);

		justify.add(label3);
		justify.add(leftJustify);
		justify.add(rightJustify);

		// Okay button
		JButton okay = new JButton("Okay");
		okay.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// if (inputFile.length() != 0 && outputFile.length() != 0 &&
				// bG.getSelection() != null) {
				jframe.getContentPane().removeAll();
				String formattedOutput = parse(inputFile);
				String outputForAnalysis = formattedOutput;

				// writing output to file
				try
				{
					//@SuppressWarnings("resource")
					if (rightJustify.isSelected())
					{
						String[] lines = formattedOutput.split("\n");
						formattedOutput = "";
						for (int i = 0; i < lines.length; i++)
						{
							String holder = lines[i];
							String modified = "";
							for (int j = lines[i].length(); j < 80; j++)
							{
								modified += " ";
							}
							modified += holder + "\n";
							formattedOutput += modified;
						}
						formattedOutput=formattedOutput.substring(0,formattedOutput.length()-1);

					}
					PrintWriter out = new PrintWriter(outputFile);
					String newFormattedOutput = formattedOutput.replaceAll("\n", System.lineSeparator());
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

				JPanel container2 = new Container2(jframe, outputForAnalysis, inputFile);
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

	private String parse(File file)
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

				if (chars == 0 && words.get(i).length() > 80)
				{
					formattedOutput += words.get(i) + "\n";
				}
				else
				{

					chars += words.get(i).length();

					if (chars > 80)
					{

						chars -= words.get(i).length();
						holder = holder.substring(0, holder.length() - 1);
						holder += "\n";

						formattedOutput += holder;

						holder = "";
						chars = 0;
						i--;
					}
					else if (chars <= 80)
					{
						holder += words.get(i) + " ";
						chars += 1;
					}
				}
			}
			
			holder = holder.substring(0, holder.length() - 1);
			formattedOutput += holder + "\n";
			//formattedOutput += holder + "\n";
			
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
		formattedOutput = formattedOutput.substring(0, formattedOutput.length() - 1);
		return formattedOutput;

	}

} 