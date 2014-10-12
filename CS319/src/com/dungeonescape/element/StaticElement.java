package com.dungeonescape.element;
import java.awt.Graphics;

public abstract class StaticElement extends GameElement {
	public StaticElement() {
		super(0, 0);
		setFlying(true);
		setMaxHealth(0);
		setFixed(true);
	}
	
	abstract public void draw(Graphics g);
	
	public void obstruction(String direction, GameElement e) {
	}
}
