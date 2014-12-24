package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

import com.dungeonescape.common.Images;

public class Obstacle extends StaticElement {
	public Obstacle(int x, int y, int w, int h) {
		super(x, y);
		width = w;
		height = h;
	}

	public void draw(Graphics g, Point camera) {
		Graphics2D g2 = (Graphics2D) g;
		TexturePaint tp = new TexturePaint(
				Images.OBSTACLE,
				new Rectangle2D.Double(getX() - camera.x, getY() -camera.y,
						Images.OBSTACLE.getWidth(), Images.OBSTACLE.getHeight()));
		Paint prevPaint = g2.getPaint();
		g2.setPaint(tp);
		g2.fillRoundRect((int) x - camera.x, (int) y - camera.y, width, height, 5, 5);
		g2.setPaint(prevPaint);
	}
}
