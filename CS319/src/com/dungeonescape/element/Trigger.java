package com.dungeonescape.element;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.List;

public class Trigger extends StaticElement implements Triggerable {
	private Triggerable triggerable;
	private double triggerActive;
	private int triggerDuration;
	private int delay;
	private double remainingDelay;

	public Trigger(double x, double y) {
		super(x, y);
		smooth = false;
		triggerActive = 0;
		triggerDuration = 1;
		delay = 0;
		remainingDelay = 0;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		super.timestep(d, elementsInWorld);
		if (triggerable != null) {
			if (triggerActive > 0) {
				if (remainingDelay <= 0) {
					triggerable.trigger(true, this);
					triggerActive -= d;
				} else {
					remainingDelay -= d;
				}
			} else {
				triggerable.trigger(false, this);
				remainingDelay = delay;
			}
		}
	}

	public void activate() {
		triggerActive = triggerDuration;
	}

	public void trigger(boolean b, Trigger t) {
		if (b)
			activate();
	}

	public Triggerable getTriggerable() {
		return triggerable;
	}

	public void setTriggerable(Triggerable triggerable) {
		this.triggerable = triggerable;
	}

	public int getTriggerDuration() {
		return triggerDuration;
	}

	public void setTriggerDuration(int triggerDuration) {
		this.triggerDuration = triggerDuration;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
		remainingDelay = delay;
	}

	public boolean hasTriggerable() {
		return triggerable != null;
	}

	@Override
	public void draw(Graphics g, Point camera) {
		if (getTriggerable() instanceof GameElement) {
			Graphics2D g2 = ((Graphics2D) g);
			Color prevColor = g2.getColor();
			Stroke prevStroke = g2.getStroke();
			Point c = getCenter();
			c.x -= camera.x;
			c.y -= camera.y;
			GameElement triggerable = ((GameElement) getTriggerable());
			while (triggerable instanceof Trigger) {
				if (((Trigger) triggerable).getTriggerable() instanceof GameElement)
					triggerable = (GameElement) ((Trigger) triggerable)
							.getTriggerable();
				else
					break;
			}
			Point t = triggerable.getCenter();
			t.x -= camera.x;
			t.y -= camera.y;
			Line2D connection = new Line2D.Double(c, t);
			g2.setColor(Color.green);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					0.2f));
			g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL));
			g2.draw(connection);
			g2.setColor(Color.yellow);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					1));
			float dash1[] = {10.0f, 5.0f, 3.0f};
			g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			g2.draw(connection);
			g2.setColor(prevColor);
			g2.setStroke(prevStroke);
		}
	}
}
