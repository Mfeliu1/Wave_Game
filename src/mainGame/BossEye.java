package mainGame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

/**
 * The last boss in the game, shown in a 3x3 grid of 9 instances of BossEye
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class BossEye extends GameObject {

	private Image img;
	private Random r = new Random();;
	private float alpha = 0;
	private double life = 0.005;
	private int tempCounter = 0;
	private int timer;
	private int spawnOrder = 1;// make them begin moving from left to right, top to bottom
	private int placement;// where they are in the 3x3 grid of eyes
	private double speed;
	private double[] speedTypes = { -5, -6, -7, -8, -9 };
	private GameObject player;
	private Handler handler;

	public BossEye(double x, double y, ID id, Handler handler, int placement) {
		super(x, y, id);
		this.img = getImage("/images/bosseye.png");
		this.velX = 0;
		this.velY = 0;
		this.speed = speedTypes[r.nextInt(4)];
		this.handler = handler;
		this.placement = placement;
		this.timer = 400;
	}

	public void tick() {
		if (tempCounter == 0) {
			if (alpha < 0.995) {// this handles each eye fading in to the game
				alpha += life + 0.001;
			} else {
				tempCounter++;
				for (int i = 0; i < handler.object.size(); i++) {
					if (handler.object.get(i).getId() == ID.Player)
						this.player = handler.object.get(i);
				}
			}
		} else if (tempCounter == 1) {
			spawn();
			if (this.placement == 1 && this.spawnOrder >= 1) {
				attackPlayer();
			} else if (this.placement == 2 && this.spawnOrder >= 2) {
				attackPlayer();
			} else if (this.placement == 3 && this.spawnOrder >= 3) {
				attackPlayer();
			} else if (this.placement == 4 && this.spawnOrder >= 4) {
				attackPlayer();
			} else if (this.placement == 5 && this.spawnOrder >= 5) {
				attackPlayer();
			} else if (this.placement == 6 && this.spawnOrder >= 6) {
				attackPlayer();
			} else if (this.placement == 7 && this.spawnOrder >= 7) {
				attackPlayer();
			} else if (this.placement == 8 && this.spawnOrder >= 8) {
				attackPlayer();
			} else if (this.placement == 9 && this.spawnOrder >= 9) {
				attackPlayer();
			} else {
				this.health = 0;
			}
		}

	}

	public void spawn() {
		timer--;
		if (timer == 0) {
			this.spawnOrder++;
			timer = 200;
		}
	}

	public void attackPlayer() {
		if (player != null) {
			double diffY = (this.y - player.getY());
			double diffX = (this.x - player.getX());
			double distance = Math.sqrt(((this.x - this.player.getX()) * (this.x - this.player.getX()))
					+ ((this.y - this.player.getY()) * (this.y - this.player.getY())));
			this.velX = (this.speed / distance) * diffX;
			this.velY = (this.speed / distance) * diffY;
			this.x += this.velX;
			this.y += this.velY;
		}
	}

	public void render(Graphics g) {
		if (g.getColor() == Color.BLACK) {// prevent black text from showing "Game Over" if the player dies here, or
											// "Winner!" if the player survives
			g.setColor(Color.GREEN);
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(img, (int) this.x, (int) this.y, null);
		g2d.setComposite(makeTransparent(1));
	}

	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));

	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, (int) this.img.getWidth(null), (int) this.img.getHeight(null));
	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;

	}

}
