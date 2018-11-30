package mainGame;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * The first boss in the game
 * 
 * @author Brandon Loehle 5/30/16
 * @author David Nguyen 12/13/17
 *
 */

public class EnemyBoss extends Enemy {

	// instances
	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private static Image img;
	private int spawn;
	private int difficulty = 1;
	private int bombTimer = 120;
	private HUD hud;
	
	// constructor
	// used to initialize the state of the object
	public EnemyBoss(ID id, Handler handler, int diff, HUD h) {
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		hud = h;
		velX = 0;
		velY = 2;
		this.health = 1000; //full health is 1000
		difficulty = diff;

	}
	
	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {
		this.x += velX;
		this.y += velY;

		if (timer <= 0)
			velY = 0;
		else
			timer--;
		drawFirstBullet();
		if (timer <= 0)
			timer2--;
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			this.isMoving = true;
			spawn = r.nextInt(5);
			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBullet, handler));
				this.health -= 3;
			}
		}
		//prevents the alien boss from spawning bombs at the earlier levels
		if (difficulty > 0) {
			//if the timer is less than 0, trigger the bombs
			bombTimer--;
			if (bombTimer < 0) {
				//resets the bomb timer
				bombTimer = 120;
				//calls calls the EnemyBossBomb class
				handler.addObject(
						new EnemyBossBomb((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBomb, handler,difficulty > 1 ? ( difficulty > 2 ? 16 : 8 ) : 4));
			}
		}
		
		
		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 96) {
			velX *= -1;
			handler.addObject(new EnemyBossBullet((int) this.x, (int) this.y + 80, ID.EnemyBossBullet, handler));
			handler.addObject(new EnemyBossBullet((int) this.x + 96, (int) this.y + 80, ID.EnemyBossBullet, handler));
		}

		// handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.025,
		// this.handler));
		hud.levelProgress = (1000-this.health)/(10);
		if (this.health <= 0) {
			System.out.println("Removing Boss");
			handler.removeObject(this);
		}
	}

	// returns an image which gets pixel data from the specific file, whose format can be either GIF, JPEG or PNG. 
	// The underlying toolkit attemps to resolve multiple requests with the same filename to the same returned image.
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

	// cast the Graphics object passed into the rendering method to a Graphics2D object
	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	public void render(Graphics x) {
		Graphics2D g = (Graphics2D) x;
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, 138, Game.WIDTH, 138);
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);
		
		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);

	}

	@Override
	// gets the bounding rectangle of this rectangle
	// returns a new rectangle, equal to the bounding rectangle for this rectangle
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1) {
            handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler));
        }
	}

    public static void updateSprite(Themes theme) {
        // Set sprite based on current theme
        try {
            switch (theme) {
                case Space:
                    img = ImageIO.read(new File("src/images/EnemyBossRed.png"));
                    break;
                case Underwater:
                    img = ImageIO.read(new File("src/images/EnemyBossJellyfish.png"));
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error reading sprite file for EnemyBoss");
        }
    }
}
