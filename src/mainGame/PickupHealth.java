package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * TO BE IMPLEMENTED - adds health to the player when they move over it
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 * 
 */

public class PickupHealth extends Pickup{
	
	// instance
	private Handler handler;

	// constructor
	// used to initialize the state of the object
	public PickupHealth(double x, double y, ID id, String path, Handler handler) {
		super(x, y, id, path);
		this.handler = handler;
	}

	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {	
	}

	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 16, 16, null);
		
	}

	// gets the bounding rectangle of this rectangle
	// returns null
	public Rectangle getBounds() {
		return null;
	}

}
