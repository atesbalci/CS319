package com.thegame.tool;
import java.awt.Graphics;
import java.awt.geom.*;

import com.thegame.element.Bullet;

public abstract class Weapon {
	private double x, y;
	private boolean direction;
	private boolean melee;
	private boolean firing;
	private double damage;
	
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
	
	abstract public Rectangle2D getImpact();
	abstract public Bullet getBullet();
	abstract public void draw(Graphics g);
	abstract public void fire();
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean getDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public boolean isFiring() {
		return firing;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void setMelee(boolean melee) {
		this.melee = melee;
	}
}
