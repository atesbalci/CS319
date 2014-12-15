package com.dungeonescape.game;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.util.List;

import com.dungeonescape.common.ToolConstants;
import com.dungeonescape.common.TriggerConstants;
import com.dungeonescape.element.GameElement;
import com.dungeonescape.element.Player;
import com.dungeonescape.element.SavedTriggerable;
import com.dungeonescape.element.Trigger;
import com.dungeonescape.element.Triggerable;
import com.dungeonescape.gameio.GamePanel;
import com.dungeonescape.tool.BoomerangTool;
import com.dungeonescape.tool.Rope;

public class Game {
	private GamePanel panel;
	private Level level;
	private PhysicsEngine physicsEngine;
	private Player player;
	private double gravity, friction;
	private boolean stopped;
	private int fps;
	private Thread gameThread;
	private GameEnder gameEnder;
	private CheckPoint checkPoint;

	public Game(GamePanel panel) {
		this.panel = panel;
		physicsEngine = new PhysicsEngine();
		setLevel(new Level());
		loadLevel(null);
		gravity = 2;
		friction = 0.5;
		stopped = true;
		setGameEnder(new GameEnder());
		setCheckPoint(new CheckPoint());
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
		g.drawString(player.getX() + ", " + player.getY(), 50, 75);
		g.drawString(level.getSpawnPoint().x + ", " + level.getSpawnPoint().y,
				50, 100);
		for (GameElement e : level.getElements()) {
			e.draw(g, camera);
		}
		player.draw(g, camera);
	}

	public void setPlayer(Player p) {
		level.removeElement(player);
		level.addElement(p);
		player = p;
	}

	public Point getPlayerPosition() {
		return player.getCenter();
	}

	private void gameLoop() {
		final double TARGET_FPS = 60.0;
		final double OPTIMAL_TIME = 1000000000.0 / TARGET_FPS;
		long previous = System.nanoTime();
		long now;
		long elapsed;
		double accumulator = 0.0;
		long lastFpsTime = 0;
		int fps = (int) TARGET_FPS;

		while (!stopped) {
			now = System.nanoTime();
			elapsed = (now - previous);
			previous = now;
			accumulator += (double) elapsed;

			if (accumulator >= OPTIMAL_TIME) {
				physicsEngine.timestep(1, level.getElements(), gravity,
						friction);
				if (player.getY() > level.getFallHeight())
					gameEnder.trigger(true, null);
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

			if (now - lastFpsTime >= 1000000000) {
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

	public void use() {
		player.use();
	}

	public void useTool(int x, int y) {
		GameElement e = player.useTool(x, y);
		if (e != null)
			level.addElement(e);
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void loadLevel(File file) {
		if (file != null)
			level.loadLevel(file);
		List<GameElement> elements = level.getElements();
		for (GameElement e : elements) {
			if (e instanceof Trigger) {
				Trigger t = (Trigger) e;
				if (t.getTriggerable() instanceof SavedTriggerable) {
					SavedTriggerable st = (SavedTriggerable) t.getTriggerable();
					if (st.getGameElementNo() >= 0)
						t.setTriggerable((Triggerable) elements.get(st
								.getGameElementNo()));
					else if (st.getGameElementNo() == TriggerConstants.CHECKPOINT)
						t.setTriggerable(checkPoint);
					else if (st.getGameElementNo() == TriggerConstants.ENDER)
						t.setTriggerable(gameEnder);
				}
			}
		}
		player = new Player(level.getSpawnPoint().x, level.getSpawnPoint().y);
		if (level.getTool() == ToolConstants.BOOMERANG)
			player.setTool(new BoomerangTool());
		else if (level.getTool() == ToolConstants.ROPE)
			player.setTool(new Rope());
		level.addElement(player);
		panel.showTip(level.getTip());
	}

	public GameEnder getGameEnder() {
		return gameEnder;
	}

	public void setGameEnder(GameEnder gameEnder) {
		this.gameEnder = gameEnder;
	}

	public CheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public class GameEnder implements Triggerable {
		@Override
		public void trigger(boolean b, Trigger t) {
			if (b) {
				stop();
				if (t != null)
					panel.end(true);
				else
					panel.end(false);
			}
		}
	}

	public class CheckPoint implements Triggerable {
		@Override
		public void trigger(boolean b, Trigger t) {
			if (b)
				level.setSpawnPoint(new Point((int) t.getX(), (int) t.getY()));
		}
	}
}
