package com.dungeonescape.game;

import java.awt.Graphics;
import java.awt.Point;

import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Player;
import com.dungeonescape.ui.GamePanel;

public class Game {
	private GamePanel panel;
	private Level map;
	private PhysicsEngine physicsEngine;
	private Player player;
	private double gravity, friction;
	private boolean stopped;
	private int fps;
	private Thread gameThread;

	public Game(GamePanel panel) {
		this.panel = panel;
		physicsEngine = new PhysicsEngine();
		map = new Level();
		gravity = 2;
		friction = 0.5;
		stopped = true;
		player = new Player(300, 300);
		map.addElement(player);
	}

	public void start() {
		stopped = false;
		gameThread = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		gameThread.start();
	}

	public void stop() {
		stopped = true;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setGravity(int g) {
		gravity = g;
	}

	public void paint(Graphics g, Point camera) {
		g.drawString("FPS: " + fps, 50, 50);
		for (GameElement e : map.getElements()) {
			e.draw(g, camera);
		}
		player.draw(g, camera);
	}

	public void setPlayer(Player p) {
		map.removeElement(player);
		map.addElement(p);
		player = p;
	}

	public Point getPlayerPosition() {
		return player.getCenter();
	}

	private void gameLoop() {
		final double TARGET_FPS = 60.0;
		final double OPTIMAL_TIME = 1000.0 / TARGET_FPS;
		long previous = System.currentTimeMillis();
		long now;
		long elapsed;
		double accumulator = 0.0;
		long lastFpsTime = 0;
		int fps = (int) TARGET_FPS;

		while (!stopped) {
			now = System.currentTimeMillis();
			elapsed = (now - previous);
			previous = now;
			accumulator += (double) elapsed;

			if (accumulator >= OPTIMAL_TIME) {
				physicsEngine.timestep(1, map.getElements(), gravity, friction);
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

	public boolean isStopped() {
		return stopped;
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

	public void useTool(int x, int y) {
		GameElement e = player.useTool(x, y);
		if (e != null)
			map.addElement(e);
	}

	public Player getPlayer() {
		return player;
	}

	public PhysicsEngine getEngine() {
		return physicsEngine;
	}

	public void setEngine(PhysicsEngine engine) {
		this.physicsEngine = engine;
	}

	public Level getMap() {
		return map;
	}

	public void setMap(Level map) {
		this.map = map;
	}
}
