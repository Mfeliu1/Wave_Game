package mainGame;
import java.awt.Dimension;

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
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		pack();
		game.start();
		setLocationRelativeTo(null);		
	}
}


