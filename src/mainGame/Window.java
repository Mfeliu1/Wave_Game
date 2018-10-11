package mainGame;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/**
 * Loads the window of the game, and sets the proper dimensions
 * @author Brandon Loehle
 * 5/30/16
 */

public class Window extends JFrame{
// Draws canvas, cannot be resized due to MAC issues
	private static final long serialVersionUID = 1L;
	public Window(int width, int height, String title, Game game){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(game);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.setPreferredSize(new Dimension(width, height));
		GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		pack();
		game.start();
		setLocationRelativeTo(null);		
	}
}


