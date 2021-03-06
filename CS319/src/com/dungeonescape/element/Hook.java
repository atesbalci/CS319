package com.dungeonescape.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import com.dungeonescape.tool.Rope;

public class Hook extends GameElement {
	private boolean grappled;
	private Rope source;

	public Hook(double x, double y, double angle, double speed, Rope s) {
		super(x, y);
		setFlying(true);
		setSmooth(false);
		grappled = false;
		setFricted(false);
		verticalSpeed = Math.sin(angle) * speed;
		horizontalSpeed = Math.cos(angle) * speed;
		source = s;
		width = 5;
		height = 5;
	}

	public Hook() {
		this(0, 0, 0, 0, null);
		setActive(false);
	}

	@Override
	public boolean intersects(GameElement e) {
		if (e instanceof StaticElement) {
			if (super.intersects(e)) {
				horizontalSpeed = 0;
				verticalSpeed = 0;
				grappled = true;
			}
		}
		return false;
	}

	public void contact(String direction, GameElement e) {
	}

	public void moveX(double xChange) {
		super.moveX(xChange);
		if (Math.hypot(getCenter().x - source.getX(),
				getCenter().y - source.getY()) >= source.getReach()
				&& isActive()) {
			setActive(false);
		}
	}

	public void moveY(double yChange) {
		super.moveY(yChange);
		if (Math.hypot(getCenter().x - source.getX(),
				getCenter().y - source.getY()) >= source.getReach()
				&& isActive()) {
			setActive(false);
		}
	}

	public Point getCenter() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}

	public void obstruction(String direction, GameElement e) {
		if (e instanceof Obstacle) {
			horizontalSpeed = 0;
			verticalSpeed = 0;
			grappled = true;
		}
	}

	public void draw(Graphics g, Point camera) {
		if (source != null) {
			Graphics2D g2 = (Graphics2D) g;
			Color prevColor = g2.getColor();
			g2.setColor(Color.red);
			Stroke s = g2.getStroke();
			g2.setStroke(new BasicStroke(3));
			g2.drawLine((int) x + width / 2  - camera.x, (int) y + height / 2 - camera.y,
					(int) source.getX() - camera.x, (int) source.getY() - camera.y);
			g2.setStroke(s);
			g2.setColor(prevColor);
		}

		g.fillRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	public boolean isGrappled() {
		return grappled;
	}

	public void setGrappled(boolean grappled) {
		this.grappled = grappled;
	}

	public Rope getSource() {
		return source;
	}

	public void setSource(Rope source) {
		this.source = source;
	}
}
