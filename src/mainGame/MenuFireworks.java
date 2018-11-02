package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * The graphics behind the menu that resemble fireworks
 * 
 * @author Brandon Loehle 5/30/16
 * @author Joe Passanante 11/28/17
 *
 */

public class MenuFireworks extends GameObject {

	private Handler handler;
	private Random r;
	private double x;
	private double y;
	private int sizeX;
	private int sizeY;
	private int max = 5;
	private int min = -5;
	private Color color;
	/**
	 * 
	 * @param x Position of Fire Work on X-Axis (double).
	 * @param y Position of Fire Work on Y-Axis (double).
	 * @param sizeX Size of Fire Work X-Axis.
	 * @param sizeY Size of Fire Work Y-Axis.
	 * @param velX Velocity X.
	 * @param velY Velocity Y.
	 * @param color Fill Color. 
	 * @param id entity ID.
	 * @param handler handler object.
	 */
	public MenuFireworks(double x, double y, int sizeX, int sizeY, double velX, double velY, Color color, ID id,
			Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		r = new Random();
		this.color = color;

	}
	//render the circle object
	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int) this.x-sizeX/2, (int) this.y-sizeY/2, sizeX, sizeY);
	}
	/**
	 * In the tick function it checks where the circle fire work is on
	 * the screen, and at a certain height, create smaller objects
	 */
	public void tick() {
		this.x += velX;
		this.y += velY;
		if (this.y <= 100) {// once it gets this high
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.id == ID.Firework) {// find the firework
					sparks(tempObject);// create sparks
					handler.removeObject(tempObject);// delete big circle
				}
			}
		}
	}
	/**
	 * This function is what shows the "sparks," the smaller circles
	 * @param tempObject
	 */
	public void sparks(GameObject tempObject) {
		for (int ii = 0; ii < 3; ii++) {
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -5,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -4,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -3,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -2,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), -1,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, -(r.nextInt(4) + 1), 0, this.color,
					ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 1,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 2,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 3,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 4,
					this.color, ID.FireworkSpark, handler));
			handler.addObject(new MenuFireworks(this.x, this.y, 20, 20, (r.nextInt((max - min) + 1) + min), 5,
					this.color, ID.FireworkSpark, handler)); 
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
