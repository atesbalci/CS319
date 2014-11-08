package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import com.dungeonescape.tool.BoomerangTool;

public class Boomerang extends GameElement {
	private BoomerangTool thrower;
	private double speed;
	private boolean returning;
	private int range;

	public Boomerang(double x, double y, double angle, double speed,
			BoomerangTool s) {
		super(x, y);
		setFlying(true);
		setSmooth(false);
		setFricted(false);
		verticalSpeed = Math.sin(angle) * speed;
		horizontalSpeed = Math.cos(angle) * speed;
		width = 5;
		height = 5;
		returning = false;
		thrower = s;
		range = 200;
	}

	public Boomerang() {
		this(0, 0, 0, 0, null);
		setActive(false);
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		double distance = Math.hypot(getCenter().x - thrower.getX(),
				getCenter().y - thrower.getY());
		if (distance >= range) {
			returning = true;
		} else if (distance < 5 && returning) {
			setActive(false);
		}
	}

	@Override
	public boolean intersects(GameElement e) {
		if (e instanceof StaticElement) {
			if (super.intersects(e)) {
				horizontalSpeed = 0;
				verticalSpeed = 0;
				returning = true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		g.drawRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public BoomerangTool getThrower() {
		return thrower;
	}

	public void setThrower(BoomerangTool thrower) {
		this.thrower = thrower;
	}

	public boolean isReturning() {
		return returning;
	}

	public void setReturning(boolean returning) {
		this.returning = returning;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public Point getCenter() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}
}
