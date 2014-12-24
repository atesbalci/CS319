package com.dungeonescape.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import com.dungeonescape.game.Game;

public class PlayerTrigger extends ContactTrigger {
	public PlayerTrigger(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void contact(int direction, GameElement e) {
		if (e instanceof Player)
			super.contact(direction, e);
	}

	@Override
	public void draw(Graphics g, Point camera) {
		super.draw(g, camera);
		Font f = g.getFont();
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.setFont(new Font("Calibri", Font.ITALIC, 20));
		if(getTriggerable() instanceof Game.CheckPoint) {
			g.drawString("Checkpoint", (int)x - camera.x, (int)y - camera.y);
		} else if(getTriggerable() instanceof Game.GameEnder) {
			g.drawString("Goal", (int)x - camera.x, (int)y - camera.y);
		}
		g.setColor(c);
		g.setFont(f);
	}
}
