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

public class EnemyFast extends Enemy {

	private Handler handler;
	private static Image img;

	public EnemyFast(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = 2*(Math.random()-Math.random());
		velY = -12;
	}

    public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40) {
			velY *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 16) {
			velX *= -1;
		}

		//handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, 0.025, this.handler));

	}

	public void render(Graphics g) {
		Graphics2D a = (Graphics2D) g;
		if (velY > 0) {
			a.drawImage(img, (int) x, (int) y+64,32,-64, null);
		}else {
		a.drawImage(img, (int) x, (int) y,32,64, null);}

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x+8, (int) this.y, 16, 64);
	}

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/Rocket_BossRed.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/Rocket_BossShark.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemyFast");
        }
    }
}
