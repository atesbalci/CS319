package com.thegame.element;
import java.awt.Graphics;
import java.awt.geom.*;

public abstract class StaticElement extends GameElement {
	public StaticElement() {
		super(0, 0);
		setFlying(true);
		setMaxHealth(0);
	}
	
	abstract public void draw(Graphics g);
	
	abstract public Rectangle2D.Double getRectangle();
	
	public void obstruction(String direction, GameElement e) {
	}
}
