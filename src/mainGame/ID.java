package mainGame;

/**
 * Used to easily identify each game entity
 * @author Brandon Loehle
 * 5/30/16
 */

/**
 * 
 * @author Katie Rosell
 * These enumerators permanently identify each game entity to 
 * make it easy for identification.
 * Class fixing implemented by Joe P.
 */
public enum ID {
	//How many of these should be spawned (will be changed + or - 20% unless marked as 1(for boss)
	//-1 is a non-enemy entity
	EnemyBasic(8),
	EnemyFast(15),
	EnemySmart(10),
	EnemyBurst(10),
	EnemySweep(35),
	EnemyShooter(3),
	EnemyBoss(1),
	EnemyRocketBoss(2),
	BossEye(1),
	EnemyShooterMover(3),
	EnemyShooterSharp(1),
	//non-enemies
	EnemyBossBullet(-1),
	EnemyBossBomb(-1),
	EnemyBurstWarning(-1),
	EnemyShooterBullet(-1),
	EnemyBossBombBullet(-1),
	Firework(-1),
	FireworkSpark(-1),
	CircleTrail(-1),
	PickupHealth(-1),
	PickupSize(-1),
	PickupLife(-1),
	PickupScore(-1),
	Levels1to10Text(-1),
	Player(-1),
	Trail(-1), PickupFreeze(-1), EnemyRocketBossMissile(-1);
    private int numVal;
    
    ID(int numVal) { //dis was joe 
        this.numVal = numVal;
    }

    public int getDifficuty() {
        return numVal;
    }
}
