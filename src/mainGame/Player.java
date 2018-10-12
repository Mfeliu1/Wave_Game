package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;


import mainGame.Game.STATE;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	protected int playerWidth, playerHeight;
	public static int playerSpeed = 10;

	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 2;
		//Player Width and Height change the size of the image, use the same number for both 
		//so the image scales properly
		playerWidth = 32;
		playerHeight = 32;
		

	}// end of player initializer

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;
		x = Game.clamp(x, 0, Game.canvasSize.getWidth()  - 38);
		y = Game.clamp(y, 0, Game.canvasSize.getHeight() - 60);
		/**
		 * Literallyyv JUST REMOVE THE TRRAAIIILLLL to make the player image to show up
		 * - Katie Rosell, 10/25
		 */
		//The color of the player?
		// add the trail that follows it
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, playerWidth, playerHeight, 0.05, this.handler));
		//The thing above is the trail code that needs to be deleted. 
		collision();
		checkIfDead();
	}
	
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return image;
	}

	public void checkIfDead() {
		if (hud.health <= 0) {// player is dead, game over!
			if (hud.getExtraLives() == 0) {
				game.gm.resetGames();
		//Save the player's score if it is a higher score than the high score
				try{
						File set = new File("src/HighScores.txt");
						BufferedWriter out = new BufferedWriter(new FileWriter(set));
						out.write(Integer.toString(HUD.thisHighScore()));
						out.close();				
					} //end try
					catch (IOException e) {
						System.out.println(e);
						System.exit(1);
					} //end catch
				game.gameState = STATE.GameOver;
			}
 
			else if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {
		try{
		hud.updateScoreColor(Color.white);
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (Enemy.class.isInstance(tempObject)){// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					AudioUtil.playClip("../gameSound/explosion.wav", false);
					hud.health -= damage;
					hud.updateScoreColor(Color.red);
				}
			}
			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will get hurt once the
				// boss starts moving
				if (this.y <= 138 && tempObject.isMoving) {
					hud.health -= 2;
					hud.updateScoreColor(Color.red);
				}
			}
			
			if (Pickup.class.isInstance(tempObject)) {
			if (tempObject.getId() == ID.PickupHealth && (getBounds().intersects(tempObject.getBounds()))) {
				hud.health = 100;
				handler.removeObject(tempObject);
			}
			
			if (tempObject.getId() == ID.PickupSize && (getBounds().intersects(tempObject.getBounds()))) {
				if (playerWidth>3) {
				playerWidth/=1.2;
				playerHeight/=1.2;}else {
					hud.setScore(hud.getScore()+1000);
				}
				handler.removeObject(tempObject);
			}
			
			if (tempObject.getId() == ID.PickupLife && (getBounds().intersects(tempObject.getBounds()))) {
				hud.setExtraLives(hud.getExtraLives()+1);
				handler.removeObject(tempObject);
			}
			
			if (tempObject.getId() == ID.PickupScore && (getBounds().intersects(tempObject.getBounds()))) {
				hud.setScore(hud.getScore()+1000);
				handler.removeObject(tempObject);
			}
			
			if (tempObject.getId() == ID.PickupFreeze && (getBounds().intersects(tempObject.getBounds()))) {
				handler.timer = 300;
				handler.removeObject(tempObject);
			}
			}
		}
		}catch(NullPointerException e){
			System.err.println("ahh looks like you done removed an object while checking the object");
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, playerWidth, playerHeight);
		//g.drawImage(img, (int) this.x, (int) this.y, playerWidth, playerHeight, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, playerWidth,playerHeight);//was this,this,60,60 -bpm
	}
	//how mucvh damage has the player taken?
	public void setDamage(int damage) {
		this.damage = damage;
	}
	//
	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}

	public int getPlayerWidth() {
		return playerWidth;
	}
	public int getPlayerHeight(){
		return this.playerHeight;
	}
	
}
