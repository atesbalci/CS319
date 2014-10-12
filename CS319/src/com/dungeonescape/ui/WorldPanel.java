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
	final int WIDTH = 800;
	final int HEIGHT = 600;

	World world;

	public WorldPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(new WorldKey());
		addMouseListener(new WorldMouse());
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
	}
}
