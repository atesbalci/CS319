import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Gun extends Weapon {
	int rate;
	boolean overheat;
	Timer timer;
	
	public Gun() {
		super(false);
		rate = 250;
		overheat = false;
		timer = new Timer(rate, new Overheat());
		timer.setRepeats(false);
		damage = 10;
	}

	public Rectangle2D.Double getImpact() {
		return null;
	}
	
	public Bullet getBullet() {
		firing = false;
		overheat = true;
		timer.start();
		return new Bullet(x, y, direction, damage);
	}
	
	public void draw(Graphics g) {
//		if(direction)
//			g.drawRect(x, y, 50, 10);
//		else
//			g.drawRect(x - 50, y, 50, 10);
	}

	public void fire() {
		if(!overheat)
			firing = true;
	}
	
	public class Overheat implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			overheat = false;
		}
	}
}
