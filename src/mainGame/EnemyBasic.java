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

public class EnemyBasic extends Enemy {

	private static Image img;
	private Handler handler;

	public EnemyBasic(double x, double y, int velX, int velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		if (Math.random() > .5) {
			velX *= -1;
		}
		if (Math.random() > .5) {
			velY *= -1;
		}
		this.velX = velX;
		this.velY = velY;


	}

	public void tick() {
		if (this.y <= 0){
			this.y = 0;
			velY *= -1;
		}
		if(this.y >= Game.HEIGHT - 40){
			velY *= -1;
		}
		if (this.x <= 0){
			velX *= -1;
			this.x = 0;
		}
		if(this.x >= Game.WIDTH - 16){
			velX *= -1;
		}

		this.x += velX;
		this.y += velY;

		//handler.addObject(new Trail(x, y, ID.Trail, Color.white, 16, 16, 0.025, this.handler));
		
	}

	public void render(Graphics g) {
		//drawHitBox(g);
		Graphics2D a = (Graphics2D) g;
		a.drawImage(img, (int) x-125, (int) y-125, 250, 250, null);
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x-62, (int) this.y-30, 125, 60);//16,16
	}

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme

        // TODO: Currently this sprite won't display and we don't know why
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/spaceship2_old.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/spaceship2_old.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemyBasic");
        }
    }
}
