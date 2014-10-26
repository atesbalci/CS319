package com.dungeonescape.element;

public abstract class StaticElement extends GameElement {
	public StaticElement() {
		super(0, 0);
		setFlying(true);
		setMaxHealth(0);
		setFixed(true);
	}
	
	public void obstruction(String direction, GameElement e) {
	}
}
