package com.dungeonescape.tool;

import com.dungeonescape.element.Boomerang;
import com.dungeonescape.element.GameElement;

public class BoomerangTool extends Tool {
	private Boomerang boomerang;
	private double speed;

	public BoomerangTool() {
		boomerang = new Boomerang();
		speed = 20;
	}

	@Override
	public void timestep(double d) {
		if (boomerang.isActive()) {
			double speedRatio = Math.abs(1
					- Math.hypot(x - boomerang.getX(), y - boomerang.getY())
					/ boomerang.getRange());
			if (speedRatio < 0.3)
				speedRatio = 0.3;
			if (boomerang.isReturning()) {
				double angle = Math.atan((double) (y - boomerang.getY())
						/ (double) (x - boomerang.getX()));
				if (x - boomerang.getX() < 0)
					angle = Math.PI + angle;
				boomerang
						.setVerticalSpeed(Math.sin(angle) * speed * speedRatio);
				boomerang.setHorizontalSpeed(Math.cos(angle) * speed
						* speedRatio);
			} else {
				boomerang.setVerticalSpeed(Math.sin(boomerang.getAngle())
						* speed * speedRatio);
				boomerang.setHorizontalSpeed(Math.cos(boomerang.getAngle())
						* speed * speedRatio);
			}
		}

	}

	@Override
	public GameElement use(int xDest, int yDest) {
		if (!boomerang.isActive()) {
			double angle = Math.atan((double) (y - yDest)
					/ (double) (x - xDest));
			if (x - xDest < 0)
				boomerang = new Boomerang(x, y, angle, speed, this);
			else {
				angle = Math.PI + angle;
				boomerang = new Boomerang(x, y, angle, speed, this);
			}
			System.out.println(angle);
			return boomerang;
		}
		return null;
	}

	public Boomerang getBoomerang() {
		return boomerang;
	}

	public void setBoomerang(Boomerang boomerang) {
		this.boomerang = boomerang;
	}
}
