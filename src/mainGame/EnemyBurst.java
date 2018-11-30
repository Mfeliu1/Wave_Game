package mainGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBurst extends Enemy {

	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();
	private static Image img;

	public EnemyBurst(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX*2;
		this.velY = velY*2;
		this.timer = 60;
		this.side = side;
		this.size = size;

		if (this.side.equals("left")) {
			handler.object.add(new EnemyBurstWarning(0, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		} else if (this.side.equals("right")) {
			handler.object
					.add(new EnemyBurstWarning(Game.WIDTH - 25, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();

		} else if (this.side.equals("top")) {
			handler.object.add(new EnemyBurstWarning(0, 0, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();

		} else if (this.side.equals("bottom")) {
			handler.object
					.add(new EnemyBurstWarning(0, Game.HEIGHT - 25, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();

		}

	}

	public void tick() {
		//check for removal
		if (this.y <= -Game.HEIGHT || this.y >= Game.HEIGHT*2){ handler.removeObject(this); return;}
		if (this.x <= -Game.WIDTH || this.x >= Game.WIDTH*2){ handler.removeObject(this); return;}

		//handler.addObject(new Trail(x, y, ID.Trail, Color.orange, this.size, this.size, 0.025, this.handler));

		timer--;
		if (timer <= 0) {
			this.x += velX;
			this.y += velY;

		}
		
	}

	public void setPos() {
		if (this.side.equals("left")) {
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
		} else if (this.side.equals("right")) {
			this.x = Game.WIDTH + 200;
			this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;

		} else if (this.side.equals("top")) {
			this.y = -(size);
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;

		} else if (this.side.equals("bottom")) {
			this.y = Game.HEIGHT + 200;
			;
			this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;

		}
	}

	public void setVel() {
		if (this.side.equals("left")) {
			this.velY = 0;
		} else if (this.side.equals("right")) {
			this.velX = -(this.velX);
			this.velY = 0;

		} else if (this.side.equals("top")) {
			this.velX = 0;

		} else if (this.side.equals("bottom")) {
			this.velX = 0;
			this.velY = -(this.velY);
		}
	}

	public void render(Graphics g) {
		Graphics2D a = (Graphics2D) g;
		a.drawImage(img, (int) x, (int) y, 150, 150, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 150,150);
	}

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/asteroid.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/pufferfish.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemyBurst");
        }
    }
}
