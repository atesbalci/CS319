package com.dungeonescape.tool;

import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Hook;

public class Rope {
	private GameElement source;
	private double x, y, reach;
	private double speed, pullSpeed;
	private Hook hook;

	public Rope(int x, int y, GameElement source) {
		this.source = source;
		this.x = x;
		this.y = y;
		speed = 20;
		reach = 300;
		hook = new Hook();
		pullSpeed = 20;
	}

	public Hook fire(int xdest, int ydest) {
		if (!hook.isActive()) {
			double angle = Math.atan((double) (y - ydest)
					/ (double) (x - xdest));
			if (x - xdest < 0)
				hook = new Hook(x, y, angle, speed, this);
			else {
				angle = Math.PI + angle;
				hook = new Hook(x, y, angle, speed, this);
			}
			System.out.println(angle);
			return hook;
		} else {
			hook.setActive(false);
		}
		return null;
	}

	public GameElement getSource() {
		return source;
	}

	public void setSource(GameElement source) {
		this.source = source;
	}

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

	public double getReach() {
		return reach;
	}

	public void setReach(double reach) {
		this.reach = reach;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Hook getHook() {
		return hook;
	}

	public void setHook(Hook hook) {
		this.hook = hook;
	}

	public double getPullSpeed() {
		return pullSpeed;
	}

	public void setPullSpeed(double pullSpeed) {
		this.pullSpeed = pullSpeed;
	}
}
