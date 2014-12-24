package com.dungeonescape.gameio.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import com.dungeonescape.element.GameElement;

public class EditorMainPanel extends JPanel {
	private static final long serialVersionUID = -4432837109006093617L;

	private List<GameElement> elements;
	private Point mouse, camera, prevMouse;
	private EditorPanel ep;
	private boolean clicked;

	public EditorMainPanel(List<GameElement> elements, EditorPanel ep) {
		setPreferredSize(new Dimension(1200, 800));
		this.ep = ep;
		this.elements = elements;
		camera = new Point(0, 0);
		addMouseListener(new EditorMouse());
		addMouseMotionListener(new EditorMouse());
		clicked = false;
		mouse = new Point(0, 0);
		setBackground(Color.white);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ep.getLevel().paintBackground((Graphics2D) g, camera, getSize());
		Point spawn = ep.getLevel().getSpawnPoint();
		for (int i = 0; i < elements.size(); i++) {
			GameElement e = elements.get(i);
			e.draw(g, camera);
		}
		g.drawString(mouse.x + ", " + mouse.y, 50, 50);
		Color prevColor = g.getColor();
		g.setColor(Color.yellow);
		g.drawLine(-10 - camera.x, 0 - camera.y, 10 - camera.x, 0 - camera.y);
		g.drawLine(0 - camera.x, 10 - camera.y, 0 - camera.x, -10 - camera.y);
		g.setColor(Color.orange);
		g.drawLine(-10 - camera.x + spawn.x, 0 - camera.y + spawn.y, 10
				- camera.x + spawn.x, 0 - camera.y + spawn.y);
		g.drawLine(0 - camera.x + spawn.x, 10 - camera.y + spawn.y, 0
				- camera.x + spawn.x, -10 - camera.y + spawn.y);
		g.drawString("Spawn Point", spawn.x - camera.x, spawn.y - camera.y);
		g.setColor(Color.cyan);
		g.drawLine(0, ep.getLevel().getFallHeight() - camera.y, getWidth(), ep.getLevel()
				.getFallHeight() - camera.y);
		g.drawString("Fall Height", 0, ep.getLevel().getFallHeight() - camera.y);
		g.setColor(prevColor);
	}

	public Point getMouse() {
		return mouse;
	}

	private class EditorMouse implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouse.x = camera.x + e.getX();
			mouse.y = camera.y + e.getY();
			GameElement element = ep.getSelectedElement();
			if (clicked) {
				if (element != null) {
					int xdiff = e.getX() - prevMouse.x;
					int ydiff = e.getY() - prevMouse.y;
					prevMouse = e.getPoint();
					element.moveX(xdiff);
					element.moveY(ydiff);
				} else {
					int xdiff = e.getX() - prevMouse.x;
					int ydiff = e.getY() - prevMouse.y;
					prevMouse = e.getPoint();
					camera.x -= (xdiff);
					camera.y -= (ydiff);
				}
			}
			ep.refresh();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			clicked = true;
			prevMouse = e.getPoint();
			List<GameElement> elements = ep.getLevel().getElements();
			for (int i = 0; i < elements.size(); i++) {
				GameElement element = elements.get(i);
				Rectangle2D rect = element.getRectangle();
				boolean has = false;
				if (rect != null)
					has = rect.contains(mouse);
				else
					has = element.getLine().contains(mouse);
				if (has) {
					ep.setSelectedElement(element, i);
					return;
				}
			}
			ep.setSelectedElement(null, -10);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			clicked = false;
			ep.setSelectedElement(ep.getSelectedElement(),
					ep.getSelectedElementId());
		}
	}
}
