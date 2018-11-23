package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * class for pickups that affect the player, such as health
 * boost, speed boost, etc...
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 */

public abstract class Pickup extends GameObject{

	// instances
	protected double x;
	protected double y;
	protected ID id;
	protected String path;
	protected Image img;

	// constructor
	// used to initialize the state of the object
	public Pickup(double x, double y, ID id, String path) {
		super(x, y, id);
		this.x = x;
		this.y = y;
		this.id = id;
		this.path = path;
		try {
			this.img = ImageIO.read(new File(this.path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// methods
	// sets the image displayed by this icon
	public Image setImg(String path) {
		Image img = null;
		try {
			URL imageURL = Game.class.getResource(path);
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;
	}

	// is called every frame, allows game objects to update themselves before being rendered.
	public abstract void tick();

	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images

	// gets the bounding rectangle of this rectangle
	// returns a new rectangle, equal to the bounding rectangle for this rectangle
	public Rectangle getBounds() {
	 return new Rectangle((int) this.x, (int) this.y,30, 30);
		
	}
	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 30,30, null);
		
	}

	// get x value
	public double getX() {
		return x;
	}

	// set x value
	public void setX(double x) {
		this.x = x;
	}

	// get y value
	public double getY() {
		return y;
	}
	
	// set y value
	public void setY(double y) {
		this.y = y;
	}

	// return the following ID
	public ID getId() {
		return id;
	}

	// set the following ID
	public void setId(ID id) {
		this.id = id;
	}
	

}
