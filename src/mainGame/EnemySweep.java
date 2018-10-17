package mainGame;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemySweep extends Enemy {

	private Handler handler;
	private Color[] colors= {Color.red, Color.blue, Color.green, Color.cyan, Color.magenta, Color.orange, Color.yellow, Color.pink};
	private Random index = new Random();

	Color random = colors[index.nextInt(8)];
	
	public EnemySweep(double x, double y, double velX, double velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		if (Math.random() > .5) {
			this.velX*=-1;
		}
		if (Math.random() > .5) {
			this.velY*=-1;
		}
		
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 43) velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;
		//check for removal once bottom of screen is hit. 
		if (this.y <= 0 || this.y >= Game.HEIGHT - 43){
			handler.removeObject(this);
			return;
		}
		
		//handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, 0.025, this.handler));
		handler.addObject(new Trail(x, y, ID.Trail, random, 16, 16, 0.025, this.handler));
	}

	public void render(Graphics g) {
		g.setColor(random);
		g.fillRect((int) x, (int) y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
