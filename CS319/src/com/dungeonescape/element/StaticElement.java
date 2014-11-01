package com.dungeonescape.element;

public abstract class StaticElement extends GameElement {
	public StaticElement(double x, double y) {
		super(x, y);
		setFlying(true);
		setMaxHealth(0);
		setFixed(true);
	}
	
	public void obstruction(String direction, GameElement e) {
	}
}
