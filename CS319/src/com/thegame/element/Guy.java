package com.thegame.element;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.thegame.common.ImageMethods;
import com.thegame.tool.Rope;
import com.thegame.tool.Weapon;

public class Guy extends GameElement {
	final int WIDTH = 20;
	final int HEIGHT = 40;
	final int ANIMATION = 4;

	private boolean direction;
	private Weapon weapon;
	private Rope rope;
	private Rectangle2D fireRectangle;
	private BufferedImage[] images, imagesInverted;
	private int stage;

	public Guy(int x, int y) {
		super(x, y);
		direction = true;
		verticalSpeed = 5;
		jumpHeight = 10;
		images = new BufferedImage[6];
		for (int i = 0; i < images.length; i++) {
			try {
				images[i] = ImageIO.read(new File("guy/" + (i + 1) + ".png"));
			} catch (IOException e) {
			}
		}
		imagesInverted = new BufferedImage[images.length];
		for (int i = 0; i < imagesInverted.length; i++) {
			imagesInverted[i] = ImageMethods.horizontalflip(images[i]);
		}
		stage = 0;
		verticalacc = 4;
		horizontalacc = 3.6;
	}

	public void action(double d) {
		super.action(d);

		if ((left || right) && !(left && right)) {
			animate();
		}

		if (rope != null) {
			if (rope.getHook().grappled) {
				if (rope.getHook().active) {
					double angle = Math.atan((double) (rope.getHook().y - rope.getY())
							/ (double) (rope.getHook().x - rope.getX()));
					if (rope.getHook().x - rope.getX() <= 0)
						angle = Math.PI + angle;
					xvel = Math.cos(angle) * rope.getPull();
					yvel = Math.sin(angle) * rope.getPull();
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
		w.setX(WIDTH / 2 + (int) x);
		w.setY(HEIGHT / 2 + (int) y);
		w.setDirection(direction);
	}

	public void takeRope() {
		rope = new Rope((int) x + WIDTH / 2, (int) y + HEIGHT / 2, this);
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

	public void moveX(int xChange) {
		x += xChange;
		if (weapon != null) {
			weapon.setX(weapon.getX() + xChange);
		}
		if (rope != null) {
			rope.setX(rope.getX() + xChange);
		}
	}

	public void moveY(int yChange) {
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

	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
	}

	public void draw(Graphics g) {
		drawHealth(g, (int) x + WIDTH / 2, (int) y);
		if (direction) {
			if (!ground)
				g.drawImage(images[5], (int) x - 10, (int) y, null);
			else if (xvel >= 1)
				g.drawImage(images[stage / ANIMATION], (int) x - 10, (int) y,
						null);
			else
				g.drawImage(images[2], (int) x - 10, (int) y, null);
		} else {
			if (!ground)
				g.drawImage(imagesInverted[5], (int) x - 10, (int) y, null);
			else if (xvel <= -1)
				g.drawImage(imagesInverted[stage / ANIMATION], (int) x - 10,
						(int) y, null);
			else
				g.drawImage(imagesInverted[2], (int) x - 10, (int) y, null);
		}
		if (weapon != null)
			weapon.draw(g);
	}

	public void obstruction(String direcction, GameElement e) {
	}

	public Point getCenter() {
		return new Point((int) x + WIDTH / 2, (int) y + HEIGHT / 2);
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
