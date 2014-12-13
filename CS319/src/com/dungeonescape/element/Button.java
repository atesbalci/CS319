package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;

public class Button extends Trigger {
	public Button(double x, double y) {
		super(x, y);
		setWidth(10);
		setHeight(10);
	}

	@Override
	public void useAction() {
		activate();
	}

	@Override
	public void draw(Graphics g, Point camera) {
		super.draw(g, camera);
		g.drawRect((int) x - camera.x, (int) y - camera.y, width, height);
	}
}
