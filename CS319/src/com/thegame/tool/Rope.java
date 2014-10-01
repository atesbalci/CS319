package com.thegame.tool;
import com.thegame.element.GameElement;
import com.thegame.element.Hook;


public class Rope {
	private final double PULL_SPEED = 20;
	
	private GameElement source;
	private int x, y, reach;
	private double speed;
	private Hook hook;
	
	public Rope(int x, int y, GameElement source) {
		this.source = source;
		this.x = x;
		this.y = y;
		speed = 20;
		reach = 300;
		hook = new Hook();
	}
	
	public Hook fire(int xdest, int ydest) {
		if(!hook.isActive()) {
			double angle = Math.atan((double)(y-ydest) / (double)(x-xdest));
			if(x-xdest < 0)
				hook = new Hook(x, y, angle, speed, this);
			else {
				angle = Math.PI + angle;
				hook = new Hook(x, y, angle, speed, this);
			}
			System.out.println(angle);
			return hook;
		}
		else {
			hook.setActive(false);
		}
		return null;
	}

	public GameElement getSource() {
		return source;
	}

	public void setSource(GameElement source) {
		this.source = source;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getReach() {
		return reach;
	}

	public void setReach(int reach) {
		this.reach = reach;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Hook getHook() {
		return hook;
	}

	public void setHook(Hook hook) {
		this.hook = hook;
	}
	
	public double getPull() {
		return PULL_SPEED;
	}
}
