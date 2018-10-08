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

public class PickupScore extends Pickup{
	
	// instance

	// constructor
	// used to initialize the state of the object
	public PickupScore(double x, double y) {
		super(x, y, ID.PickupScore,"src/images/PickupCoin.png");
	}

	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {	
	}

	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	

}
