package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.dungeonescape.common.ContactConstants;

public abstract class GameElement {
	protected int width, height;
	protected double x, y, verticalSpeed, horizontalSpeed, weight, elasticity;
	protected boolean flying, smooth, active, fricted, fixed;

	public GameElement(double x, double y) {
		this.x = x;
		this.y = y;
		flying = false;
		smooth = true;
		fricted = true;
		verticalSpeed = 0;
		horizontalSpeed = 0;
		active = true;
		width = 20;
		height = 40;
		weight = 10;
		fixed = false;
		elasticity = 0.5;
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}

	public Line2D getLine() {
		return null;
	}

	public boolean intersects(GameElement e) {
		if (e.getLine() == null) {
			if (getRectangle().intersects(e.getRectangle())) {
				if (!e.isSmooth()) {
					e.contact(ContactConstants.IN, this);
					return false;
				}
				return true;
			}
			return false;
		} else {
			if (getRectangle().intersectsLine(e.getLine())) {
				if (!e.isSmooth()) {
					e.contact(ContactConstants.IN, this);
					return false;
				}
				return true;
			}
			return false;
		}
	}

	public Point getCenter() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}

	public void contact(int direction, GameElement e) {
	}

	public void timestep(double d, List<GameElement> elementsInWorld) {
	}

	public void useAction() {
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

	public void draw(Graphics g, Point camera) {
	}

	public double getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(int verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
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

	public boolean isFricted() {
		return fricted;
	}

	public void setFricted(boolean fricted) {
		this.fricted = fricted;
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
