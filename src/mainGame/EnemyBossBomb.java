package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBossBomb extends Enemy {

	// instances
	private Handler handler;
	int explodeHeight = (int) (Math.random()*Game.HEIGHT);
	double sizeX, sizeY;
	// constructor
	// used to initialize the state of the object
	public EnemyBossBomb(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velY = 5;
		sizeX = 32;
		sizeY = 32;
	}

	// methods
	// is called every frame, allows game objects to update themselves before being rendered.
	public void tick() {
		this.y += velY;
		if (y>explodeHeight) {
			handler.removeObject(this);
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,0,16));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,16,16));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,16,0));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,16,-16));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,0,-16));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,-16,-16));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,-16,0));
			handler.addObject(new EnemyBossBombBullet((int) this.x + 48, (int) this.y + 80, ID.EnemyBossBombBullet, handler,-16,16));
		}

	}
	
	// is the abstract base class for all graphics contexts that allow an application to draw 
	// onto components that are realized on various devices, as well as onto off-screen images
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int) (x-sizeX/2), (int) (y-sizeY/2), (int)sizeX,(int)sizeY);
	}

	@Override
	// gets the bounding rectangle of this rectangle
	// returns a new rectangle, equal to the bounding rectangle for this rectangle
	public Rectangle getBounds() {
		return new Rectangle((int) (x-sizeX/2), (int) (y-sizeY/2), (int)sizeX,(int)sizeY);
	}

}
