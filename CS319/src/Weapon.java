import java.awt.Graphics;
import java.awt.geom.*;

public abstract class Weapon {
	int x, y;
	boolean direction, melee, firing;
	double damage;
	
	public Weapon(boolean melee) {
		x = 0;
		y = 0;
		direction = true;
		this.melee = melee;
		firing = false;
		damage = 0;
	}
	
	public boolean firing() {
		return firing;
	}
	
	public boolean isMelee() {
		return melee;
	}
	
	abstract public Rectangle2D.Double getImpact();
	abstract public Bullet getBullet();
	abstract public void draw(Graphics g);
	abstract public void fire();
}
