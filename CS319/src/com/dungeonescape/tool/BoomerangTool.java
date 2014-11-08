package com.dungeonescape.tool;

import com.dungeonescape.element.Boomerang;
import com.dungeonescape.element.GameElement;

public class BoomerangTool extends Tool {
	private Boomerang boomerang;
	private double speed;

	public BoomerangTool() {
		boomerang = new Boomerang();
		speed = 10;
	}

	@Override
	public void timestep(double d) {
		if (boomerang.isActive() && boomerang.isReturning()) {
			double angle = Math.atan((double) (y - boomerang.getY())
					/ (double) (x - boomerang.getX()));
			if (x - boomerang.getX() < 0)
				angle = Math.PI + angle;
			boomerang.setVerticalSpeed(Math.sin(angle) * speed);
			boomerang.setHorizontalSpeed(Math.cos(angle) * speed);
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
		} else {
			boomerang.setActive(false);
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
