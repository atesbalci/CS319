package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;

public class Trigger extends StaticElement {
	private Triggerable triggerable;
	private double triggerActive;
	private int triggerDuration;

	public Trigger(double x, double y) {
		super(x, y);
		smooth = false;
		triggerActive = 0;
		triggerDuration = 1;
	}

	@Override
	public void timestep(double d) {
		super.timestep(d);
		if (triggerActive > 0 && triggerable != null) {
			triggerable.trigger(true);
		} else {
			triggerable.trigger(false);
		}
		if (triggerActive > 0)
			triggerActive -= d;
	}

	@Override
	public void contact(String direction, GameElement e) {
		triggerActive = triggerDuration;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		g.drawRect((int) x - camera.x, (int) y - camera.y, width, height);
	}

	public Triggerable getTriggerable() {
		return triggerable;
	}

	public void setTriggerable(Triggerable triggerable) {
		this.triggerable = triggerable;
	}

	public int getTriggerDuration() {
		return triggerDuration;
	}

	public void setTriggerDuration(int triggerDuration) {
		this.triggerDuration = triggerDuration;
	}

}
