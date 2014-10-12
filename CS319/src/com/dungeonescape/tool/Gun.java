package com.dungeonescape.tool;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.Timer;

import com.dungeonescape.element.Bullet;

public class Gun extends Weapon {
	private int rate;
	private boolean overheat;
	private Timer timer;
	
	public Gun() {
		super(false);
		rate = 250;
		overheat = false;
		timer = new Timer(rate, new Overheat());
		timer.setRepeats(false);
		setDamage(10);
	}

	public Rectangle2D.Double getImpact() {
		return null;
	}
	
	public Bullet getBullet() {
		setFiring(false);
		overheat = true;
		timer.start();
		return new Bullet(getX(), getY(), getDirection(), getDamage());
	}
	
	public void draw(Graphics g) {
//		if(direction)
//			g.drawRect(x, y, 50, 10);
//		else
//			g.drawRect(x - 50, y, 50, 10);
	}

	public void fire() {
		if(!overheat)
			setFiring(true);
	}
	
	public class Overheat implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			overheat = false;
		}
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isOverheat() {
		return overheat;
	}

	public void setOverheat(boolean overheat) {
		this.overheat = overheat;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
