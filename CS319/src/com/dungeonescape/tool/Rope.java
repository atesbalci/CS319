package com.dungeonescape.tool;

import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Hook;

public class Rope extends Tool {
	private double reach;
	private double speed, pullSpeed;
	private Hook hook;

	public Rope() {
		speed = 20;
		reach = 300;
		hook = new Hook();
		pullSpeed = 20;
	}

	@Override
	public GameElement use(int xDest, int yDest) {
		if (!hook.isActive()) {
			double angle = Math.atan((double) (y - yDest)
					/ (double) (x - xDest));
			if (x - xDest < 0)
				hook = new Hook(x, y, angle, speed, this);
			else {
				angle = Math.PI + angle;
				hook = new Hook(x, y, angle, speed, this);
			}
			return hook;
		} else {
			hook.setActive(false);
		}
		return null;
	}

	@Override
	public void timestep(double d) {
		if (hook.isActive() && hook.isGrappled()) {
			double angle = Math.atan((double) (hook.getY() - getY())
					/ (double) (hook.getX() - getX()));
			if (hook.getX() - getX() <= 0)
				angle = Math.PI + angle;
			owner.setHorizontalSpeed(Math.cos(angle) * getPullSpeed());
			owner.setVerticalSpeed(Math.sin(angle) * getPullSpeed());
		}
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
