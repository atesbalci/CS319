package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class Lever extends Trigger {
	private boolean leverActive;

	public Lever(double x, double y) {
		super(x, y, 10, 10);
		leverActive = false;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		if (leverActive)
			activate();
		super.timestep(d, elementsInWorld);
	}

	@Override
	public void useAction() {
		leverActive = !leverActive;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		super.draw(g, camera);
		g.drawRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	public boolean isLeverActive() {
		return leverActive;
	}

	public void setLeverActive(boolean leverActive) {
		this.leverActive = leverActive;
	}
}
