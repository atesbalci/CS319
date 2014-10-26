package com.dungeonescape.element;
import java.awt.Graphics;
import java.awt.Point;

public class Obstacle extends StaticElement {
	public Obstacle(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public void draw(Graphics g, Point camera) {
		g.fillRect((int)x - camera.x, (int)y - camera.y, width, height);
	}
}
