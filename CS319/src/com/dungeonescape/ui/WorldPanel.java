package com.dungeonescape.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.dungeonescape.world.World;

public class WorldPanel extends JPanel {
	private static final long serialVersionUID = -7233967302635631295L;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	private World world;
	private Point cameraPosition;

	public WorldPanel() {
		setLayout(null);
		setFocusable(true);
		addKeyListener(new WorldKey());
		addMouseListener(new WorldMouse());
		cameraPosition = new Point(WIDTH / 2, HEIGHT / 2);
		setSize(new Dimension(1000, 1000));
	}

	public void setWorld(World w) {
		world = w;
	}

	public Point getCenter() {
		return new Point(WIDTH / 2, HEIGHT / 2);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		world.paint(g);
	}

	public Point getCameraPosition() {
		return cameraPosition;
	}

	public class WorldKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				world.right(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				world.left(true);
			}

			if (e.getKeyCode() == KeyEvent.VK_W) {
				world.jump(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				world.fire();
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				world.right(false);
			}

			if (e.getKeyCode() == KeyEvent.VK_A) {
				world.left(false);
			}

			if (e.getKeyCode() == KeyEvent.VK_W) {
				world.jump(false);
			}
		}
	}

	public class WorldMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			world.hook(e.getX(), e.getY());
		}

		public void mouseMoved(MouseEvent e) {
			cameraPosition.x = (world.getPlayerPosition().x + cameraPosition.x + e
					.getX()) / 2;
			cameraPosition.y = (world.getPlayerPosition().y + cameraPosition.y + e
					.getY()) / 2;
		}
	}
}
