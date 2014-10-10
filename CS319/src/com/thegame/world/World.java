package com.thegame.world;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.thegame.element.Bullet;
import com.thegame.element.GameElement;
import com.thegame.element.Guy;
import com.thegame.element.Hook;
import com.thegame.element.Obstacle;
import com.thegame.ui.WorldPanel;

public class World {
	public final int HEIGHT = 600;
	private static final int DELAY = 16;

	private WorldPanel panel;
	private ArrayList<GameElement> elements;
	private Guy guy;
	private double gravity, friction;
	private boolean running;

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

	public void paint(Graphics g) {
		for (GameElement e : elements) {
			e.draw(g);
		}
	}

	public void setGuy(Guy g) {
		elements.remove(guy);
		elements.add(g);
		guy = g;
	}

	public void gameLoop() {
		final double TARGET_FPS = 60.0;
		final double OPTIMAL_TIME = 1000 / TARGET_FPS;
		long previous = System.currentTimeMillis();
		long now;
		long elapsed;
		double accumulator = 0.0;
		long lastFpsTime = 0;
		long fps = 0;

		while (running) {
			now = System.currentTimeMillis();
			elapsed = (now - previous);
			previous = now;
			accumulator += elapsed;

			if (accumulator >= OPTIMAL_TIME) {
				update(1);
				accumulator -= OPTIMAL_TIME;
				panel.updateUI();
				fps++;
			} else if (accumulator < OPTIMAL_TIME * 0.7) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (now - lastFpsTime >= 1000) {
				System.out.println("(FPS: " + fps + ")");
				lastFpsTime = now;
				fps = 0;
			}
		}
	}

	public void update(double d) {
		GameElement e, o;
		d = d / 2;

		for (int i = 0; i < elements.size(); i++) {
			e = elements.get(i);
			e.action(d);

			if (!e.isFlying() && e.getYvel() < 20)
				e.setYvel(e.getYvel() + gravity * d);

			if (e.getYvel() > 0) {
				for (int k = 0; k < e.getYvel() * d; k++) {
					e.moveY(1);
					e.setGround(false);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Bottom", o);
						if (e.isSmooth() && o.isSmooth()) {
							e.moveY(-1);
							e.setYvel(0);
							e.setGround(true);
							break;
						}
					}
				}
			}
			if (e.getYvel() < 0) {
				for (int k = 0; k > e.getYvel() * d; k--) {
					e.moveY(-1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Top", o);
						if (e.isSmooth() && o.isSmooth()) {
							e.moveY(1);
							e.setYvel(0);
							e.setJumping(false);
							break;
						}
					}
				}
			}

			if (e.getXvel() >= 1) {
				for (int k = 0; k < e.getXvel() * d; k++) {
					e.moveX(1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Left", o);
						if (e.isSmooth() && o.isSmooth()) {
							e.moveX(-1);
							e.setXvel(0);
							break;
						}
					}
				}
				if (e.isFricted())
					e.setXvel(e.getXvel() - friction * d);
			}
			if (e.getXvel() <= -1) {
				for (int k = 0; k > e.getXvel() * d; k--) {
					e.moveX(-1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Right", o);
						if (e.isSmooth() && o.isSmooth()) {
							e.moveX(1);
							e.setXvel(0);
							break;
						}
					}
				}
				if (e.isFricted())
					e.setXvel(e.getXvel() + friction * d);
			}

			if (!e.isActive()) {
				elements.remove(i);
				i--;
			}
		}

		if (guy.firing()) {
			if (guy.getWeapon().isMelee()) {
				Rectangle2D.Double impact = (Rectangle2D.Double) guy
						.getWeapon().getImpact();
				for (int k = 0; k < elements.size(); k++) {
					if (elements
							.get(k)
							.getRectangle()
							.intersects(impact.x, impact.y, impact.width,
									impact.height)
							&& elements.get(k) != guy)
						elements.get(k).damage(
								guy.getWeapon().getDamage() * ((double) DELAY)
										/ ((double) 1000));
				}
			}
			if (!guy.getWeapon().isMelee()) {
				addElement(guy.getWeapon().getBullet());
			}
		}
	}

	protected GameElement obstructed(GameElement e) {
		Rectangle2D r = e.getRectangle();

		for (int i = 0; i < elements.size(); i++) {
			GameElement o = elements.get(i);

			if (o != e) {
				Rectangle2D or = o.getRectangle();

				if (o instanceof Bullet) {
				} else if (e instanceof Bullet && o == guy) {
				} else if (r.intersects(or.getX(), or.getY(), or.getWidth(),
						or.getHeight())) {
					return o;
				}
			}
		}
		return null;
	}

	public void right(boolean b) {
		guy.right(b);
	}

	public void left(boolean b) {
		guy.left(b);
	}

	public void jump(boolean b) {
		guy.jump(b);
	}

	public void fire() {
		guy.fire();
	}

	public void hook(int x, int y) {
		Hook h = guy.throwHook(x, y);
		if (h != null)
			elements.add(h);
	}

	public Guy getPlayer() {
		return guy;
	}
}
