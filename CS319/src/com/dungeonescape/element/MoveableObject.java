package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;

import com.dungeonescape.common.Images;

public class MoveableObject extends GameElement {
	final int WIDTH = 20;
	final int HEIGHT = 40;

	public MoveableObject(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g, Point camera) {
		g.drawImage(Images.MOVEABLE_OBJECT, (int) x - camera.x, (int) y
				- camera.y, null);
	}

	public void obstruction(String direction, GameElement e) {
	}
}
