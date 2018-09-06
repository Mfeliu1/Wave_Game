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
 * @author Katie Rosell
 * Any changes or comments I made I sign off with my name!
 */

public class Menu {

	private Game game;
	private Handler handler;
	@SuppressWarnings("unused")
	private HUD hud;
	private Image img;
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
		if (game.gameState == STATE.Menu) {
			//display the background  
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			//usng the handler, render the graphics
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
			g.drawRect(700, 300, 470, 250); //changes the rectangle size drawn : kt
			g.setFont(font3);
			g.setColor(Color.white);
			g.drawString("Waves", 730, 465);//move the text down and center it inside the rectangle : katie              
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
			//Credits to original editor
			g.setColor(Color.white);
			g.setFont(font2);
			g.drawString("Credits: Brandon Loehle's AP CSC project '15-'16", 0, 1000);
			//Now if the user clicked the Help button
		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
			Font font = new Font("impact", 1, 50); //make a new font
			Font font2 = new Font("impact", 1, 30); //also make a new font
			//Help text
			g.setFont(font); //set the font with its parameters above 
			g.setColor(Color.white);
			g.drawString("Help", 900, 70); //this is the help text, yayy
			//Instructions 
			g.setFont(font2);
			g.drawString("Player controls: W A S D" + " \n"
					+ "Defeat bosses and avoid enemies as long as you can as you probe deeper into space...", 40, 200);
			//Back button
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(850, 300, 200, 64); //ugly rectangle box
			g.drawString("Back", 920, 340); //make it the back button
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


