package com.dungeonescape.world;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Hook;
import com.dungeonescape.element.Obstacle;
import com.dungeonescape.element.Player;
import com.dungeonescape.ui.WorldPanel;

public class World {
	public final int HEIGHT = 600;
	private static final int DELAY = 16;

	private WorldPanel panel;
	private ArrayList<GameElement> elements;
	private Player player;
	protected double gravity, friction;
	private boolean running;
	private long fps;

	public World(WorldPanel panel) {
		this.panel = panel;
		elements = new ArrayList<GameElement>();
		addElement(new Obstacle(-5000, HEIGHT, 10000, 1));
		gravity = 2;
		friction = 0.5;
		running = true;
	}

	public void start() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	public void addElement(GameElement e) {
		elements.add(e);
	}

	public void setGravity(int g) {
		gravity = g;
	}

	public void paint(Graphics g, Point camera) {
		g.drawString("FPS: " + fps, 50, 50);
		for (GameElement e : elements) {
			e.draw(g, camera);
		}
	}

	public void setPlayer(Player p) {
		elements.remove(player);
		elements.add(p);
		player = p;
	}

	public Point getPlayerPosition() {
		return player.getCenter();
	}

	public void gameLoop() {
		final double TARGET_FPS = 60.0;
		final double OPTIMAL_TIME = 1000.0 / TARGET_FPS;
		long previous = System.currentTimeMillis();
		long now;
		long elapsed;
		double accumulator = 0.0;
		long lastFpsTime = 0;
		long fps = (long) TARGET_FPS;

		while (running) {
			now = System.currentTimeMillis();
			elapsed = (now - previous);
			previous = now;
			accumulator += (double) elapsed;

			if (accumulator >= OPTIMAL_TIME) {
				update(1);
				accumulator -= OPTIMAL_TIME;
				panel.updateUI();
				fps++;
			} else if (accumulator < OPTIMAL_TIME * 0.5) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (now - lastFpsTime >= 1000) {
				this.fps = fps;
				lastFpsTime = now;
				fps = 0;
			}
		}
	}

	public void update(double d) {
		d = d / 2;

		for (int i = 0; i < elements.size(); i++) {
			GameElement e = elements.get(i);
			e.timestep(d);

			if (!e.isFixed()) {
				applyFriction(e, d);
				if (!e.isFlying() && e.getVerticalSpeed() < 20)
					e.setVerticalSpeed(e.getVerticalSpeed() + gravity * d);
				e.moveX(e.getHorizontalSpeed() * d);
				e.moveY(e.getVerticalSpeed() * d);

				for (int n = 0; n < elements.size(); n++) {
					GameElement o = elements.get(n);
					if (!(o == e)) {
						if (e.intersects(o)) {
							e.moveY(-e.getVerticalSpeed() * d);
							if (!e.intersects(o)) {
								if (e.getVerticalSpeed() > 0) {
									e.contact("bottom", o);
									o.contact("top", e);
								} else {
									e.contact("top", o);
									o.contact("bottom", e);
								}
								if (o.isFixed()) {
									e.setVerticalSpeed(-e.getVerticalSpeed()
											* e.getElasticity());
								} else {
									double ratio = e.getWeight()
											/ o.getWeight();
									double tmp = e.getVerticalSpeed();
									e.setVerticalSpeed((o.getVerticalSpeed() - e
											.getVerticalSpeed())
											* ratio
											* e.getElasticity());
									o.setVerticalSpeed((tmp - o
											.getVerticalSpeed())
											* (1 / ratio)
											* o.getElasticity());
								}
							} else {
								e.moveY(e.getVerticalSpeed() * d);
							}
						}
						if (e.intersects(o)) {
							e.moveX(-e.getHorizontalSpeed() * d);
							if (!e.intersects(o)) {
								if (e.getHorizontalSpeed() > 0) {
									e.contact("right", o);
									o.contact("left", e);
								} else {
									e.contact("left", o);
									o.contact("right", e);
								}
								if (o.isFixed()) {
									e.setHorizontalSpeed(-e
											.getHorizontalSpeed()
											* e.getElasticity());
								} else {
									double ratio = e.getWeight()
											/ o.getWeight();
									double tmp = e.getHorizontalSpeed();
									e.setHorizontalSpeed((o
											.getHorizontalSpeed() - e
											.getHorizontalSpeed())
											* ratio * e.getElasticity());
									o.setHorizontalSpeed((tmp - o
											.getHorizontalSpeed())
											* (1 / ratio) * o.getElasticity());
								}
							} else {
								e.moveX(e.getHorizontalSpeed() * d);
							}
						}
					}
				}
			}

			if (!e.isActive()) {
				elements.remove(e);
				i--;
			}
		}

		if (player.firing()) {
			if (player.getWeapon().isMelee()) {
				Rectangle2D.Double impact = (Rectangle2D.Double) player
						.getWeapon().getImpact();
				for (int k = 0; k < elements.size(); k++) {
					if (elements
							.get(k)
							.getRectangle()
							.intersects(impact.x, impact.y, impact.width,
									impact.height)
							&& elements.get(k) != player)
						elements.get(k).damage(
								player.getWeapon().getDamage()
										* ((double) DELAY) / ((double) 1000));
				}
			}
			if (!player.getWeapon().isMelee()) {
				addElement(player.getWeapon().getBullet());
			}
		}
	}

	public void applyFriction(GameElement e, double d) {
		if (e.getHorizontalSpeed() > friction)
			e.setHorizontalSpeed(e.getHorizontalSpeed() - friction * d);
		else if (e.getHorizontalSpeed() < -1 * friction)
			e.setHorizontalSpeed(e.getHorizontalSpeed() + friction * d);
		else
			e.setHorizontalSpeed(0);
		if (e.getVerticalSpeed() > friction)
			e.setVerticalSpeed(e.getVerticalSpeed() - friction * d);
		else if (e.getVerticalSpeed() < -1 * friction)
			e.setVerticalSpeed(e.getVerticalSpeed() + friction * d);
		else
			e.setVerticalSpeed(0);
	}

	public void right(boolean b) {
		player.right(b);
	}

	public void left(boolean b) {
		player.left(b);
	}

	public void jump(boolean b) {
		player.jump(b);
	}

	public void fire() {
		player.fire();
	}

	public void hook(int x, int y) {
		Hook h = player.throwHook(x, y);
		if (h != null)
			elements.add(h);
	}

	public Player getPlayer() {
		return player;
	}
}
