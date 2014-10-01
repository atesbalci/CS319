import java.awt.geom.*;
import java.awt.*;

public class Bullet extends GameElement {
	int height, width;
	double damage;
	
	public Bullet(int x, int y, boolean direction, double damage) {
		super(x, y);
		damage = 3;
		flying = true;
		smooth = false;
		fricted = false;;
		verticalSpeed = 5;
		if(direction)
			right = true;
		else
			left = true;
		width = 50;
		height = 10;
		this.damage = damage;
	}
	
	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		g.fillRect((int)x, (int)y, width, height);
	}
	
	public void obstruction(String direction, GameElement e) {
		active = false;
		if(e instanceof Enemy) {
			e.damage(damage);
		}
	}
}
