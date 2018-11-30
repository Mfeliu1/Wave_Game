package mainGame;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import mainGame.Game.STATE;

/**
 * Handles key input from the user
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 *
 */

public class KeyInput extends KeyAdapter {

	// instances
	private Handler handler;
	private boolean[] keyDown = new boolean[5];
	private int speed;
	private Game game;
	private HUD hud;
	private Player player;
	private Upgrades upgrades;
	private String ability;
	private final ControllerManager controllers;

	// constructor
	// used to initialize the state of the object
	// uses current handler created in Game as parameter
	public KeyInput(Handler handler, Game game, HUD hud, Player player, Upgrades upgrades) {
		this.handler = handler;
		this.speed = Player.playerSpeed;
		this.game = game;
		this.player = player;
		this.hud = hud;
		this.upgrades = upgrades;
		controllers = new ControllerManager();
		controllers.initSDLGamepad();
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = false;

	}

	public enum InputAction {
	    MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT
	}
	
	Set<InputAction> actions() { //Still needs testing with controller
        ControllerState currState = controllers.getState(0);
        if (!currState.isConnected) {
            return Collections.emptySet();
        }

        Set<InputAction> actions = new HashSet<>();
        if (currState.dpadLeft) {
            actions.add(InputAction.MOVE_LEFT);
            keyDown[1] = true;
        }
        if (currState.dpadRight) {
            actions.add(InputAction.MOVE_RIGHT);
            keyDown[3] = true;
        }
        if (currState.dpadUp) {
            actions.add(InputAction.MOVE_UP);
            keyDown[0] = true;
        }
        if (currState.dpadDown) {
            actions.add(InputAction.MOVE_DOWN);
            keyDown[2] = true;
        }
        return actions;
    }
	
	// methods
	// keyevent indicates that a keystroke occurred in a component
	// invoked when a key has been pressed
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		this.speed = Player.playerSpeed;

		// finds what key strokes associate with Player
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
                
			// using only if's allows multiple keys to be triggered at once
			if (tempObject.getId() == ID.Player) {// find the player object, as he is the only one the user can control
				// key events for player 1
				// if the p key is pressed, the game would paused, if the key is pressed again, it would unpaused
				if(key == KeyEvent.VK_P && game.devMode == true){
					game.paused = !game.paused;
					AudioUtil.playClip("../gameSound/pause.wav", false);
					AudioUtil.pauseGameClip();
				}
				if(key == KeyEvent.VK_U){
					game.gameState = STATE.Upgrade;
				}
				if (key == KeyEvent.VK_ESCAPE) {
				game.gm.resetGames();
				game.gameState = STATE.Menu;
				handler.clearPlayer();
				
				}
				// if the w key is pressed, the player would move up
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
					//tempObject.setVelY(-(this.speed));
					keyDown[0] = true;
				}
				// if the a key is pressed, the player would move left
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
					//tempObject.setVelX(-(this.speed));
					keyDown[1] = true;
				}
				// if the s key is pressed, the player would move down
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
					//tempObject.setVelY(this.speed);
					keyDown[2] = true;
				}
				// if the d key is pressed, the player would move right
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					//tempObject.setVelX(this.speed);
					keyDown[3] = true;
				}
				changeDir();
				// if the spacebar key is pressed, the current level the player is currently in would skip to the next level
				if (key == KeyEvent.VK_SPACE) {
					game.gm.skip();
				}
				// if the enter key is pressed while having an ability, the ability would be used 
				if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_E) {
					ability = upgrades.getAbility();
					if (ability.equals("clearScreen")) {
						upgrades.clearScreenAbility();
					} else if (ability.equals("levelSkip")) {
						upgrades.levelSkipAbility();
					} else if (ability.equals("freezeTime")) {
						upgrades.freezeTimeAbility();
					} else ability.equals("none");
				}

			}

		}

	}
	// keyevent indicates that a keystroke occurred in a component
	// invoked when a key has been released
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode(); 

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Player) {
				// key events for player 1
				if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
					keyDown[0] = false;// tempObject.setVelY(0);
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
					keyDown[1] = false;// tempObject.setVelX(0);
				if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
					keyDown[2] = false;// tempObject.setVelY(0);
				if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
					keyDown[3] = false;// tempObject.setVelX(0);
					keyDown[4] = false;
				}

				/*
				// vertical movement
				if (!keyDown[0] && !keyDown[2])
					tempObject.setVelY(0);
				// horizontal movement
				if (!keyDown[1] && !keyDown[3])
					tempObject.setVelX(0);
				*/
				changeDir();
			}

		}
	}
	
	private void changeDir() {
		if (keyDown[0] == false && keyDown[1] == false && keyDown[2] == false && keyDown[3] == false) {player.velX = 0; player.velY = 0;}else {
			int goX = 0;
			int goY = 0;
			if (keyDown[0]) {goY--;} 
			if (keyDown[2]) {goY++;} 
			if (keyDown[1]) {goX--;} 
			if (keyDown[3]) {goX++;}
			
			double rad = Math.atan2(goY,goX);
			player.velX = Math.cos(rad)*Player.playerSpeed;
			player.velY = Math.sin(rad)*Player.playerSpeed;
			
		}
	}
	

}
