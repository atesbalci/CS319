import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class World {
	final int WIDTH = 800;
	final int HEIGHT = 600;
	public static final int DELAY = 16;

	WorldPanel panel;
	ArrayList<GameElement> elements;
	Guy guy;
	double gravity, friction;
	boolean running, paused;
	float interpolation;
	int fps, frameCount;
	long lastFpsTime = 0;

	public World(WorldPanel panel) {
		this.panel = panel;
		elements = new ArrayList<GameElement>();
		addElement(new Obstacle(-5000, HEIGHT, 10000, 1));
		gravity = 2;
		friction = 0.5;
		fps = 60;
		frameCount = 0;
		running = true;
		paused = false;
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
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(g);
		}
		frameCount++;
	}

	public void setGuy(Guy g) {
		elements.remove(guy);
		elements.add(g);
		guy = g;
	}

	public void gameLoop() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		// keep looping round til the game ends
		while (running) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double) OPTIMAL_TIME);

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000) {
				System.out.println("(FPS: " + fps + ")");
				lastFpsTime = 0;
				fps = 0;
			}

			if (delta <= OPTIMAL_TIME) {
				// update the game logic
				update(delta);
				// draw everyting
				panel.repaint();
			}

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in
			// ns.
			try {
				Thread.sleep((long) ((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000));
			} catch (Exception e) {
			}
			;
		}
	}

	public void update(double d) {
		GameElement e, o;
		d = d / 2;

		for (int i = 0; i < elements.size(); i++) {
			e = elements.get(i);
			e.action(d);

			if (!e.flying && e.yvel < 20)
				e.yvel += gravity * d;

			if (e.yvel > 0) {
				for (int k = 0; k < e.yvel * d; k++) {
					e.moveY(1);
					e.ground = false;
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Bottom", o);
						if (e.smooth && o.smooth) {
							e.moveY(-1);
							e.yvel = 0;
							e.ground = true;
							break;
						}
					}
				}
			}
			if (e.yvel < 0) {
				for (int k = 0; k > e.yvel * d; k--) {
					e.moveY(-1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Top", o);
						if (e.smooth && o.smooth) {
							e.moveY(1);
							e.yvel = 0;
							e.jumping = false;
							break;
						}
					}
				}
			}

			if (e.xvel >= 1) {
				for (int k = 0; k < e.xvel * d; k++) {
					e.moveX(1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Left", o);
						if (e.smooth && o.smooth) {
							e.moveX(-1);
							e.xvel = 0;
							break;
						}
					}
				}
				if (e.fricted)
					e.xvel -= friction * d;
			}
			if (e.xvel <= -1) {
				for (int k = 0; k > e.xvel * d; k--) {
					e.moveX(-1);
					o = obstructed(elements.get(i));
					if (o != null) {
						e.obstruction("Right", o);
						if (e.smooth && o.smooth) {
							e.moveX(1);
							e.xvel = 0;
							break;
						}
					}
				}
				if (e.fricted)
					e.xvel += friction * d;
			}

			if (!e.active) {
				elements.remove(i);
				i--;
			}
		}

		if (guy.firing()) {
			if (guy.weapon.isMelee()) {
				Rectangle2D.Double impact = (Rectangle2D.Double) guy.weapon
						.getImpact();
				for (int k = 0; k < elements.size(); k++) {
					if (elements
							.get(k)
							.getRectangle()
							.intersects(impact.x, impact.y, impact.width,
									impact.height)
							&& elements.get(k) != guy)
						elements.get(k).damage(
								guy.weapon.damage * ((double) DELAY)
										/ ((double) 1000));
				}
			}
			if (!guy.weapon.isMelee()) {
				addElement(guy.weapon.getBullet());
			}
		}
	}

	protected GameElement obstructed(GameElement e) {
		Rectangle2D.Double r = e.getRectangle();

		for (int i = 0; i < elements.size(); i++) {
			GameElement o = elements.get(i);

			if (o != e) {
				Rectangle2D.Double or = o.getRectangle();

				if (o instanceof Bullet) {
				} else if (e instanceof Bullet && o == guy) {
				} else if (r.intersects(or.x, or.y, or.width, or.height)) {
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
}
