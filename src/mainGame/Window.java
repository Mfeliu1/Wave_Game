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

	private static final long serialVersionUID = 1L;
	private boolean mac = System.getProperty("os.name").toLowerCase().contains("mac");

	public Window(int width, int height, String title, Game game){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Set fullscreen
		if (mac) { //If user is on macOS
			try {
				com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(this, true);
				com.apple.eawt.Application.getApplication().requestToggleFullScreen(this);
			} catch (Exception e) {
				System.err.println("Failed to load apple extensions package");
			}
		} else {
			setUndecorated(true);
		}
		add(game);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.setPreferredSize(new Dimension(width, height));
		pack();
		game.start();
		setLocationRelativeTo(null);
	}
}


