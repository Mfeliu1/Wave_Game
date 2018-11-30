package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

/*****
 * 
 */

public class Menu {

	private Game game;
	private Handler handler;
	@SuppressWarnings("unused")
	private HUD hud;
	private Image img;
	private Image PowerCoin;
	private Image PowerLife;
	private Image PowerHealth;
	private Image PowerSpeed;
	private Image PowerSlow;
	private Image HUDshield1;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	//private static boolean rockMusic = true; //the music that is supposed to play

	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		timer = 10;
		r = new Random();
		addColors();

		img = getImage("/images/Background.png");

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}

	//using the java color picker, which colors you will add to the scene
	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
	}

	//tick method, allows game to check time and spawn fireworks
	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}
	//THIS MAKES THE MENU LOOK THE WAY IT DOES USING THE GRAPHICS 
	public void render(Graphics g) {
	    // Change background on theme change
	    if (handler.getTheme() == Themes.Space) {
            img = getImage("/images/Background.png");
        } else if (handler.getTheme() == Themes.Underwater) {
            img = getImage("/images/Water.jpg");
        }

		if (game.gameState == STATE.Menu) {
			//display the background  
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			//using the handler, render the graphics
			handler.render(g);
			//create the font objects
			Font font = new Font("Amoebic", 1, 100); //the title
			Font font2 = new Font("Amoebic", 1, 34); //help and quit
			Font font3 = new Font("Amoebic", 1, 130); //use for Waves
			//Main Title
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Loehle's Sandbox", 500, 100);
			//Waves button, the start game button essentially
			g.setColor(Color.white);
			g.drawRect(700, 300, 470, 250); //changes the rectangle size drawn
			g.setFont(font3);
			g.setColor(Color.white);
			g.drawString("Waves", 730, 465);//move the text down and center it inside the rectangle            
			//Help button
			g.setColor(Color.white);
			g.drawRect(230, 360, 260, 200);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 250, 500);
			//The Quit button 
			g.setColor(Color.white);
			g.drawRect(1390, 360, 260, 200);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Quit", 1400, 500);

            //Theme buttons
            g.setColor(Color.white);
            g.setFont(font);

            g.drawString("Themes:", 330,710);

            g.drawRect(400, 730, 350, 120);
            g.drawString("Space", 430, 815);

            g.drawRect(850, 730, 650, 120);
            g.drawString("Underwater", 870, 825);

			//Credits to team that worked on game last editor
			g.setColor(Color.white);
			g.setFont(font2);
			g.drawString("Credits: Irrelephant Games '18-'19", 0, 1000);
			//Now if the user clicked the Help button
		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
			Font font = new Font("impact", Font.PLAIN, 50); //make a new font
			Font font2 = new Font("impact", Font.PLAIN, 30); //also make a new font
			Font font3 = new Font("impact", Font.PLAIN, 30); //also make a new font
			//gets images, allows them to be used on help menu
			PowerCoin = getImage("/images/PickupCoin.png");
			PowerSlow = getImage("/images/freezeAbilitySnowflake.png");
			PowerHealth = getImage("/images/PickupHealth.png");
			PowerLife = getImage("/images/PickupLife.png");
			PowerSpeed = getImage("/images/ShrinkAbility0.png");
			HUDshield1 = getImage("/images/shield1.png");
			//Help text
			g.setFont(font); //set the font with its parameters above 
			g.setColor(Color.white);
			g.drawString("Help", 900, 70); //this is the help text, yayy
			//Instructions 
			g.setFont(font);
			g.drawString("  How to play:", 35, 160);
			g.drawString("  Player controls: WASD/Arrow Keys", 35, 240);
			g.drawString("  Avoid enemies as difficulty increases", 35, 300);
			g.drawString("  Boss levels occur every five levels", 35, 360);
			g.drawString("  Players can pick up various powerups", 35, 420);
			g.drawString("  Players obtain a new powerup after every boss", 35, 480);
			g.drawString("  Power-Ups:", 1100, 160);
			g.drawString("		-  Screen freeze powerup", 1125, 240);
			g.drawImage(PowerSlow, 1655, 205, 40, 40, null);
			g.drawString("		-  Make players smaller", 1125, 300);
			g.drawImage(PowerSpeed, 1635, 260, 40, 40, null);
			g.drawString("		-  More points", 1125, 360);
			g.drawImage(PowerCoin, 1430, 325, 40, 40, null);
			g.drawString("		-  Health refill", 1125, 420);
			g.drawImage(PowerHealth, 1430, 380, 40, 40, null);
			g.drawString("		-  Extra Life", 1125, 480);
			g.drawImage(PowerLife, 1370, 440, 40, 40, null);
			g.drawString("  Helpful Tips:", 35, 700);
			g.drawImage(HUDshield1, 55, 740, 40, 40, null);
			g.drawString("  : Represents amount of Damage Resistance you have", 80, 780);
			g.drawString("  Press E or Enter to activate abilities", 35, 860);
			g.drawString("  Git Gud", 35, 940);
			//Back button
			g.setFont(font3);
			g.setColor(Color.white);
			g.drawRect(910, 300, 150, 64); //ugly rectangle box
			g.drawString("Back", 955, 340); //make it the back button
		}
	}
//Background image source path
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

}


