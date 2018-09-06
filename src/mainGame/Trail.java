package mainGame;

import java.awt.AlphaComposite;

/**
 * Adds a fading trail behind the player (purely esthetic)
 * @author Brandon Loehle
 * 5/30/16
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The trail that follows the player and some of the enemies
 * @author Brandon Loehle
 * 5/30/16
 *
 */

public class Trail extends GameObject{

	private float alpha = 1;
	private Handler handler;
	private Color color;
	private int width, height;
	private double life;	//life = 0.01 -> 0.1
	
	
	public Trail(double x, double y, ID id, Color color, int width, int height, double life, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.color = color;
		this.width = width;
		this.height = height;
		this.life = life;

	}


	public void tick() {//slowly fades each square
		if (alpha > life){
			alpha = (float) (alpha - (life - 0.001));
		}
		else{
			handler.removeObject(this);
		}
	}


	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(makeTransparent(alpha));
		g.setColor(color);
		g.fillRect((int)this.x, (int)this.y, this.width, this.height);
		g2d.setComposite(makeTransparent(1));//allows for the rectangle to appear like it's fading
	}
	
	/**
	 * Helps make the rectangle fade away
	 * @param alpha is the amount of fade
	 * @return the AlphaComposite instance of this alpha level (simply how Swing allows you to use opacity in objects)
	 */
	private AlphaComposite makeTransparent(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
		
	}


	public Rectangle getBounds() {
		return null;
	}

}
