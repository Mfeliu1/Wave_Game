package mainGame;

import java.awt.Graphics;

/**
 * 
 * @author Joe Passanante 11/28/2017
 * 
 * Gamemanger manages all the game modes implemented in the game, and passes ticks/render calls to the corresponding modes. 
 * Current Modes Implemented: Waves.
 */
public class GameManager {
	private Game _game;
	private HUD _hud;
	public CURRENTGAME _currentGame;
	/**
	 * 
	 * @author Joe Passanante
	 * All the types of Game Modes in the game.
	 * Menu.Java access this and changes _currentGame when a button is clicked on the main menu
	 */
	public enum CURRENTGAME{
		Waves
	}
	//List all Gamemodes
	private Waves _waves;
	
	/**
	 * 
	 * @param game Game instance, this allows access to the holder values in the class. 
	 * @param hud Hub instance, this allows health and player statistics to be displayed.
	 */
	public GameManager(Game game,HUD hud){
		_game = game;
		_hud = hud;
		_currentGame = CURRENTGAME.Waves;
		_waves = new Waves(_game.player, _game.handler,_hud,_game);
	}
	/**
	 * Checks what the current gamemode is and ticks that mode. 
	 */
	public void tick(){
		if(_game.gameState == Game.STATE.Game){ //check to make sure game is running
			if(_currentGame == CURRENTGAME.Waves){
				_waves.tick();
			}
		}
	}
	/**
	 * 
	 * @param g - Graphics pass from game.java when called in game.java's handler method. 
	 */
	public void render(Graphics g){
		if(_game.gameState == Game.STATE.Game){ //check to make sure game is running
			if(_currentGame == CURRENTGAME.Waves){
				_waves.render(g);
			}
		}
	}
	/**
	 * Resets gamemodes to original states.
	 */
	public void resetGames(){
		this._waves.resetMode();
	}
	/**
	 * Skips level if gamemode supports.
	 */
	public void skip() {
		System.out.println("Skip");
		_waves.resetMode(false);
	}

}
