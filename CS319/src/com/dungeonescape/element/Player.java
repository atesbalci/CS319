package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.dungeonescape.common.ImageMethods;
import com.dungeonescape.tool.Tool;

public class Player extends GameElement {
	final int ANIMATION = 4;

	private boolean direction;
	private Tool tool;
	private BufferedImage[] images, imagesInverted;
	private int stage;

	public Player(double x, double y) {
		super(x, y);
		direction = true;
		jumpHeight = 10;
		images = new BufferedImage[6];
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO
						.read(new File("img/player/" + (i + 1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imagesInverted = new BufferedImage[images.length];
		for (int i = 0; i < imagesInverted.length; i++) {
			imagesInverted[i] = ImageMethods.horizontalflip(images[i]);
		}
		stage = 0;
		verticalacc = 4;
		horizontalacc = 2;
		elasticity = 0.1;
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		super.timestep(d, elementsInWorld);
		
		if(tool != null)
			tool.timestep(d);

		if ((left || right) && !(left && right)) {
			animate();
		}
	}

	public void left(boolean b) {
		super.left(b);
		if (b)
			setDirection(false);
	}

	public void right(boolean b) {
		super.right(b);
		if (b)
			setDirection(true);
	}

	public GameElement use(int x, int y) {
		if (tool != null)
			return tool.use(x, y);
		return null;
	}

	public void moveX(double xChange) {
		x += xChange;
		if (tool != null) {
			tool.setX(tool.getX() + xChange);
		}
	}

	public void moveY(double yChange) {
		y += yChange;
		if (tool != null) {
			tool.setY(tool.getY() + yChange);
		}
	}

	public void setDirection(boolean dir) {
		direction = dir;
	}

	public void draw(Graphics g, Point camera) {
		drawHealth(g, (int) x + width / 2, (int) y);
		if (direction) {
			if (!ground)
				g.drawImage(images[5], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
			else if (horizontalSpeed >= 1)
				g.drawImage(images[stage / ANIMATION], (int) x - 10 - camera.x,
						(int) y - camera.y, null);
			else
				g.drawImage(images[2], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
		} else {
			if (!ground)
				g.drawImage(imagesInverted[5], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
			else if (horizontalSpeed <= -1)
				g.drawImage(imagesInverted[stage / ANIMATION], (int) x - 10
						- camera.x, (int) y - camera.y, null);
			else
				g.drawImage(imagesInverted[2], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
		}
	}

	public Point getCenter() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}

	public void animate() {
		stage++;
		if (stage / ANIMATION >= 5)
			stage = 0;
	}

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
		tool.setOwner(this);
		tool.setX(x + width / 2);
		tool.setY(y + height / 2);
	}
}
