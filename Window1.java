import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Window1 extends JFrame implements ActionListener
{

	public Window1()
	{
		JFrame window1 = new JFrame();

		JPanel container = new Container1(window1);

		window1.setSize(500, 500);
		window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window1.add(container);
		window1.setVisible(true);

	}

	public static void main(String[] args)
	{
		new Window1();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}