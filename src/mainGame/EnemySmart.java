package mainGame;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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

public class EnemySmart extends Enemy {

	private Handler handler;
	private GameObject player;
	private int speed;
	static private Image img;

    public EnemySmart(double x, double y, int speed, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.speed = speed;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}

	}

	public void tick() {
		this.x += velX;
		this.y += velY;
		////////////////////////////// pythagorean theorem
		////////////////////////////// below//////////////////////////////////////////////////
		/*
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		*/
		
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		//velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
		//velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		//handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.025, this.handler));
		
		double dir = pointDirection(new Point.Double(this.x,this.y),new Point.Double(player.x,player.y));
		
		velX = Math.cos(dir)*speed;
		velY = Math.sin(dir)*speed;

	}

	public void render(Graphics g) {
		Graphics2D a = (Graphics2D) g;
		a.drawImage(img, (int)x-75, (int) y-30, 150, 75, null);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x-75, (int) this.y-30, 150, 75);
	}
	
	public static double pointDirection(Point.Double p1, Point.Double p2)
    {
        double xDiff = p1.x - p2.x;
        double yDiff = p1.y - p2.y;
        return (Math.atan2(yDiff, xDiff));
    }

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/spaceship3blue.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/spaceship3sub.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemySmart");
        }
    }
}
