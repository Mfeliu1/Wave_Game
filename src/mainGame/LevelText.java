package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.sql.Time;
import java.util.Random;

/**
 * This is the text you see before each set of 10 levels
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 *
 */

public class LevelText extends GameObject {
	
	// instances
	private String text;
	private int timer;
	private Color[] color = { Color.WHITE, Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE,
			Color.PINK, Color.YELLOW };
	private Random r = new Random();
	private int index;

	// constructor
	// used to initialize the state of the object
	public LevelText(double x, double y, String text, ID id) {
		super(x, y, id);
		this.text = text;
		AffineTransform at = new AffineTransform();
		timer = 15;
	}

	@Override
	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {
	}

	@Override
	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	public void render(Graphics g) {
		timer--;
		Font font = new Font("Amoebic", 1, 125);
		g.setFont(font);
		g.setColor(color[index]);// set the new random color
		g.drawString(this.text, Game.WIDTH / 2 - getTextWidth(font, this.text) / 2, (int) this.y);
		
		// Controls color switch
		if (timer == 0) {
			index = r.nextInt(9);// get a new random color
			timer = 15;
		}

	}

	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	@Override
	// gets the bounding rectangle of this rectangle
	// returns a new rectangle, equal to the bounding rectangle for this rectangle
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
