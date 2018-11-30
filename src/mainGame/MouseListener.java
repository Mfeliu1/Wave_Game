package mainGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mainGame.Game.STATE;

/**
 * Handles all mouse input
 * 
 * @author Brandon Loehle 5/30/16
 *
 */
/**
 * @author Katie Rosell - DESCRIPTION
 * 
 * The mouse listener class is responsible for handling where the 
 * player clicks the screen. 
 *
 */

public class MouseListener extends MouseAdapter {
	//Initialization, what is needed in here
	private Game game;
	private Handler handler;
	private HUD hud;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private String upgradeText;
	//Constructors for the class (blue prints for what composes the class)
	public MouseListener(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player,
	Upgrades upgrades) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
	}
	/** MOUSE PRESSED
	 * @param A mouse click!
	 * @return Nothing returned
	 * This function uses the click from a mouse and 
	 */
	public void mousePressed(MouseEvent e) {
		double scaleFactor = Game.windowSize.getWidth()/Game.canvasSize.getWidth(); 
		int mx = (int)(((double)e.getX())/scaleFactor);
		int my = (int)(((double)e.getY())/scaleFactor);
		if (game.gameState == STATE.GameOver) {
			handler.object.clear();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			game.gameState = STATE.Menu;
		}
		else if (game.gameState == STATE.Game) {
			//nothing needs to be done for the game state since
			//mouse clicks are not how the player plays 
		}
		else if (game.gameState == STATE.Upgrade) {
			//the player can select an upgrade specific to the (x,y) coordinates
			if (mouseOver(mx, my, 100, 300, 1721, 174)) {
				upgradeText = upgradeScreen.getPath(1);
				upgrades.activateUpgrade(upgradeText);
				//upgradeScreen.removeUpgradeOption(1);//remove that upgrade option since it was chosen
				upgradeScreen.resetIndexes();
				game.gameState = STATE.Game;
				game.paused = false;
			} else if (mouseOver(mx, my, 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(2);
				upgrades.activateUpgrade(upgradeText);
				//upgradeScreen.removeUpgradeOption(2);//remove that upgrade option since it was chosen
				upgradeScreen.resetIndexes();
				game.gameState = STATE.Game;
				game.paused = false;
			} else if (mouseOver(mx, my, 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(3);
				upgrades.activateUpgrade(upgradeText);
				//upgradeScreen.removeUpgradeOption(3);//remove that upgrade option since it was chosen
				upgradeScreen.resetIndexes();
				game.gameState = STATE.Game;
				game.paused = false;
			}
		}
		else if (game.gameState == STATE.Menu) {
			// Waves Button
			if (mouseOver(mx, my, 700, 300, 470, 250)) {
				handler.object.clear();
				game.gameState = STATE.Game;
				handler.addObject(player);
			}
			// Help Button
			else if (mouseOver(mx, my, 230, 360, 260, 200)) {
				game.gameState = STATE.Help;  //go to the help menu
			}
			// Quit Button
			else if (mouseOver(mx, my, 1390, 360, 260, 200)) {
				System.exit(1);
			}
            // Space Theme Button
            else if (mouseOver(mx, my, 400, 730, 350, 120)) {
                game.toggleMenuMusic();
                handler.setTheme(Themes.Space);
            }
            // Underwater Theme Button
            else if (mouseOver(mx, my, 850, 730, 650, 120)) {
                game.toggleMenuMusic();
                handler.setTheme(Themes.Underwater);
            }
		}
		// Back Button for Help screen
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 850, 300, 200, 64)) {
				game.gameState = STATE.Menu;
				return;
			}
		}
	}

	/** MOUSE OVER 
	 * Helper method to detect is the mouse is over a "button" drawn via Graphics
	 * @param mx
	 * 	mouse x position
	 * @param my
	 * 	mouse y position
	 * @param x
	 * 	button x position
	 * @param y
	 * 	button y position
	 * @param width
	 * 	button width
	 * @param height
	 * 	button height
	 * @return boolean, true if the mouse is contained within the button
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
}
