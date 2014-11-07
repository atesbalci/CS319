package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;

public abstract class GameElement {

	protected int width, height;
	protected double health, maxHealth, verticalacc, horizontalacc, x, y,
			verticalSpeed, horizontalSpeed, jumpHeight, weight, elasticity;
	protected boolean ground, flying, smooth, active, jumping, fricted, left,
			right, jump, fixed;

	public GameElement(double x, double y) {
		this.x = x;
		this.y = y;
		ground = false;
		flying = false;
		smooth = true;
		fricted = true;
		verticalSpeed = 0;
		horizontalSpeed = 0;
		jumpHeight = 0;
		jumping = false;
		active = true;
		maxHealth = 100;
		left = false;
		right = false;
		jump = false;
		verticalacc = 0;
		horizontalacc = 0;
		width = 20;
		height = 40;
		weight = 10;
		fixed = false;
		elasticity = 0.5;
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	public boolean intersects(GameElement e) {
		if (getRectangle().intersects(e.getRectangle())) {
			if (!e.isSmooth()) {
				e.contact("", this);
				return false;
			}
			return true;
		}
		return false;
	}

	public void contact(String direction, GameElement e) {
		if (direction.equals("bottom")) {
			if (horizontalSpeed < 0.1 && horizontalSpeed > -0.1)
				ground = true;
		}
	}

	public void timestep(double d, List<GameElement> elementsInWorld) {
		if (right) {
			if (horizontalSpeed < horizontalacc * 10)
				horizontalSpeed += horizontalacc * d;
		}
		if (left) {
			if (horizontalSpeed > horizontalacc * -10)
				horizontalSpeed -= horizontalacc * d;
		}
		if (jump) {
			if (ground) {
				ground = false;
				jumping = true;
			}

			if (jumping) {
				if (verticalSpeed > -jumpHeight)
					verticalSpeed -= verticalacc * d;
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

	public double getX() {
		return (int) x;
	}

	public double getY() {
		return (int) y;
	}

	public void moveX(double xChange) {
		x += xChange;
	}

	public void moveY(double yChange) {
		y += yChange;
	}

	public void drawHealth(Graphics g, int x, int y) {
		// Color c = g.getColor();
		// g.setColor(Color.red);
		// g.fillRect(x - (int) (maxHealth / 2), y - 20, (int) maxHealth, 15);
		// g.setColor(Color.green);
		// g.fillRect(x - (int) (maxHealth / 2), y - 20, (int) health, 15);
		// g.setColor(Color.black);
		// g.drawRect(x - (int) (maxHealth / 2), y - 20, (int) maxHealth, 15);
		// g.drawString((int) health + "/" + (int) maxHealth, x
		// - (int) (maxHealth / 2), y - 7);
		// g.setColor(c);
	}

	public void damage(double damage) {
		if (maxHealth > 0) {
			health -= damage;
			if (health <= 0)
				active = false;
		}
	}

	public void draw(Graphics g, Point camera) {
	}

	public double getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(int verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}

	public double getJumpHeight() {
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

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getHorizontalSpeed() {
		return horizontalSpeed;
	}

	public void setHorizontalSpeed(double horizontalSpeed) {
		this.horizontalSpeed = horizontalSpeed;
	}

	public void setVerticalSpeed(double verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
		if (verticalSpeed > -0.1 && verticalSpeed < 0.1)
			ground = false;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public double getElasticity() {
		return elasticity;
	}

	public void setElasticity(double elasticity) {
		this.elasticity = elasticity;
	}
}
