import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	public Window (int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		//come back and check later
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.add(game);
		frame.setResizable(false);    // not resizable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exit on close 
		frame.setLocationRelativeTo(null);  // set the window to the middle of screen when start
		frame.setVisible(true);   // let the window be visible 
		
	}
}
