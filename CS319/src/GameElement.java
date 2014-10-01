import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class GameElement {

	int x, y, verticalSpeed, jumpHeight;
	double health, maxHealth, xvel, yvel, verticalacc, horizontalacc;
	boolean ground, flying, smooth, active, jumping, fricted;
	boolean left, right, jump;

	public GameElement(int x, int y) {
		this.x = x;
		this.y = y;
		ground = false;
		flying = false;
		smooth = true;
		fricted = true;
		verticalSpeed = 0;
		jumpHeight = 0;
		jumping = false;
		active = true;
		setMaxHealth(100);
		left = false;
		right = false;
		jump = false;
		verticalacc = 0;
		horizontalacc = 0;
	}

	public void action(double d) {
		if (right) {
			if (xvel < verticalSpeed)
				xvel += verticalacc * d;
		}
		if (left) {
			if (xvel > -verticalSpeed)
				xvel -= verticalacc * d;
		}
		if (jump) {
			if (ground) {
				ground = false;
				jumping = true;
			}

			if (jumping) {
				if (yvel > -jumpHeight)
					yvel -= horizontalacc * d;
				else {
					jumping = false;
				}
			}
		}
	}

	public void right(boolean b) {
		right = b;
	}

	public void left(boolean b) {
		left = b;
	}

	public void jump(boolean b) {
		if (b)
			jump = true;
		else {
			jump = false;
			jumping = false;
		}
	}

	public void setMaxHealth(double h) {
		maxHealth = h;
		health = h;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void moveX(int xChange) {
		x += xChange;
	}

	public void moveY(int yChange) {
		y += yChange;
	}

	public void drawHealth(Graphics g, int x, int y) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillRect(x - (int) (maxHealth / 2), y - 20, (int) maxHealth, 15);
		g.setColor(Color.green);
		g.fillRect(x - (int) (maxHealth / 2), y - 20, (int) health, 15);
		g.setColor(Color.black);
		g.drawRect(x - (int) (maxHealth / 2), y - 20, (int) maxHealth, 15);
		g.drawString((int) health + "/" + (int) maxHealth, x
				- (int) (maxHealth / 2), y - 7);
		g.setColor(c);
	}

	public void damage(double damage) {
		if (maxHealth > 0) {
			health -= damage;
			if (health <= 0)
				active = false;
		}
	}

	abstract public void draw(Graphics g);

	abstract public Rectangle2D.Double getRectangle();

	abstract public void obstruction(String direction, GameElement e);
}
