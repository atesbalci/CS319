package com.dungeonescape.gameio;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.dungeonescape.game.Game;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -7233967302635631295L;

	private Game game;
	private Point mousePosition;
	private Point cameraPosition;

	public GamePanel() {
		GameMouse mouse = new GameMouse();
		setLayout(null);
		setFocusable(true);
		addKeyListener(new GameKey());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		mousePosition = new Point(this.getSize().width / 2,
				this.getSize().height / 2);
		cameraPosition = new Point(0, 0);
	}

	public void setWorld(Game w) {
		game = w;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		refreshCameraPosition();
		game.paint(g, cameraPosition);
	}

	public void refreshCameraPosition() {
		int width = getSize().width;
		int height = getSize().height;
		cameraPosition.x = ((mousePosition.x + cameraPosition.x + 2 * (game
				.getPlayerPosition().x)) / 3) - (width / 2);
		cameraPosition.y = ((mousePosition.y + cameraPosition.y + 2 * (game
				.getPlayerPosition().y)) / 3) - (height / 2);
	}

	public class GameKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				game.right(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				game.left(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				game.jump(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_E) {
				game.use();
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if(game.isStopped())
					game.start();
				else
					game.stop();
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				game.right(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				game.left(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				game.jump(false);
			}
		}
	}

	public class GameMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			game.useTool(e.getX() + cameraPosition.x, e.getY()
					+ cameraPosition.y);
		}

		public void mouseMoved(MouseEvent e) {
			mousePosition.x = (e.getX());
			mousePosition.y = (e.getY());
		}
	}
}
