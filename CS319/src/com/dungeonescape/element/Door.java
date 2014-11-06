package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Door extends StaticElement implements Triggerable {
	private double doorOpening;
	private int maxHeight;
	private boolean closed;
	private double speed;

	public Door(double x, double y) {
		super(x, y);
		width = 10;
		height = 50;
		doorOpening = height;
		maxHeight = height;
		speed = 8;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		super.timestep(d, elementsInWorld);
		if (closed) {
			if (doorOpening < maxHeight)
				doorOpening += speed * d;
			if (doorOpening > maxHeight)
				doorOpening = maxHeight;
		} else {
			if (doorOpening > 0)
				doorOpening -= speed * d;
			if (doorOpening < 0)
				doorOpening = 0;
		}

		height = (int) doorOpening;
	}

	@Override
	public void setHeight(int height) {
		maxHeight = height;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		g.fillRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public void toggleClosed() {
		closed = !closed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void trigger(boolean b) {
		if(b) {
			closed = false;
		} else {
			closed = true;
		}
	}

}
