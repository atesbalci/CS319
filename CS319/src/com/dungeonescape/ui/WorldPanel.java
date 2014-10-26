package com.dungeonescape.ui;

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

	private World world;
	private Point mousePosition;
	private Point cameraPosition;

	public WorldPanel() {
		WorldMouse mouse = new WorldMouse();
		setLayout(null);
		setFocusable(true);
		addKeyListener(new WorldKey());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		mousePosition = new Point(this.getSize().width / 2,
				this.getSize().height / 2);
		cameraPosition = new Point(0, 0);
	}

	public void setWorld(World w) {
		world = w;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		refreshCameraPosition();
		world.paint(g, cameraPosition);
	}

	public void refreshCameraPosition() {
		int width = getSize().width;
		int height = getSize().height;
		cameraPosition.x = ((mousePosition.x + cameraPosition.x + (world
				.getPlayerPosition().x)) / 2) - (width / 2);
		cameraPosition.y = ((mousePosition.y + cameraPosition.y + (world
				.getPlayerPosition().y)) / 2) - (height / 2);
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
			world.hook(e.getX() + cameraPosition.x, e.getY() + cameraPosition.y);
		}

		public void mouseMoved(MouseEvent e) {
			mousePosition.x = (e.getX());
			mousePosition.y = (e.getY());
		}
	}
}
