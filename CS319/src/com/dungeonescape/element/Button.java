package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;

import com.dungeonescape.common.Images;

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
		if (isTriggerActive())
			g.drawImage(Images.BUTTON_ON, (int) x - camera.x, (int) y
					- camera.y, null);
		else
			g.drawImage(Images.BUTTON_OFF, (int) x - camera.x, (int) y
					- camera.y, null);
	}
}
