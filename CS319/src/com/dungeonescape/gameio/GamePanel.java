package com.dungeonescape.gameio;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.dungeonescape.game.Game;
import com.dungeonescape.ui.GameMenu;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -7233967302635631295L;
	
	public static int rightButton = KeyEvent.VK_D;
	public static int leftButton = KeyEvent.VK_A;
	public static int jumpButton = KeyEvent.VK_W;
	public static int useButton = KeyEvent.VK_E;
	public static int restartButton = KeyEvent.VK_R;
		
	private Game game;
	private Point mousePosition;
	private Point cameraPosition;
	private BufferedImage cursorImage;
	private String tip;
	private GameMenu gameMenu;
	
	public GamePanel() {
		GameMouse mouse = new GameMouse();
		setFocusable(true);
		addKeyListener(new GameKey());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		mousePosition = new Point(this.getSize().width / 2,
				this.getSize().height / 2);
		cameraPosition = new Point(0, 0);
		try {
			setCursorImage(ImageIO.read(new File("img/cursor.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setCursor(getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		setBackground(Color.white);
	}

	public void end(boolean success) {
		(new EnderThread(success)).start();
	}

	private class EnderThread extends Thread {
		private boolean success;

		public EnderThread(boolean success) {
			this.success = success;
		}

		public void run() {
			game.stop();
			if (!success) {
				game.reload();
				dismissTip();
				game.start();
			} else if(gameMenu != null) {
				gameMenu.levelComplete();
			}
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		refreshCameraPosition();
		game.getLevel().paintBackground((Graphics2D) g, cameraPosition,
				getSize());
		game.paint(g, cameraPosition);
		g.drawImage(cursorImage, mousePosition.x, mousePosition.y, null);
		if (tip != null) {
			Dimension size = getSize();
			Graphics2D g2d = ((Graphics2D) g);
			Composite prev = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.9f));
			g.fillRect(0, 0, size.width, size.height);
			g2d.setComposite(prev);
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", Font.BOLD, 32));
			int centerX = getWidth() / 2;
			int centerY = getHeight() / 2;
			FontMetrics fontMetrics = g.getFontMetrics();
			Rectangle stringBounds = fontMetrics.getStringBounds(tip, g)
					.getBounds();
			Font font = g.getFont();
			FontRenderContext renderContext = ((Graphics2D) g)
					.getFontRenderContext();
			GlyphVector glyphVector = font
					.createGlyphVector(renderContext, tip);
			Rectangle visualBounds = glyphVector.getVisualBounds().getBounds();
			int textX = centerX - stringBounds.width / 2;
			int textY = centerY - visualBounds.height / 2 - visualBounds.y;

			g.drawString(tip, textX, textY);
		}
	}

	public void refreshCameraPosition() {
		int width = getSize().width;
		int height = getSize().height;
		cameraPosition.x = ((mousePosition.x + cameraPosition.x + 2 * (game
				.getPlayerPosition().x)) / 3) - (width / 2);
		cameraPosition.y = ((mousePosition.y + cameraPosition.y + 2 * (game
				.getPlayerPosition().y)) / 3) - (height / 2);
	}

	public BufferedImage getCursorImage() {
		return cursorImage;
	}

	public void setCursorImage(BufferedImage cursorImage) {
		this.cursorImage = cursorImage;
	}

	public void showTip(String tip) {
		if (!tip.isEmpty()) {
			this.tip = tip;
		}
	}

	public void dismissTip() {
		tip = null;
	}

	public GameMenu getGameMenu() {
		return gameMenu;
	}

	public void setGameMenu(GameMenu gameMenu) {
		this.gameMenu = gameMenu;
	}

	public class GameKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			dismissTip();
			if (e.getKeyCode() == rightButton) {
				game.right(true);
			}
			if (e.getKeyCode() == leftButton) {
				game.left(true);
			}
			if (e.getKeyCode() == jumpButton) {
				game.jump(true);
			}
			if (e.getKeyCode() == useButton) {
				game.use();
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (game.isStopped())
					game.start();
				else
					game.stop();
			}
			if (e.getKeyCode() == restartButton) {
				end(false);
			}
		}

		public void keyReleased(KeyEvent e) {
			dismissTip();
			if (e.getKeyCode() == rightButton) {
				game.right(false);
			}
			if (e.getKeyCode() == leftButton) {
				game.left(false);
			}
			if (e.getKeyCode() == jumpButton) {
				game.jump(false);
			}
		}
	}

	public class GameMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			dismissTip();
			if (e.getButton() == MouseEvent.BUTTON1)
				game.useTool(e.getX() + cameraPosition.x, e.getY()
						+ cameraPosition.y);
		}

		public void mouseMoved(MouseEvent e) {
			mousePosition.x = (e.getX());
			mousePosition.y = (e.getY());
			game.getPlayer().setDirection(mousePosition.x > getWidth() / 2);

		}

		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}
	}
}
