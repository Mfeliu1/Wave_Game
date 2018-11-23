package mainGame;

/**
 * The upgrades that a user can have (they modify the game for the user)
 * 
 * @author Brandon Loehle 5/30/16
 *
 */
/**
 * 
 * @author Team B3
 * It contains all the upgrades
 * that are possibly avaiable for the player to obtain.
 * Descriptions of what the upgrades are are listed by the 
 * respective function 
 */
public class Upgrades {
	private Game game;
	private Handler handler;
	private HUD hud;
	private Player player;
	private UpgradeScreen upgradeScreen;
	private String ability = "none";
	//constructor
	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
	}
	//Remove all enemies on the screen
	public void clearScreenAbility() {
		handler.clearEnemies();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}
	//Shrink the player's size
	public void decreasePlayerSize() {
		player.setPlayerSize((int) (player.getPlayerHeight()/1.2));
	}
	//Add another life for the player
	public void extraLife() {
		hud.setExtraLives(hud.getExtraLives() + 1);
	}
	//increase the amount of health the player has
	public void healthIncrease() {
		hud.healthIncrease();
	}
	//Health comes back over time
	public void healthRegeneration() {
		hud.setRegen();
	}
	//The player takes less hit damage when an enemy hits them
	public void improvedDamageResistance() {
		player.setDamage(player.getDamage()-.25);
	}
	//Skip a level (p.s set dev mode to false to test if this works)
	public void levelSkipAbility() {
		handler.clearEnemies();
		hud.setLevel(hud.getLevel() + 1);
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}

	}
	//Freeze the screen so enemies don't move, time would still tick on
	public void freezeTimeAbility() {
		handler.pause();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}
	//increases the players speed
	public void speedBoost() {
		Player.playerSpeed *= 2;
	}
	//returns the ability the player has
	public String getAbility() {
		return ability;
	}

	/**
	 * Activate the correct upgrade
	 * 
	 * @param path
	 *            is to the image of the upgrade that was pressed by the user
	 */
	public void activateUpgrade(String path) {
		if (path.equals("/images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (path.equals("/images/decreaseplayersize.png")) {
			decreasePlayerSize();
		} else if (path.equals("/images/extralife.png")) {
			extraLife();
		} else if (path.equals("/images/healthincrease.png")) {
			healthIncrease();
		} else if (path.equals("/images/healthregeneration.png")) {
			healthRegeneration();
		} else if (path.equals("/images/improveddamageresistance.png")) {
			improvedDamageResistance();
		} else if (path.equals("/images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (path.equals("/images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (path.equals("/images/speedboost.png")) {
			speedBoost();
		}

	}
	//re add all the upgrades again
	public void resetUpgrades() {
		Player.playerSpeed = 10;
		hud.resetHealth();
		hud.resetRegen();
		hud.setExtraLives(0);
		player.setPlayerSize(32);
		upgradeScreen.resetPaths();
	}
}
