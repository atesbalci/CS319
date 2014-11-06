package com.dungeonescape.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.dungeonescape.common.ImageMethods;
import com.dungeonescape.tool.Rope;
import com.dungeonescape.tool.Weapon;

public class Player extends GameElement {
	final int ANIMATION = 4;

	private boolean direction;
	private Weapon weapon;
	private Rope rope;
	private Rectangle2D fireRectangle;
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
						.read(new File("player/" + (i + 1) + ".png"));
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

		if ((left || right) && !(left && right)) {
			animate();
		}

		if (rope != null) {
			if (rope.getHook().grappled) {
				if (rope.getHook().active) {
					double angle = Math.atan((double) (rope.getHook().y - rope
							.getY())
							/ (double) (rope.getHook().x - rope.getX()));
					if (rope.getHook().x - rope.getX() <= 0)
						angle = Math.PI + angle;
					horizontalSpeed = Math.cos(angle) * rope.getPullSpeed();
					verticalSpeed = Math.sin(angle) * rope.getPullSpeed();
					// Rectangle2D.Double r = rope.hook.getRectangle();
					// if(getRectangle().intersects(r.x, r.y, r.width,
					// r.height)) {
					// xvel -= Math.cos(angle) * rope.PULL_SPEED;
					// yvel -= Math.sin(angle) * rope.PULL_SPEED;
					// }
				}
			}
		}
	}

	public void fire() {
		if (weapon != null) {
			weapon.fire();
		}
	}

	public boolean firing() {
		if (weapon != null)
			return weapon.firing();
		return false;
	}

	public void arm(Weapon w) {
		weapon = w;
		w.setX(width / 2 + (int) x);
		w.setY(height / 2 + (int) y);
		w.setDirection(direction);
	}

	public void takeRope() {
		rope = new Rope((int) x + width / 2, (int) y + height / 2, this);
	}

	public void disarm() {
		weapon = null;
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

	public void moveX(double xChange) {
		x += xChange;
		if (weapon != null) {
			weapon.setX(weapon.getX() + xChange);
		}
		if (rope != null) {
			rope.setX(rope.getX() + xChange);
		}
	}

	public void moveY(double yChange) {
		y += yChange;
		if (weapon != null) {
			weapon.setY(weapon.getY() + yChange);
		}
		if (rope != null) {
			rope.setY(rope.getY() + yChange);
		}
	}

	public void setDirection(boolean dir) {
		direction = dir;
		if (weapon != null)
			weapon.setDirection(dir);
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
		if (weapon != null)
			weapon.draw(g);
	}

	public Point getCenter() {
		return new Point((int) x + width / 2, (int) y + height / 2);
	}

	public void animate() {
		stage++;
		if (stage / ANIMATION >= 5)
			stage = 0;
	}

	public Hook throwHook(int xdir, int ydir) {
		Hook h = null;
		if (rope != null) {
			h = rope.fire((int) xdir, (int) ydir);
		}
		return h;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Rope getRope() {
		return rope;
	}

	public void setRope(Rope rope) {
		this.rope = rope;
	}

	public Rectangle2D getFireRectangle() {
		return fireRectangle;
	}

	public void setFireRectangle(Rectangle2D fireRectangle) {
		this.fireRectangle = fireRectangle;
	}
}
