package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;

public class ContactTrigger extends Trigger {
	public ContactTrigger(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void contact(int direction, GameElement e) {
		if (getTriggerable() != e) {
			activate();
		}
	}
	
	@Override
	public void draw(Graphics g, Point camera) {
		super.draw(g, camera);
		g.drawRect((int) x - camera.x, (int) y - camera.y, width, height);
	}
}
