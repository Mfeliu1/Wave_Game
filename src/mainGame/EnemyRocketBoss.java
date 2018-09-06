package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.net.URL;

public class EnemyRocketBoss extends GameObject {
	private Image img;
	private double dash_x,dash_y; //current target
	private boolean inDash = false; // in dash
	private int cooldown = 10; // dash cooldown
	private Player player;
	private double drawAngle = 90;
	private Handler handler;
	private int speed = 18;
	private int health = 1000;
	private HUD hud;
	private GameMode mode;
	public EnemyRocketBoss(double x, double y, ID id, Player p, Handler h, HUD hud, GameMode mode) {
		super(x, y, id);
		this.player = p;
		img = getImage("/images/Rocket_Boss.png");
		handler = h;
		this.hud = hud;
		this.mode = mode; 
	}

	@Override
	public void tick() {
		if(this.health%150 == 0){
			handler.addObject(mode.getEnemyFromID(ID.EnemyBurst, new Point(100,100)));
		}
		health--;
//		handler.addObject(new Trail(this.x - 80, this.y-296, ID.Trail, Color.red, 20, 20, 0.05, this.handler));
		if(inDash){
			this.speed = 28 - this.health/100;
			Move();
		}else{
			if(cooldown<=0){
				this.dash_x = player.getX()+16 + player.velX*1.5;
				this.dash_y = player.getY()+16 + player.velY*1.5;
				double angle = this.GetAngleOfLineBetweenTwoPoints(new Point.Double(this.x+40, this.y), new Point.Double(dash_x,dash_y));
				this.drawAngle = angle;
				this.inDash = true;
				cooldown = 60 - (int)(Math.random()*25); //lazy way to make cooldown shorter
			}else{
				cooldown--;
					this.dash_x = player.getX()+16;
					this.dash_y = player.getY()+16;
					double angle = this.GetAngleOfLineBetweenTwoPoints(new Point.Double(this.x+40, this.y), new Point.Double(dash_x,dash_y));
					this.drawAngle = angle;
			}
		}
		if(health<=0){
			System.out.println("Removing Boss");
			handler.removeObject(this);
		}
	}
    public double GetAngleOfLineBetweenTwoPoints(Point.Double p1, Point.Double p2)
    {
        double xDiff = p2.x - p1.x;
        double yDiff = p2.y - p1.y;
        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }
	private void Move(){
	// System.out.println("Moving");
		this.x = this.x + this.velX;
		this.y = this.y +  this.velY;

		double diffX = this.x - this.dash_x;
		double diffY = this.y - this.dash_y;
		double distance = Math.sqrt(((this.x - this.dash_x) * (this.x - this.dash_x))
				+ ((this.y - this.dash_y) * (this.y - this.dash_y)));

		this.velX = -((this.speed / distance) * diffX); // numerator affects speed of enemy
		this.velY = -((this.speed / distance) * diffY);// numerator affects speed of enemy

		if((Math.abs(this.dash_x-this.x)<=Math.abs(velX))&&(Math.abs(this.dash_y-this.y)<=Math.abs(velY))){
			this.inDash = false;
			this.velX = 0;
			this.velY = 0;
		}
	}
	@Override
	public void render(Graphics g) {
	    //draw health bar
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		
		
		Graphics2D g2d = (Graphics2D)g;
		//DEV TOOLS
		/*
		g2d.drawLine((int)this.x, (int)this.y, (int)this.dash_x, (int)this.dash_y); //DEV TOOL
		Ellipse2D e = new Ellipse2D.Double(this.dash_x,this.dash_y,10,10);
		g2d.draw(e);
		 */
		
		//Draw Rocket
		AffineTransform old = g2d.getTransform();
		g2d.translate(this.x, this.y);
		g2d.rotate(Math.toRadians(this.drawAngle + 90));
		g2d.drawImage(img, 0, 0, 80, 296, null);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, 80, 200);
	    Rectangle2D playerBounds = new Rectangle2D.Double(this.x-player.x,this.y-player.y,player.getPlayerWidth(),player.getPlayerHeight());
	    if(rec.contains(playerBounds)){
	    	hud.health = hud.health - 1;
	    }
	    g2d.setTransform(old);
	}
	//Unlike the other enemies, this boss will handle collisions internally with the player. This allows us to have an accurate hitbox..
	//Despite being angled. 
	@Override // In player.java, RocketBoss was never added to collisions so this should never be called. 
	public Rectangle getBounds() {
		return new Rectangle();
	}
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.err.println("AHHHH");
			System.out.println(e.getMessage());
		}

		return image;
	}
}
