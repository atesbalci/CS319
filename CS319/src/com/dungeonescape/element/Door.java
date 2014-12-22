package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.dungeonescape.common.Images;

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
		closed = true;
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
		Graphics2D g2 = (Graphics2D) g;
		TexturePaint tp = new TexturePaint(Images.DOOR, new Rectangle2D.Double(
				getX() - camera.x, getY() - camera.y
						- (maxHeight - (int) doorOpening),
				Images.DOOR.getWidth(), Images.DOOR.getHeight()));
		Paint prevPaint = g2.getPaint();
		g2.setPaint(tp);
		g.fillRect((int) x - camera.x, (int) y - camera.y, width, height);
		g2.setPaint(prevPaint);
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
	public void trigger(boolean b, Trigger t) {
		if (b) {
			closed = false;
		} else {
			closed = true;
		}
	}

}
