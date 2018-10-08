package mainGame;

import java.awt.Graphics;

public abstract class Enemy extends GameObject {

	public Enemy(double x, double y, ID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	public void drawHitBox(Graphics g) {
		g.fillRect(this.getBounds().x,this.getBounds().y,this.getBounds().width,this.getBounds().height);
	}

}
