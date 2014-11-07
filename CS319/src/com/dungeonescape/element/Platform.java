package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Platform extends StaticElement implements Triggerable {
	private double travel;
	private int maxTravel;
	private boolean activated;
	private double speed;
	private boolean verticalTravel;
	private double origin;

	public Platform(int x, int y, int width, int height, int maxTravel,
			boolean verticalTravel) {
		super(x, y);
		this.width = width;
		this.height = height;
		speed = 8;
		this.maxTravel = maxTravel;
		activated = false;
		travel = 0;
		this.verticalTravel = verticalTravel;
		if (verticalTravel)
			origin = y;
		else
			origin = x;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		g.fillRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	@Override
	public void contact(String direction, GameElement e) {
		super.contact(direction, e);
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		super.timestep(d, elementsInWorld);
		double diff = 0;
		if (activated) {
			if (maxTravel > 0) {
				if (travel < maxTravel)
					diff = speed * d;
			} else {
				if (travel > maxTravel)
					diff = -speed * d;
			}
		} else {
			if (travel > 0)
				diff = -speed * d;
			else if (travel < 0)
				diff = speed * d;
		}
		travel += diff;
		if (verticalTravel) {
			moveY(diff);
		} else {
			moveX(diff);
		}
		moveY(-1);
		for (GameElement e : elementsInWorld) {
			if (this.intersects(e) && !e.isFixed() && e.isSmooth()) {
				if (verticalTravel)
					e.moveY(diff);
				else
					e.moveX(diff);
			}
		}
		moveY(1);
	}

	@Override
	public void trigger(boolean b) {
		if (b) {
			activated = true;
		} else {
			activated = false;
		}
	}

	public double getTravel() {
		return travel;
	}

	public void setTravel(double travel) {
		this.travel = travel;
	}

	public int getMaxTravel() {
		return maxTravel;
	}

	public void setMaxTravel(int maxTravel) {
		this.maxTravel = maxTravel;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isVerticalTravel() {
		return verticalTravel;
	}

	public void setVerticalTravel(boolean verticalTravel) {
		this.verticalTravel = verticalTravel;
	}

	public double getOrigin() {
		return origin;
	}

	public void setOrigin(double origin) {
		this.origin = origin;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
