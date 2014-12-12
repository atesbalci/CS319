package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;

public class LinearTrigger extends ContactTrigger {
	private int xEnd, yEnd;

	public LinearTrigger(double x, double y, int xEnd, int yEnd) {
		super(x, y, 0, 0);
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}
	
	@Override
	public Line2D getLine() {
		return new Line2D.Double(x, y, xEnd, yEnd);
	}
	
	@Override
	public void draw(Graphics g, Point camera) {
		super.draw(g, camera);
		g.drawLine((int)x - camera.x, (int)y - camera.y, xEnd - camera.x, yEnd - camera.y);
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}
}
