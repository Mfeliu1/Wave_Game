package mainGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyShooter extends Enemy {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;
	private static Image img = null;

    public EnemyShooter(double x, double y, int sizeX, int sizeY, int bulletSpeed, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = 100;
		this.sizeY = 75;
		this.timer = 60;
		this.bulletSpeed = bulletSpeed;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		//handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, this.sizeX, this.sizeY, 0.025, this.handler));
		
		timer--;
		
		
		if (timer <= 0) {
			shoot();
			
			timer = 20;
		}

	}

	public void shoot() {
		if (player != null) {
			
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
		bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

		handler.addObject(
				new EnemyShooterBullet(this.x -10, this.y-10, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler));
		}else {
			System.err.println("player is null on shooter!");//bpm
			for (int i = 0; i < handler.object.size(); i++) {
				if (handler.object.get(i).getId() == ID.Player)
					player = handler.object.get(i);
			}
		}
	}

	public void updateEnemy() {
		this.sizeX*= .95;
		this.sizeY*=.95;

		if (sizeX <= 1 || sizeY <= 1) {
			handler.removeObject(this);
		}
	}

	public void render(Graphics g) {
		Graphics2D a = (Graphics2D) g;
		a.drawImage(img, (int)this.x-this.sizeX/2, (int) this.y-sizeY/2, this.sizeX, this.sizeY, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x-this.sizeX/2, (int) this.y-sizeY/2, this.sizeX, this.sizeY);
	}

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/spaceship1yellow.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/spaceship4sub.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemyShooter");
        }
    }
}
