package com.thegame.element;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import com.thegame.tool.Rope;

public class Hook extends GameElement {
	final int WIDTH = 5;
	final int HEIGHT = 5;
	
	boolean grappled;
	double angle;
	Rope source;
	
	public Hook(int x, int y, double angle, double speed, Rope s) {
		super(x, y);
		setFlying(true);
		setSmooth(false);
		grappled = false;
		setFricted(false);
		yvel = Math.sin(angle) * speed;
		xvel = Math.cos(angle) * speed;
		this.angle = angle;
		source = s;
	}
	
	public Hook() {
		this(0, 0, 0, 0, null);
		setActive(false);
	}
	
	public void moveX(int xChange) {
		super.moveX(xChange);
		if(Math.hypot(getCenter().x - source.getX(), getCenter().y - source.getY()) >= source.getReach() && isActive()) {
			setActive(false);
		}
	}
	public void moveY(int yChange) {
		super.moveY(yChange);
		if(Math.hypot(getCenter().x - source.getX(), getCenter().y - source.getY()) >= source.getReach() && isActive()) {
			setActive(false);
		}
	}
	
	public Point getCenter() {
		return new Point((int)x + WIDTH/2, (int)y + HEIGHT/2);
	}
	
	public void obstruction(String direction, GameElement e) {
		if(e instanceof Obstacle) {
			xvel = 0;
			yvel = 0;
			grappled = true;
		}
	}
	
	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
	}
	
	public void draw(Graphics g) {
		if(source != null) {
			Graphics2D g2 = (Graphics2D)g;		
			Stroke s = g2.getStroke();
			g2.setStroke(new BasicStroke(3));
			g2.drawLine((int)x + WIDTH/2, (int)y + HEIGHT/2, (int)source.getX(), (int)source.getY());
			g2.setStroke(s);
		}
		
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
	}
}
