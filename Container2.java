import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * Notes (A.J. England) 4-19-18 @ 10:15pm
 * 	Updates:
 * 		 -Container2 now simply creates a Container3. crude but effective
 */



@SuppressWarnings({ "serial", "unused" })
public class Container2 extends JPanel
{

	JFrame jframe;

	public Container2(JFrame jframe, String formattedOutput, File inputFile)
	{
		/*this.jframe = jframe;

		JLabel output = new JLabel("Output: ");
		JTextPane outputField = new JTextPane();
		outputField.setText(formattedOutput);
		outputField.setEditable(false);
		outputField.setSize(400, 450);

		JScrollPane scroll = new JScrollPane(outputField);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel buttons = new JPanel();

		JButton analysis = new JButton("Analysis");
		JButton restart = new JButton("Restart");

		buttons.setLayout(new FlowLayout());
		buttons.add(analysis);
		buttons.add(restart);

		analysis.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{*/
				jframe.getContentPane().removeAll();
				JPanel container3 = new Container3(formattedOutput, inputFile);
				jframe.add(container3);
				jframe.revalidate();
				jframe.repaint();
				/*}
		});

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(output);
		this.add(scroll);
		this.add(buttons);*/

	}

}
