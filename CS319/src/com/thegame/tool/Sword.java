package com.thegame.tool;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

import com.thegame.element.Bullet;

public class Sword extends Weapon {
	private Timer duration;
	
	public Sword() {
		super(true);
		duration = new Timer(1000, new FireAction());
		duration.setRepeats(false);
		setDamage(20);
	}
	
	public void draw(Graphics g) {
		if(isFiring()) {
			if(getDirection())
				g.fillRect(getX(), getY(), 20, 5);
			else
				g.fillRect(getX() - 20, getY(), 20, 5);
		}
	}
	
	public Rectangle2D.Double getImpact() {
		if(getDirection())
			return new Rectangle2D.Double(getX(), getY(), 20, 5);
		return new Rectangle2D.Double(getX() - 20, getY(), 20, 5);
	}
	
	public Bullet getBullet() {
		return null;
	}

	public void fire() {
		setFiring(true);
		duration.start();
	}
	
	private class FireAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setFiring(false);
		}
	}
}
