package com.thegame.element;
import java.awt.*;
import java.awt.geom.*;

public class Enemy extends GameElement {
	final int WIDTH = 20;
	final int HEIGHT = 40;
	
	public Enemy(int x, int y) {
		super(x, y);
	}
	
	public void draw(Graphics g) {
		drawHealth(g, (int)x + WIDTH/2, (int)y);
		g.drawRect((int)x, (int)y, WIDTH, HEIGHT);
	}
	
	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
	}
	
	public void obstruction(String direction, GameElement e) {
	}
}
