package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class HUD {

	public double health = 100;
	private double healthMax = 100;
    private static int highscore = Game.highScore();
	private double greenValue = 255;

	private int score = 0;
	private int level = 0;

	private boolean regen = false;
	private int timer = 60;
	private int healthBarWidth = 400;
	private int healthBarModifier = 2;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;

	private Color scoreColor = Color.white;

	private int extraLives = 0;
	public int levelProgress;
	public Player player;
	
	private Image img;
	private Image HUDshield1;
	private Image HUDshield2;
	private Image HUDshield3;
	private Image HUDshield4;
	private Image HUDshield5;

	public void tick() {
		health = Game.clamp(health, 0, health);

		greenValue = Game.clamp(greenValue, 0, 255);

		greenValue = health * healthBarModifier;
		
		greenValue = Game.clamp(greenValue, 0, 255);

		score++;

		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0 && health < 100) {
				health += 1;
				timer = 60;
			}
		}
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 30);
		
		g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		HUDshield1 = getImage("/images/shield1.png");
		HUDshield2 = getImage("/images/shield2.png");
		HUDshield3 = getImage("/images/shield3.png");
		HUDshield4 = getImage("/images/shield4.png");
		HUDshield5 = getImage("/images/shield5.png");

		g.setColor(Color.GRAY);
		g.fillRect(15, 1000, healthBarWidth, 64);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 1000, (int) health * 4, 64);
		g.setColor(scoreColor);
		g.drawRect(15, 1000, healthBarWidth, 64);

		g.setFont(font);

		g.drawString("Score: " + score, 15, 25);
		g.drawString("Level: " + level, 15, 75);
		g.drawString("Extra Lives: " + extraLives, 15, 125);
		g.drawString("Level Progress: " + levelProgress + "%", 15, 175);
		g.drawString("Health: " + (int)health + "/" + (int)healthMax, 15, 1050);
		g.drawString("Damage Resist: " +  (2 -player.getDamage()), 15, 275);
		g.drawString("Player Size: " + player.getPlayerHeight(), 15, 325);
		g.drawString("Regeneration: " + regen, 15, 375);
		g.drawString("High Score: " + highscore, 1500, 25);
		if ((2 -player.getDamage()) == 0.0) {
			g.drawImage(HUDshield1, 440, 1010, 40, 40, null);
			g.drawString("" + (2 -player.getDamage()), 500, 1040);
		} else if ((2 -player.getDamage()) == 0.25) { 
			g.drawImage(HUDshield2, 440, 1010, 40, 40, null);
			g.drawString("" + (2 -player.getDamage()), 500, 1040);
		} else if ((2 -player.getDamage()) == 0.50) {
			g.drawImage(HUDshield3, 440, 1010, 40, 40, null);
			g.drawString("" + (2 -player.getDamage()), 500, 1040);
		} else if ((2 -player.getDamage()) == 0.75) {
			g.drawImage(HUDshield4, 440, 1010, 40, 40, null);
			g.drawString("" + (2 -player.getDamage()), 500, 1040);
		} else if ((2 -player.getDamage()) > 0.76) {
			g.drawImage(HUDshield5, 440, 1010, 40, 40, null);
			g.drawString("" + (2 -player.getDamage()), 500, 1040);
		} 

		
		
		
		if(highscore < score){
			highscore = score;
		}
		

		if (ability.equals("freezeTime")) {//comment
			g.drawString("Time Freezes: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("clearScreen")) {
			g.drawString("Screen Clears: " + abilityUses, Game.WIDTH - 300, 64);
		} else if (ability.equals("levelSkip")) {
			g.drawString("Level Skips: " + abilityUses, Game.WIDTH - 300, 64);
		}
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public int getAbilityUses() {
		return this.abilityUses;
	}

	public void setAbilityUses(int abilityUses) {
		this.abilityUses = abilityUses;
	}

	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setRegen() {
		regen = true;
	}

	public void resetRegen() {
		regen = false;
	}

	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	public int getExtraLives() {
		return this.extraLives;
	}

	public void healthIncrease() {
		doubleHealth = true;
		healthMax +=20;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = (int) (healthMax*4);
	}

	public void resetHealth() {
		doubleHealth = false;
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2;
		healthBarWidth = 400;
	}

	public void restoreHealth() {
		this.health = healthMax;
	}
	
		public static int thisHighScore() {
		return highscore;
	}

		public boolean getRegen() {
			return regen;
		}
		
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
