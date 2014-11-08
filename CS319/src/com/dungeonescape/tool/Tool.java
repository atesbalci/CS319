package com.dungeonescape.tool;

import com.dungeonescape.element.GameElement;

public abstract class Tool {
	protected double x, y;
	protected GameElement owner;
	
	public Tool() {
	}
	
	public GameElement use(int xDest, int yDest) {
		return null;
	}
	
	public void timestep(double d) {
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public GameElement getOwner() {
		return owner;
	}

	public void setOwner(GameElement owner) {
		this.owner = owner;
	}
}
