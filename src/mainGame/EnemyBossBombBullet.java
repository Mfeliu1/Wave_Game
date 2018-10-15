package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * The bullets that the first boss shoots
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 *
 */

public class EnemyBossBombBullet extends Enemy {

	// instances
	private Handler handler;
	Random r = new Random();
	private int max = 15;
	private int min = -15;
	double sizeX, sizeY;

	// constructor
	// used to initialize the state of the object
	public EnemyBossBombBullet(double x, double y, ID id, Handler handler,int velX,int velY) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this. velY = velY;
		sizeX = 16;
		sizeY = 16;
	}

	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y >= Game.HEIGHT || this.y < 0 || this.x > Game.WIDTH  || this.x < 0) {
			handler.removeObject(this);}

		handler.addObject(new Trail(x-sizeX/2, y-sizeY/2, ID.Trail, Color.PINK,(int)sizeX,(int)sizeY, 0.025, this.handler));

	}
	
	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int) (x-sizeX/2), (int) (y-sizeY/2), (int)sizeX,(int)sizeY);
	}

	@Override
	// gets the bounding rectangle of this rectangle
	// returns a new rectangle, equal to the bounding rectangle for this rectangle
	public Rectangle getBounds() {
		return new Rectangle((int) (x-sizeX/2), (int) (y-sizeY/2), (int)sizeX,(int)sizeY);
	}

}
