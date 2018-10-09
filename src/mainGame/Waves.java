package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Waves implements GameMode {
	private Player player;
	private int currentLevelNum = 0;
	protected int maxTick = 2000,currentTick = 0;
	private Handler handler = null;
	private Random r = new Random();
	private ArrayList<ID> currentEnemy;
	private String[] side = { "left", "right", "top", "bottom" };
	private Level currentLevel = null;
	private Game game;
	private ArrayList<Integer> currentEnemySpawns;
	private HUD hud;
	private Image img = this.getImage("/images/cartoonspace.jpg");
	private int levelPopTimer = 0;
	private LevelText t;
	private ID lastEnemy = null;
	private ID lastBoss = (Math.random()*1 == 0 ? ID.EnemyBoss:ID.EnemyRocketBoss);
	public Waves(Player p,Handler h, HUD _hud){
		player = p;
		handler = h;
		hud = _hud;
	}
	
	//Links the ID of an enemy to actual creation.
	//This allows the gameMode to override the generic Level Spawning Scheme. IE if a boss doesn't care where a player is. 
	@Override
	public GameObject getEnemyFromID(ID enemy,Point spawnLoc){
		switch(enemy){
		case EnemyBasic:  return new EnemyBasic(spawnLoc.getX(), spawnLoc.getY(), 9, 9, ID.EnemyBasic, handler);
		case EnemySmart: return new EnemySmart(spawnLoc.getX(), spawnLoc.getY(), -5, ID.EnemySmart, handler);
		case EnemySweep: return new EnemySweep(spawnLoc.getX(), spawnLoc.getY(), 9, 2, ID.EnemySweep, handler);
		case EnemyShooter: return new EnemyShooter(spawnLoc.getX(),spawnLoc.getY(), 100, 100, -20 + (int)(Math.random()*5), ID.EnemyShooter, this.handler);
		case EnemyBurst: return new EnemyBurst(-200, 200, 15, 15, 200, side[r.nextInt(4)], ID.EnemyBurst, handler);
		case BossEye: return new EnemyBoss(ID.EnemyBoss, handler);
		case EnemyBoss: return new EnemyBoss(ID.EnemyBoss, handler);
		case EnemyRocketBoss: return new EnemyRocketBoss(100,100,ID.EnemyRocketBoss,this.player, this.handler,this.hud, this);
		case EnemyFast: return new EnemyFast(spawnLoc.getX(), spawnLoc.getY(), ID.EnemySmart, handler);
		default: return new EnemyBasic(spawnLoc.getX(),spawnLoc.getY(), 9, 9, ID.EnemyBasic, handler);
		}
	}
	/**
	 * Generates a random enemy ID
	 * @return ID (for entities)
	 */
	private ID randomEnemy(){	
		int r = (int)(Math.random()*6); //0-6 can be generated
		ID returnID = null;
		System.out.println("Enemy type of level " + this.currentLevelNum + " is " + r);
		switch(r){ //pick what enemy the random integer represents
			case 0: returnID = ID.EnemySweep; break;
			case 1: returnID = ID.EnemySmart; break;
			case 2: returnID = ID.EnemyBasic; break;
			case 3: returnID = ID.EnemyShooter; break;
			case 4: returnID = ID.EnemyBurst; break;
			case 5: returnID = ID.EnemyFast; break;
			default: returnID = randomEnemy(); break;
		}
		System.out.println(returnID + "| " + this.lastEnemy);
		if(returnID == this.lastEnemy){
			returnID = this.randomEnemy();
		}
		this.lastEnemy = returnID;
		return returnID;
	}
	/**
	 * Ticks Level classes generated.
	 * Generates levels when they are completed. 
	 */
	@Override
	public void tick() {
		currentTick++;
		this.levelPopTimer++;
		//after 3 seconds, the handler would remove the level text object "t".
		if(this.levelPopTimer>=100){
			handler.removeObject(t);
		}
		if(currentLevel==null || currentLevel.running()==false){
			this.currentLevelNum = this.currentLevelNum + 1;
			hud.setLevel(this.currentLevelNum);
			handler.clearEnemies();
			this.levelPopTimer = 0;
			t = new LevelText(Game .WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Level " + this.currentLevelNum + (this.currentLevelNum%5 == 0 ? ": Boss Level!!!":""), ID.Levels1to10Text);
			handler.addObject(t);
			
			double tempx = Math.random()*Game.WIDTH;
			double tempy = Math.random()*Game.HEIGHT;
			switch ((int)(Math.random()*5)){
			case 0: handler.addObject(new PickupSize(tempx,tempy));break;
			case 1: handler.addObject(new PickupHealth(tempx,tempy));break;
			case 2: handler.addObject(new PickupLife(tempx,tempy));break;
			case 3: handler.addObject(new PickupScore(tempx,tempy));break;
			case 4: handler.addObject(new PickupFreeze(tempx,tempy));break;
			}
			if(this.currentLevelNum%5 == 0){
				ArrayList<Integer>bossLimit = new ArrayList<Integer>();
				bossLimit.add(1);
				System.out.println("New Boss Level");
				currentLevel = new Level(handler, this, this.game, this.player,0,randomBoss(), bossLimit, -1 , false, false);
				
			}else{
				System.out.println("New Normal Level");
				this.createNewEnemyLists();
				System.out.println(this.currentEnemy.size());
				System.out.println(this.currentEnemySpawns.size());
				currentLevel = new Level(handler, this, this.game, this.player,0, this.currentEnemy,this.currentEnemySpawns,60*(20),false,false);
			}
			
		}
		currentLevel.tick();
		
	}
	/**
	 * Creates a new list of enemies for the next level to spawn.
	 * Sets the new list as a global variable for the game to access later. 
	 * (This can be later changed to return a list and can be passed into the level class | removing the global variable)
	 * Problem - Java Tuples cannot return both an arraylist of enemies, and the # of times they spawn. 
	 */
	private void createNewEnemyLists() {
		ArrayList<ID>newEnemy = new ArrayList<ID>();
		ArrayList<Integer>newSpawn = new ArrayList<Integer>();
		int curr = 0;
		do{
			curr++;
			ID e = this.randomEnemy();
			newEnemy.add(e);
			int s = (e.getDifficuty() + (int)(Math.random()*((e.getDifficuty()*0.1))));
			if(e.getDifficuty()==1)
				s = 1;
			newSpawn.add(s);
			System.out.println("----" + e + "-----" + s);
		}while(curr<=(this.currentLevelNum/15));
		this.currentEnemy = newEnemy;
		this.currentEnemySpawns = newSpawn;
		
	}
	/**
	 * 
	 * @return Returns an array of enemy bosses to be generated. 
	 * As of right now, enemy bosses are hard coded to only spawn once during a level. 
	 * See tick above.
	 */
	private ArrayList<ID> randomBoss() {
		ArrayList<ID>bossReturn = new ArrayList<ID>();
		if(this.lastBoss==ID.EnemyRocketBoss){
			System.out.println("Enemy Boss");
			bossReturn.add(ID.EnemyBoss);
			this.lastBoss = ID.EnemyBoss;
		}else{
			System.out.println("Enemy Rocket Boss");
			this.lastBoss = ID.EnemyRocketBoss;
			bossReturn.add(ID.EnemyRocketBoss);
		}
		return bossReturn;
	}
	/**
	 * Renders any static images for the level.
	 * IE Background. 
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
	}
	/**
	 * @param hardReset - if false only enemies are wiped. If true gamemode is completely reset. 
	 */
	@Override
	public void resetMode(boolean hardReset) {
		this.currentTick = 0;
		this.currentEnemy = null;
		this.currentLevel =  null;
		handler.clearEnemies();
		if(hardReset)
			this.currentLevelNum = 0;
	}
	@Override
	public void resetMode() {
		resetMode(true);
	}
	
	private Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return image;
	}

}
