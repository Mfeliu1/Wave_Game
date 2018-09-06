package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A warning that is displayed before EnemyBurst comes across the screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBurstWarning extends GameObject {

	private Handler handler;
	private int width;
	private int height;
	private int timer;
	private Color color;
	private int hasFlashed;

	public EnemyBurstWarning(double x, double y, int width, int height, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.width = width;
		this.height = height;
		timer = 10;
		this.color = Color.red;
		this.hasFlashed = 0;

	}

	public void tick() {
		flash();
		checkFlash();
	}

	public void flash() {
		timer--;
		if (timer == 5) {
			this.color = Color.black;
		} else if (timer == 0) {
			this.color = Color.red;
			this.hasFlashed++;
			timer = 10;
		}

	}

	public void checkFlash() {
		if (this.hasFlashed == 5) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				if (tempObject.getId() == ID.EnemyBurstWarning) {
					handler.removeObject(tempObject);
					i--;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(this.color);
		g.fillRect((int) x, (int) y, this.width, this.height);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
