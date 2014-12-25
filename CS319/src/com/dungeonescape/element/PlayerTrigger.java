package com.dungeonescape.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import com.dungeonescape.common.TriggerConstants;
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
		Font f = g.getFont();
		Color c = g.getColor();
		g.setColor(Color.white);
		g.setFont(new Font("Calibri", Font.PLAIN, 16));
		int type = 0;
		if(getTriggerable() instanceof Game.CheckPoint)
			type = TriggerConstants.CHECKPOINT;
		else if (getTriggerable() instanceof Game.GameEnder)
			type = TriggerConstants.ENDER;
		else if (getTriggerable() instanceof SavedTriggerable)
			type = ((SavedTriggerable)getTriggerable()).getGameElementNo();
		
		if (type == TriggerConstants.CHECKPOINT) {
			g.drawString("Checkpoint", (int) x - camera.x, (int) y - camera.y + 15);
			g.setColor(new Color(255, 255, 0, 50));
		} else if (type == TriggerConstants.ENDER) {
			g.drawString("Goal", (int) x - camera.x, (int) y - camera.y + 15);
			g.setColor(new Color(0, 255, 0, 50));
		} else {
			g.setColor(new Color(255, 255, 255, 50));
		}
		g.fillRoundRect((int) x - camera.x, (int) y - camera.y, width, height,
				5, 5);
		g.setColor(c);
		g.setFont(f);
	}
}
