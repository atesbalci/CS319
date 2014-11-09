package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.dungeonescape.tool.BoomerangTool;

public class Boomerang extends GameElement {
	private BoomerangTool thrower;
	private double speed;
	private boolean returning;
	private int range;
	private double angle;
	private BufferedImage image;

	public Boomerang(double x, double y, double angle, double speed,
			BoomerangTool s) {
		super(x, y);
		setFlying(true);
		setFricted(false);
		verticalSpeed = Math.sin(angle) * speed;
		horizontalSpeed = Math.cos(angle) * speed;
		width = 15;
		height = 15;
		returning = false;
		thrower = s;
		range = 200;
		this.angle = angle;
		try {
			image = ImageIO.read(new File("img/boomerang.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Boomerang() {
		this(0, 0, 0, 0, null);
		setActive(false);
	}

	@Override
	public void contact(String direction, GameElement e) {
		super.contact(direction, e);
		if (e != thrower.getOwner())
			returning = true;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		double distance = Math.hypot(getCenter().x - thrower.getX(),
				getCenter().y - thrower.getY());
		if (distance >= range) {
			returning = true;
		} else if (distance < width && returning) {
			setActive(false);
		}
	}

	@Override
	public boolean intersects(GameElement e) {
		if (returning)
			return false;
		boolean intersects = super.intersects(e);
		return intersects;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		g.drawImage(image, (int) x - camera.x, (int) y - camera.y, null);
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

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
