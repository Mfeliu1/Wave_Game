package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * @author Team B3
 * This interface can be implemented in other classes
 * and these functions can be overridden
 */
public interface GameMode {
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void resetMode();
	public abstract GameObject getEnemyFromID(ID x, Point spawnLoc);
	void resetMode(boolean hardReset);
}
