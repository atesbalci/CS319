package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import com.dungeonescape.common.Images;

public class Lever extends Trigger {
	private boolean leverActive;

	public Lever(double x, double y) {
		super(x, y);
		setWidth(10);
		setHeight(20);
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
		if (isTriggerActive())
			g.drawImage(Images.LEVER_ON, (int) x - camera.x,
					(int) y - camera.y, null);
		else
			g.drawImage(Images.LEVER_OFF, (int) x - camera.x, (int) y
					- camera.y, null);
	}

	public boolean isLeverActive() {
		return leverActive;
	}

	public void setLeverActive(boolean leverActive) {
		this.leverActive = leverActive;
	}
}
