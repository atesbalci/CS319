package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.dungeonescape.common.CommonMethods;
import com.dungeonescape.common.ContactConstants;
import com.dungeonescape.tool.Tool;

public class Player extends GameElement {
	private final int ANIMATION = 5;

	private boolean direction;
	private Tool tool;
	private BufferedImage[] images, imagesInverted;
	private int stage;
	private boolean ground, jumping, left, right, jump, using;
	private double verticalacc, horizontalacc, jumpHeight;

	public Player(double x, double y) {
		super(x, y);
		direction = true;
		jumpHeight = 10;
		images = new BufferedImage[10];
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(new File("img/player/" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		imagesInverted = new BufferedImage[images.length];
		for (int i = 0; i < imagesInverted.length; i++) {
			imagesInverted[i] = CommonMethods.horizontalflip(images[i]);
		}
		stage = 0;
		verticalacc = 4;
		horizontalacc = 2;
		elasticity = 0;
		ground = false;
		jumping = false;
		jump = false;
		left = false;
		right = false;
		using = false;
	}

	@Override
	public void contact(int direction, GameElement e) {
		if (direction == ContactConstants.BOTTOM) {
			ground = true;
		} else if (direction == ContactConstants.TOP) {
			jumping = false;
		}
	}

	@Override
	public void timestep(double d, List<GameElement> elementsInWorld) {
		if (using) {
			for (GameElement e : elementsInWorld) {
				if (!e.isSmooth()) {
					if (e.getRectangle().intersects(getRectangle())) {
						e.useAction();
						using = false;
					}
				}
			}
		}
		if (right) {
			if (horizontalSpeed < horizontalacc * 10)
				horizontalSpeed += horizontalacc * d;
		} else if (horizontalSpeed > 0) {
			horizontalSpeed -= horizontalacc * d;
		}
		if (left) {
			if (horizontalSpeed > horizontalacc * -10)
				horizontalSpeed -= horizontalacc * d;
		} else if (horizontalSpeed < 0) {
			horizontalSpeed += horizontalacc * d;
		}
		if (jump) {
			if (ground) {
				ground = false;
				jumping = true;
			}

			if (jumping) {
				if (verticalSpeed > -jumpHeight)
					verticalSpeed -= verticalacc * d;
				else {
					jumping = false;
				}
			}
		}
		if (ground) {
			verticalSpeed = 0;
		}
		ground = false;

		if (tool != null)
			tool.timestep(d);

		if ((left || right) && !(left && right)) {
			animate();
		}
	}

	public void left(boolean b) {
		left = b;
	}

	public void right(boolean b) {
		right = b;
	}

	public void jump(boolean b) {
		if (b)
			jump = true;
		else {
			jump = false;
			jumping = false;
		}
	}

	public GameElement useTool(int x, int y) {
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

	public void setDirection(boolean b) {
		direction = b;
	}

	public void draw(Graphics g, Point camera) {
		if (direction) {
			if (!ground)
				g.drawImage(images[9], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
			else if (Math.abs(horizontalSpeed) >= 0.1)
				g.drawImage(images[stage / ANIMATION + 1], (int) x - 10 - camera.x,
						(int) y - camera.y, null);
			else
				g.drawImage(images[0], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
		} else {
			if (!ground)
				g.drawImage(imagesInverted[9], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
			else if (Math.abs(horizontalSpeed) >= 0.1)
				g.drawImage(imagesInverted[stage / ANIMATION + 1], (int) x - 10
						- camera.x, (int) y - camera.y, null);
			else
				g.drawImage(imagesInverted[0], (int) x - 10 - camera.x, (int) y
						- camera.y, null);
		}
	}

	public void animate() {
		stage++;
		if (stage / ANIMATION >= 8)
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

	public boolean isGround() {
		return ground;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public double getVerticalacc() {
		return verticalacc;
	}

	public void setVerticalacc(double verticalacc) {
		this.verticalacc = verticalacc;
	}

	public double getHorizontalacc() {
		return horizontalacc;
	}

	public void setHorizontalacc(double horizontalacc) {
		this.horizontalacc = horizontalacc;
	}

	public double getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	public boolean isUsing() {
		return using;
	}

	public void use() {
		this.using = true;
	}

	public void setX(double x) {
		super.setX(x);
		if (tool != null)
			tool.setX(getCenter().x);
	}

	public void setY(double y) {
		super.setY(y);
		if (tool != null)
			tool.setY(getCenter().y);
	}
}
