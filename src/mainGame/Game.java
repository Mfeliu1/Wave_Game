package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.awt.GraphicsEnvironment;

/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references too ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 * @author Joe Passanante 11/28/17
 */

public class Game extends Canvas{

	private static final long serialVersionUID = 1L;

	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final double aspectRatio = screenSize.getWidth()/screenSize.getHeight();
	private static int WindowWidth = (int) (1 * screenSize.getWidth());
	private static int WindowHeight = (int) (1 *screenSize.getHeight());

	public static final Dimension canvasSize = canvasSize();
	public static final Dimension windowSize = new Dimension(WindowWidth,WindowHeight);

	private boolean running = false;
	private HUD hud;
	private Menu menu;
	private GameOver gameOver;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private int FPS = 0;
	private static int HS;
	
	protected Handler handler;
	protected Player player;
	protected GameManager gm;
	
	//---------------------------------------------------------------------------------
	protected boolean devMode = true;//true - display game information | false - do not
	//---------------------------------------------------------------------------------
	
	public static int TEMP_COUNTER;
	public static final int WIDTH  = (int)canvasSize.getWidth();
	public static final int HEIGHT = (int)canvasSize.getHeight();

	public STATE gameState = STATE.Menu;
	public GAME_AUDIO gameCurrentClip = GAME_AUDIO.Menu;
	public boolean paused = false;

	public boolean africa = false;

	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, Help, Game, GameOver, Upgrade
	};
	
	public enum GAME_AUDIO {
		Menu, Game, None
	}

	/**
	 * Initialize the core mechanics of the game
	 */
	public Game() {
		handler = new Handler();
		handler.updateSprites();
		hud = new HUD();
		menu = new Menu(this, this.handler, this.hud);
		
		player = new Player(canvasSize.getWidth() / 2 - 32, canvasSize.getHeight() / 2 - 32, ID.Player, handler, this.hud, this);
		this.upgradeScreen = new UpgradeScreen(this, handler, hud);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player);
		gameOver = new GameOver(this, this.handler, this.hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.upgradeScreen,
				this.player, this.upgrades);
		gm = new GameManager(this, hud);
		this.addKeyListener(new KeyInput(this.handler, this, this.hud, this.player, this.upgrades));
		this.addMouseListener(mouseListener);
		AudioUtil.closeGameClip();
		AudioUtil.playMenuClip(true, false);

		@SuppressWarnings("unused")
		Window window = new Window((int) windowSize.getWidth(), (int)windowSize.getHeight(), "Wave Game", this);
		window.setIconImage(this.getImage("/images/Rocket_Boss.png"));
		window.repaint();
	}
	
	/**
	 * 
	 * @return int value of highscore saved in local Highscores.txt
	 */
	public static int highScore(){
		Scanner fileInput;
		File inFile = new File("src/HighScores.txt");
		
			try {
				fileInput = new Scanner(inFile);
					HS = Integer.parseInt(fileInput.next());
					inFile.canWrite();
					fileInput.close();
					System.out.print("success");
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.print("OOPS");
			}
			return HS;
			
	}
	/**
	 * Starts the game loop.
	 */
	public synchronized void start() {
		running = true;
		run();
	}
	/**
	 * Stops the game loop.
	 */
	public synchronized void stop() {
			running = false;
	}
	/**
	 * Main game loop that handles ticks. 
	 * Ticks 60 times a second.
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();// 60 times a second, objects are being updated
				delta--;
			}
			if (running)
				render();// 60 times a second, objects are being drawn
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS = frames;
				frames = 0;
			}
		}
		stop();

	} 
	/**
	 * Main tick function that calls it for all operating classes in the game. 
	 * Ticks classes based on current game state. 
	 */
	private void tick() {
		if (this.paused) {return;}
		
		handler.tick();// handler must always be ticked in order to draw all entities.
		if (gameState == STATE.Menu || gameState == STATE.Help) {// user is on menu, update the menu items
			if (this.gameCurrentClip != GAME_AUDIO.Menu) {
				this.gameCurrentClip = GAME_AUDIO.Menu;
				AudioUtil.closeGameClip();
				AudioUtil.playMenuClip(true, false);
			}
			menu.tick();
		} 
		if (gameState == STATE.Game) {// game is running
			if (this.gameCurrentClip != GAME_AUDIO.Game) {
				this.gameCurrentClip = GAME_AUDIO.Game;
				AudioUtil.closeMenuClip();
				AudioUtil.playGameClip(true);
			}
			hud.tick();
			gm.tick();
		} 
		else if (gameState == STATE.Upgrade) {// user is on upgrade screen, update the upgrade screen
			upgradeScreen.tick();
		} else if (gameState == STATE.GameOver) {// game is over, update the game over screen
			gameOver.tick();
		}

	}

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring the
	 * Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {
		/*
		 * BufferStrategies are used to prevent screen tearing. In other words, this
		 * allows for all objects to be redrawn at the same time, and not individually
		 */
		if (getWidth() <= 0 || getHeight() <= 0) {
	        return;
	    }
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		double scaleFactor = windowSize.getWidth()/canvasSize.getWidth(); 
		g.scale(scaleFactor, scaleFactor);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, (int)Game.canvasSize.getWidth(), (int)Game.canvasSize.getHeight());
		if (gameState == STATE.Game) {
			gm.render(g); 
			hud.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.Help) {
			menu.render(g);
		} else if (gameState == STATE.Upgrade) {
			upgradeScreen.render(g);
		} else if (gameState == STATE.GameOver) {
			gameOver.render(g);
		}
		if(devMode){
			//debug menu
			Font font2 = new Font("Amoebic", 1, 25);
			g.setColor(Color.white);
			g.setFont(font2);
			g.drawString("Objects: " + handler.getNumObjects(), Game.WIDTH-300, Game.HEIGHT-200);
			g.drawString("Pickups: " + handler.getNumPickUps(), Game.WIDTH-300, Game.HEIGHT-150);
			g.drawString("FPS(?): " + this.FPS, Game.WIDTH-300, Game.HEIGHT-100);
			g.drawString("Trails: " + handler.getTrails() + " / " + handler.getNumObjects(), Game.WIDTH-300, Game.HEIGHT-50);
		}
		handler.render(g);

		g.dispose();
		g.scale(1/scaleFactor, 1/scaleFactor);
		bs.show();
	}

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't leave
	 * screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 * 
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public static void main(String[] args) {
			AudioUtil.playGameClip(true);
			new Game();	
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

	private static Dimension canvasSize() {
		// Choose canvas size that conforms to screen aspect ratio
		if (aspectRatio > 1.7) {
			// Screen aspect ratio is 16:9 or wider
			return new Dimension(1920,1080);
		} else if (aspectRatio < 1.4) {
			// Screen aspect ratio is 4:3 or taller
			return new Dimension(1920,1440);
		} else {
			// Screen aspect ratio is somewhere in between, probably 16:10
			return new Dimension(1920,1200);
		}
	}

	public void toggleMenuMusic() {
	    // Toggle menu theme between Space Jam and Africa
		africa = !africa;
		// Restart menu music
		AudioUtil.closeMenuClip();
		AudioUtil.playMenuClip(true, africa);
	}
}
