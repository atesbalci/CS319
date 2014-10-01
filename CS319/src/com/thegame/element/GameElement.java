package com.thegame.element;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class GameElement {

	protected int x, y, verticalSpeed, jumpHeight;
	protected double health, maxHealth, xvel, yvel, verticalacc, horizontalacc;
	protected boolean ground, flying, smooth, active, jumping, fricted;
	protected boolean left, right, jump;

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

	abstract public Rectangle2D getRectangle();

	abstract public void obstruction(String direction, GameElement e);

	public int getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(int verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}

	public int getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getXvel() {
		return xvel;
	}

	public void setXvel(double xvel) {
		this.xvel = xvel;
	}

	public double getYvel() {
		return yvel;
	}

	public void setYvel(double yvel) {
		this.yvel = yvel;
	}

	public double getVerticalacc() {
		return verticalacc;
	}

	public void setVerticalacc(double verticalacc) {
		this.verticalacc = verticalacc;
	}

	public double getHorizontalacc() {
		return horizontalacc;
	}

	public void setHorizontalacc(double horizontalacc) {
		this.horizontalacc = horizontalacc;
	}

	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(boolean smooth) {
		this.smooth = smooth;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isFricted() {
		return fricted;
	}

	public void setFricted(boolean fricted) {
		this.fricted = fricted;
	}

	public double getMaxHealth() {
		return maxHealth;
	}
}
