package com.dungeonescape.element;
import java.awt.Graphics;

public class Obstacle extends StaticElement {
	public Obstacle(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public void draw(Graphics g) {
		g.fillRect((int)x, (int)y, width, height);
	}
}
