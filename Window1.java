
import javax.swing.*;

public class Window1
{
	public static void main(String[] args)
	{
		JFrame window1 = new JFrame();

		JPanel container = new Container1(window1);

		window1.setSize(500, 500);
		window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window1.add(container);
		window1.pack();
		window1.setVisible(true);
	}

}