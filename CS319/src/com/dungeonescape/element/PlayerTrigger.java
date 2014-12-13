package com.dungeonescape.element;

public class PlayerTrigger extends ContactTrigger {
	public PlayerTrigger(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void contact(int direction, GameElement e) {
		if (e instanceof Player)
			super.contact(direction, e);
	}
}
